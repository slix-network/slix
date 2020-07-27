package io.natix.slix.core.payload;

public class ReadRegistrationStatusRequest implements Payload {
    private String bundleId;

    public String getBundleId() {
        return bundleId;
    }

    public void setBundleId(String bundleId) {
        this.bundleId = bundleId;
    }

    public ReadRegistrationStatusRequest() {
    }

    public ReadRegistrationStatusRequest(String bundleId) {
        this.bundleId = bundleId;
    }

    @Override
    public String toString() {
        return "ReadRegistrationStatusRequest{" +
                "bundleId='" + bundleId + '\'' +
                '}';
    }
}
