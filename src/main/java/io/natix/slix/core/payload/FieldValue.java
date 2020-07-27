package io.natix.slix.core.payload;

import org.apache.commons.lang3.StringUtils;

public class FieldValue implements Payload {
    private Field field;

    private String value;

    public Field getField() {
        return field;
    }

    public void setField(Field field) {
        this.field = field;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public FieldValue() {
    }

    public FieldValue(Field field) {
        this(field, StringUtils.EMPTY);
    }

    public FieldValue(Field field, String value) {
        this.field = field;
        this.value = value;
    }

    @Override
    public String toString() {
        return "FieldValue{" +
                "field='" + field + '\'' +
                ", value='" + value + '\'' +
                '}';
    }
}
