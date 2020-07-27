package io.natix.slix.core.payload;

import io.natix.slix.core.util.CryptoUtil;

public class PublicKey implements Payload {
    private String id;

    private String type;

    private String publicKey;

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

    public String getPublicKey() {
        return publicKey;
    }

    public void setPublicKey(String publicKey) {
        this.publicKey = publicKey;
    }

    public PublicKey() {
    }

    public PublicKey(String type, String publicKey) {
        this(type, publicKey, CryptoUtil.sha256(publicKey));
    }

    public PublicKey(String type, String publicKey, String id) {
        this.type = type;
        this.publicKey = publicKey;
        this.id = id;
    }


}
