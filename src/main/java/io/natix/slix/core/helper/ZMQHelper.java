package io.natix.slix.core.helper;

import org.zeromq.SocketType;
import org.zeromq.ZContext;
import org.zeromq.ZMQ;

import java.util.concurrent.Executors;

public interface ZMQHelper {

    default void send(String uri, String msg, ReplyCallBack callBack) {
        send(uri, msg.getBytes(), callBack);
    }

    default void send(String uri, byte[] msg, ReplyCallBack callBack) {
        try (ZContext context = new ZContext()) {
            ZMQ.Socket socket = context.createSocket(SocketType.REQ);
            socket.connect(uri);
            socket.setSendTimeOut(60000);
            socket.setReceiveTimeOut(60000);
            socket.send(msg, 0);
            byte[] reply = socket.recv(0);
            callBack.onReceive(reply);
        }
    }

    default void publish(String uri, String topic, String msg) {
        publish(uri, topic.getBytes(), msg.getBytes());
    }

    default void publish(String uri, byte[] topic, byte[] msg) {
        try (ZContext context = new ZContext()) {
            ZMQ.Socket socket = context.createSocket(SocketType.PUB);
            socket.bind(uri);
            socket.sendMore(topic);
            socket.send(msg, 0);
        }
    }

    default void subscribe(String uri, String topic, SubscribeCallBack callBack) {
        subscribe(uri, topic.getBytes(), callBack);
    }

    default void subscribe(String uri, byte[] topic, SubscribeCallBack callBack) {
        Executors.newFixedThreadPool(1).execute(() -> {
            try (ZContext context = new ZContext()) {
                ZMQ.Socket subscriber = context.createSocket(SocketType.SUB);
                subscriber.connect(uri);
                subscriber.subscribe(topic);
                while (!Thread.currentThread().isInterrupted()) {
                    byte[] recvTopic = subscriber.recv();
                    byte[] msg = subscriber.recv();
                    callBack.onReceive(recvTopic, msg);
                }
            }
        });
    }
}
