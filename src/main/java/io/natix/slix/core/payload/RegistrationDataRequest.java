package io.natix.slix.core.payload;

public class RegistrationDataRequest implements Payload {
    private String field;

    private String value;

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public RegistrationDataRequest() {
    }

    public RegistrationDataRequest(String field, String value) {
        this.field = field;
        this.value = value;
    }

    @Override
    public String toString() {
        return "RegistrationDataRequest{" +
                "field='" + field + '\'' +
                ", value='" + value + '\'' +
                '}';
    }
}
