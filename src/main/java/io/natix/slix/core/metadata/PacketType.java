package io.natix.slix.core.metadata;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;

public enum PacketType {
    Requester(0),
    Subject(1),
    Prover(2),
    Custom(3);

    private final Integer value;

    public Integer getValue() {
        return value;
    }

    PacketType(Integer value) {
        this.value = value;
    }

    public static PacketType nameOf(String s) {
        for (PacketType value : values())
            if (StringUtils.equalsIgnoreCase(s, value.name())) return value;
        return null;
    }

    public static PacketType valueOf(Integer s) {
        for (PacketType value : values())
            if (NumberUtils.compare(s, value.getValue()) == 0) return value;
        return null;
    }
}
