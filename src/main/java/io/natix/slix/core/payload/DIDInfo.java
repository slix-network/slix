package io.natix.slix.core.payload;

public class DIDInfo implements Payload {
    private String did;

    private String didName;

    public String getDid() {
        return did;
    }

    public void setDid(String did) {
        this.did = did;
    }

    public String getDidName() {
        return didName;
    }

    public void setDidName(String didName) {
        this.didName = didName;
    }

    public DIDInfo() {
    }

    public DIDInfo(String did, String didName) {
        this.did = did;
        this.didName = didName;
    }

    @Override
    public String toString() {
        return "DIDInfo{" +
                "did='" + did + '\'' +
                ", didName='" + didName + '\'' +
                '}';
    }
}
