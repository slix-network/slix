package io.natix.slix.core.payload;

public class DIDDocumentRequest implements Payload {
    private String name;

    private PublicKey[] publicKey;

    private Service[] service;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public PublicKey[] getPublicKey() {
        return publicKey;
    }

    public void setPublicKey(PublicKey[] publicKey) {
        this.publicKey = publicKey;
    }

    public Service[] getService() {
        return service;
    }

    public void setService(Service[] service) {
        this.service = service;
    }

    public DIDDocumentRequest() {
    }

    public DIDDocumentRequest(String name, PublicKey[] publicKey, Service[] service) {
        this.name = name;
        this.publicKey = publicKey;
        this.service = service;
    }
}
