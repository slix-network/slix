package io.natix.slix.core.payload;

import org.apache.commons.lang3.StringUtils;

public class CustomMessage implements Payload {
    private String kind;

    private String value;

    public String getKind() {
        return kind;
    }

    public void setKind(String kind) {
        this.kind = kind;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public CustomMessage() {
    }

    public CustomMessage(String kind) {
        this(kind, StringUtils.EMPTY);
    }

    public CustomMessage(String kind, String value) {
        this.kind = kind;
        this.value = value;
    }


    @Override
    public String toString() {
        return "CustomMessage{" +
                "kind='" + kind + '\'' +
                ", value='" + value + '\'' +
                '}';
    }
}
