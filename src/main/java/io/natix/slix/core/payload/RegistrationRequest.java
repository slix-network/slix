package io.natix.slix.core.payload;

import java.util.Set;

public class RegistrationRequest implements Payload {
    private String formId;

    private String bundleId;

    private Set<RegistrationDataRequest> data;

    public String getFormId() {
        return formId;
    }

    public void setFormId(String formId) {
        this.formId = formId;
    }

    public String getBundleId() {
        return bundleId;
    }

    public void setBundleId(String bundleId) {
        this.bundleId = bundleId;
    }

    public Set<RegistrationDataRequest> getData() {
        return data;
    }

    public void setData(Set<RegistrationDataRequest> data) {
        this.data = data;
    }

    public RegistrationRequest() {
    }

    public RegistrationRequest(String formId, String bundleId, Set<RegistrationDataRequest> data) {
        this.formId = formId;
        this.bundleId = bundleId;
        this.data = data;
    }

    @Override
    public String toString() {
        return "Registration{" +
                "formId='" + formId + '\'' +
                ", bundleId='" + bundleId + '\'' +
                ", data=" + data +
                '}';
    }
}
