package io.natix.slix.core.payload;

public class DIDDocument implements Payload {
    private String id;

    private String type;

    private String name;

    private PublicKey[] publicKey;

    private Service[] service;

    private String status;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public DIDDocument() {
    }

    public DIDDocument(String id, String type, String name, PublicKey[] publicKey, Service[] service, String status) {
        this.id = id;
        this.type = type;
        this.name = name;
        this.publicKey = publicKey;
        this.service = service;
        this.status = status;
    }
}
