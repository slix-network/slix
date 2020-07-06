# SLIX

In recent years, the number of deployed mobile and Internet of Things (IoT) devices has been growing drastically. The Edge computing Networks created by these IoT/mobile devices enables participates of the network to offload their tasks to get assisted to deliver the assigned jobs. However, this introduces more security threats since it increases the real-world attack surface, especially in multi-party scenarios where not all the devices are owned by one party.

Considering how much information is generated or carried by an IoT device, organizations have good reasons to keep the data confidential. While it is needed to share information with others, it is hardly possible to ensure that one cannot share information without the consent of the owner. However, if the trustworthy requester of the information, is interested in mutually confirmed data by both parties (e.g. device and owner), we will be able to design a system with high integrity. Such system guarantees a certain level of protection and offering fine-grained access management system.

The access control models designed for the web applications and cloud computing mainly consist of different permissions about Read and Write. Such a model would never be satisfiable in edge computing due to the more complicity of the systems and their enabled applications. This calls for fine-grained access control that address the questions such as who can access which devices for what reason at when and how. Current access control protocols sucj OAuth can not satisfy this requirement.

## Getting Started

To use this library you should add this dependency to your pom.xml
```
<dependency>
    <groupId>io.natix</groupId>
    <artifactId>slix</artifactId>
    <version>1.0</version>
</dependency>
```
It's not available on maven central yet. For now you should add this repository to you pom.xml as well.
```
<repository>
    <id>slix</id>
    <url>https://github.com/natix/slix/raw/master/mvn-repo</url>
</repository>

``` 

## Protocol
In the scchenarios that a comfirmation is needed form a third party, to exchange attributes related to a peer, 
we can picture a network with three main actors involved: Subject, Requester, and

[Prover]--------[Subject]--------[Requester]

In these scenarios, Requester asks for information from a Subject
but requires to have a confirmation from the corresponding Prover. For example, an institute (Requester) wants to verify that a person or a
device (Subject) works for the claimed organization (Prover).


Hamlib creates a P2P network between nodes of the system. We use proto to serialize and deserialize packets in
communication between nodes. We use Curve25519 to sign and verify the signature of packets and to calculate a shared key
between two parties and use the shared key to AES encrypt the whole package. So the communication between nodes are encrypted 
and can not get interpreted other than the parties who wants to communicate with each other.
The most underlying packet that can get transferred in this network is:

```
message EncryptedPacket {
    int32 type = 1; //0 -> requesterPacket, 1 -> subjectPacket, 2 -> proverPacket
    string did = 2;
    bytes data = 3;
} 
```

In this type of packet the did parameter is the sender did, when a node receives this packet by getting the did parameter 
the receiver node can load the did document of the sender and thus it can retrieve the ECDH public key of the sender so that   
the shared key can get calculated for further decryption.

#### DID Document
Decentralized identifiers (DIDs) are a new type of identifier that enables verifiable, decentralized digital identity.
Each node is defined by a DID document and the structure of its document is:

```
{
    "context":"https://w3id.org/future-method/v1",
    "type":"PROVER",
    "status":"ACTIVE",
    "id":"Rb9s3hViUucztAU4VjRdJn"
    "name": name of the devuce,
    "publicKey":
        [
            {
                "id": "xxxxxxxxxxxxx",
                "type":"eddsa",
                "publicKey":"xxxxxxxxxxxxx"
            },
            {
                "id":"xxxxxxxxxxxxx",
                "type":"ecdh",
                "publicKey":"xxxxxxxxxxxxx"
            }
        ],
    "service":
        [
            {
                "id":"xxxxxxxxxxxxx",
                "type":"subscribe",
                "serviceEndpoint":"tcp://IP:7200"
            },
            {
                "id":"xxxxxxxxxxxxx",
                "type":"reply",
                "serviceEndpoint":"tcp://IP:7201"
            }
        ]    
}
```
By having the shared key and the type parameter of encrypted packet the receiver node can decrypt the packet.
If Requester wants to request a permission it creates a RequesterPacket signs the packet and wraps it in the EncryptPacket and sends it to 
the subject then subject creates the SubjectPacket with the received data, signs the packet then wraps it in the EncryptPacket and sends it to a prover then prover
creates a ProverPacket with the received data, adds a confirmation to the packet with accept or reject status, signs the packet and as always wraps it in the EncryptPacket 
and sends it to another prover to get its permission or sends it back to the subject.
Subject can decide to resend the packet to prover or add another confirmation to the ProverPacket with its new signature and sends it to the Requester.
With all signatures in the process requester can make sure what happened to the packet and which node signed the packet and when it signed the packet 
and with all these data requester can make sure that data is not corrupted or bogus and can decide weather to accept the packet or not.

#### Structures
The structure of all packets are as followed:


