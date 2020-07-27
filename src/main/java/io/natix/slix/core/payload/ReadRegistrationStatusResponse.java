package io.natix.slix.core.payload;

import io.natix.slix.core.metadata.AgentStatus;

import java.util.Set;

public class ReadRegistrationStatusResponse implements Payload {
    private String id;

    private DIDInfo didInfo;

    private String bundleId;

    private Set<FieldValue> data;

    private Boolean forwardingPermission;

    private AgentStatus status;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public DIDInfo getDidInfo() {
        return didInfo;
    }

    public void setDidInfo(DIDInfo didInfo) {
        this.didInfo = didInfo;
    }

    public String getBundleId() {
        return bundleId;
    }

    public void setBundleId(String bundleId) {
        this.bundleId = bundleId;
    }

    public Set<FieldValue> getData() {
        return data;
    }

    public void setData(Set<FieldValue> data) {
        this.data = data;
    }

    public Boolean getForwardingPermission() {
        return forwardingPermission;
    }

    public void setForwardingPermission(Boolean forwardingPermission) {
        this.forwardingPermission = forwardingPermission;
    }

    public AgentStatus getStatus() {
        return status;
    }

    public void setStatus(AgentStatus status) {
        this.status = status;
    }

    public ReadRegistrationStatusResponse() {
    }

    public ReadRegistrationStatusResponse(String id, DIDInfo didInfo, String bundleId, Set<FieldValue> data, Boolean forwardingPermission, AgentStatus status) {
        this.id = id;
        this.didInfo = didInfo;
        this.bundleId = bundleId;
        this.data = data;
        this.forwardingPermission = forwardingPermission;
        this.status = status;
    }

    @Override
    public String toString() {
        return "ReadRegistrationStatusResponse{" +
                "id='" + id + '\'' +
                ", didInfo=" + didInfo +
                ", bundleId='" + bundleId + '\'' +
                ", data=" + data +
                ", forwardingPermission=" + forwardingPermission +
                ", status=" + status +
                '}';
    }
}
