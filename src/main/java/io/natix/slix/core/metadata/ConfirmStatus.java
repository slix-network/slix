package io.natix.slix.core.metadata;

public enum ConfirmStatus {
    Reject(0),
    Confirm(1),
    Modify(2),
    OnHold(3);

    private final Integer value;

    public Integer getValue() {
        return value;
    }

    ConfirmStatus(Integer value) {
        this.value = value;
    }
}
