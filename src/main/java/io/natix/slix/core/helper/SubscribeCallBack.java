package io.natix.slix.core.helper;

public interface SubscribeCallBack {

    void onReceive(byte[] topic, byte[] msg);

}