---
````
message RequesterPacket {
    Query query = 1;
    Signature signature = 2;
}
```` 
---
````
message Query {
    int32 version = 1;
    string requesterDID = 2;
    string proverDID = 3;
    int32 type = 4;
    repeated string queryArgs = 5;
}
````
---
````
message Signature {
    int64 time = 1;
    string data = 2;
}
````
---
````
message SubjectPacket {
    RequesterPacket requesterPacket = 1;
    Answer answer = 2;
    Signature signature = 3;

}
````
---
````
message Answer {
    string subjectDID = 1;
    string proverDID = 2;
    repeated string answerArgs = 3;
    int64 startValidationTime = 4;
    int64 endValidationTime = 5;
    int64 validityToken = 6;
    string bundleId = 7;
}
````
---
````
message ProverPacket {
    SubjectPacket subjectPacket = 1;
    repeated Confirmation confirmations = 3;
    repeated Signature signatures = 4;
}
````
---
````
message Confirmation {
    string proverDID = 1;
    int32 status = 2;
    repeated string answerArgs = 3;
    int64 startValidationTime = 4;
    int64 endValidationTime = 5;
    int64 validityToken = 6;

}
````
---
```
message EncryptedPacket {
    int32 type = 1; //0 -> requesterPacket, 1 -> subjectPacket, 2 -> proverPacket, 3 -> custom
    string did = 2;
    bytes data = 3;
} 
```

#### Detail
If Requester wants to ask for a permission from a Prover through Subject it should create a RequesterMessage then encrypt
the RequesterMessage with the calculated shared key by its private key and prover's public key then wrap it in the EncryptPAcket
and then send the packet to the prover's reply service endpoint and should subscribe to the according service in prover's did document
to get the result from that port.

##### Requester Packet
To create a requester packet you should first create a query packet. query consists of these parameters:

| Parameter | Type | Description |
|-----------|------|-------------|
| Version   | int32 | This is for backward compatibility for future, for now this parameter is 1|
|requesterDID|string|This is the DID of requester|
|proverDID|string| This is the the DID of the suggested prover that requester wants to get the permission from.|
|type|int32| 0 => Relation, 1 => Position, 2 => Certificate, 3 => Permission, 4 => Edge |
|queryArgs|array of string| This is arguments that each type of question can use. For example in Relation, Position, Certificate and Permission type of query args[0] is the name of the organization, args[1] is the name of the position or certificate or permission, args[2] is the suggested validity time of the token, args[3] and args[4] is the suggested time frame of the certificate or permission.


After creating the Query with above parameters requester should sign the Query packet and put the hex string of the signature and the EPOCH time of signature in Signature Packet
and then requester can create Requester packet with Query and Signature.

##### Subject Packet
After receiving requester packet subject should answer the packet with following parameters:

| Parameter | Type | Description |
|-----------|------|-------------|
| subjectDID   | string | This is the did of the subject |
|proverDID|string| This is the did of the prover that subject decides to send the packet to |
|answerArgs|array of string| This is the answer of the Query the Requester asked |
|startValidationTime|int64| This is the time frame of the answer if necessary |
|endValidationTime|int64| This is the time frame of the answer if necessary |
|validityToken|int64| This is time that until then this token is valid |
|bundleId|string| This is an id to be able to group these packets |
 
 After that subject should create the Subject packet with answer and requester packet then it should sign the subject packet and put 
 the hex string of signature in the subject packet.
 
 
##### Prover Packet
When prover receives a subject packet it can decide weather reject or accept the packet. Then if there is no need to send it to another prover, prover creates a confirmation and then signs the packet and
sends it back to subject but if another approval is needed by another prover, prover should create a confirmation packet with the hold status and signs the packet and then forward it to another prover. Any packet sending 
to a prover should be via reply port of the node and any receiving packet in subject should be via subscribe port of the node.

***Notice:*** Each prover should sign the combination of subject packet and its own confirmation.
 
You can find description of confirmation packet's parameters in following table:

| Parameter | Type | Description |
|-----------|------|-------------|
| proverDID   | string | This is the did of the subject |
|status|int32| 0 => Reject, 1 => Confirm, 2 => Modify, 3 => Hold|
|answerArgs|array of string| This is the real answer of the Query that subject filled, and prover modified it. this field should only get filled when status is Modify|
|startValidationTime|int64| This is the time frame of the real answer that prover modified, this field should only get field if status is Modified|
|endValidationTime|int64| This is the time frame of the real answer that prover modified, this field should only get field if status is Modify|
|validityToken|int64| This is time that until then this token is valid |
  
## Code Walkthrough
... Under construction...


## License

This project is licensed under the GNU AGPLv3 License - see the [LICENSE](LICENSE) file for details


