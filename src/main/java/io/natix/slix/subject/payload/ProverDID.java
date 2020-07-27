package io.natix.slix.subject.payload;

import io.natix.slix.core.payload.Payload;

public class ProverDID implements Payload {
    private String id;

    private String type;

    private String name;

    private String replyUri;

    private String subscribeUri;

    private String ecdhPublicKey;

    private String eddsaPublicKey;

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

    public String getReplyUri() {
        return replyUri;
    }

    public void setReplyUri(String replyUri) {
        this.replyUri = replyUri;
    }

    public String getSubscribeUri() {
        return subscribeUri;
    }

    public void setSubscribeUri(String subscribeUri) {
        this.subscribeUri = subscribeUri;
    }

    public String getEcdhPublicKey() {
        return ecdhPublicKey;
    }

    public void setEcdhPublicKey(String ecdhPublicKey) {
        this.ecdhPublicKey = ecdhPublicKey;
    }

    public String getEddsaPublicKey() {
        return eddsaPublicKey;
    }

    public void setEddsaPublicKey(String eddsaPublicKey) {
        this.eddsaPublicKey = eddsaPublicKey;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public ProverDID() {
    }

    public ProverDID(String id, String type, String name, String replyUri, String subscribeUri, String ecdhPublicKey, String eddsaPublicKey, String status) {
        this.id = id;
        this.type = type;
        this.name = name;
        this.replyUri = replyUri;
        this.subscribeUri = subscribeUri;
        this.ecdhPublicKey = ecdhPublicKey;
        this.eddsaPublicKey = eddsaPublicKey;
        this.status = status;
    }

    @Override
    public String toString() {
        return "ProverDID{" +
                "id='" + id + '\'' +
                ", type='" + type + '\'' +
                ", name='" + name + '\'' +
                ", replyUri='" + replyUri + '\'' +
                ", subscribeUri='" + subscribeUri + '\'' +
                ", ecdhPublicKey='" + ecdhPublicKey + '\'' +
                ", eddsaPublicKey='" + eddsaPublicKey + '\'' +
                ", status='" + status + '\'' +
                '}';
    }
}
