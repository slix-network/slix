package io.natix.slix.core.payload;

import io.natix.slix.core.metadata.PacketType;

public class Event {
    private DIDDocument sender;

    private String packetId;

    private boolean validity;

    private PacketType type;

    private byte[] data;

    public DIDDocument getSender() {
        return sender;
    }

    public void setSender(DIDDocument sender) {
        this.sender = sender;
    }

    public String getPacketId() {
        return packetId;
    }

    public void setPacketId(String packetId) {
        this.packetId = packetId;
    }

    public boolean isValidity() {
        return validity;
    }

    public void setValidity(boolean validity) {
        this.validity = validity;
    }

    public PacketType getType() {
        return type;
    }

    public void setType(PacketType type) {
        this.type = type;
    }

    public byte[] getData() {
        return data;
    }

    public void setData(byte[] data) {
        this.data = data;
    }

    public Event() {
    }

    public Event(DIDDocument sender, String packetId, boolean validity, PacketType type, byte[] data) {
        this.sender = sender;
        this.packetId = packetId;
        this.validity = validity;
        this.type = type;
        this.data = data;
    }
}
