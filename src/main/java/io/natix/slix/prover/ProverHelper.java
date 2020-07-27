package io.natix.slix.prover;

import com.google.protobuf.InvalidProtocolBufferException;
import io.natix.slix.core.event.EventHandler;
import io.natix.slix.core.helper.DIDHelper;
import io.natix.slix.core.helper.LogHelper;
import io.natix.slix.core.metadata.EncryptionKind;
import io.natix.slix.core.metadata.PacketType;
import io.natix.slix.core.metadata.UserType;
import io.natix.slix.core.payload.*;
import io.natix.slix.core.payload.proto.Message;
import io.natix.slix.core.util.CryptoUtil;
import io.natix.slix.prover.config.ProverConfiguration;
import org.apache.commons.lang3.StringUtils;
import org.reflections.Reflections;
import org.springframework.stereotype.Component;
import org.zeromq.SocketType;
import org.zeromq.ZContext;
import org.zeromq.ZMQ;
import java.lang.reflect.InvocationTargetException;
import java.nio.channels.ClosedSelectorException;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.Executors;

@Component
public class ProverHelper implements DIDHelper {

    private static ZMQ.Socket PUBLISH_SOCKET;

    private static ZMQ.Socket REPLY_SOCKET;

    private static boolean LISTENING = true;

    public Optional<DIDDocument> registerToDID(String name, String ecdhPublicKey, String eddsaPublicKey, String ip) {
        String subscribe = "tcp://" + ip + ":" + ProverConfiguration.COMMUNICATION_CONFIGURER.getPublishPort();
        String reply = "tcp://" + ip + ":" + ProverConfiguration.COMMUNICATION_CONFIGURER.getReplyPort();
        PublicKey[] publicKeys = new PublicKey[]{
                new PublicKey(CryptoUtil.sha256(ecdhPublicKey), EncryptionKind.ECDH.name().toLowerCase(), ecdhPublicKey),
                new PublicKey(CryptoUtil.sha256(eddsaPublicKey), EncryptionKind.EDDSA.name().toLowerCase(), eddsaPublicKey)
        };
        Service[] services = new Service[]{
                new Service(UUID.randomUUID().toString(), "subscribe", subscribe),
                new Service(UUID.randomUUID().toString(), "reply", reply)
        };
        DIDDocumentRequest requestBody = new DIDDocumentRequest(name, publicKeys, services);
        return add(requestBody, UserType.PROVER);
    }

    public Optional<DIDDocument> loadDID(String id) {
        return findById(id);
    }

    private void startListen() {
        LISTENING = true;
        Executors.newFixedThreadPool(1).execute(this::listenOnReplyPort);
    }

    private void stopListen() {
        try {
            Thread.sleep(2000);
            LISTENING = false;
            if (PUBLISH_SOCKET != null) {
                PUBLISH_SOCKET.unbind(getPublishUri());
                PUBLISH_SOCKET.disconnect(getPublishUri());
                PUBLISH_SOCKET.close();
            }
            if (REPLY_SOCKET != null) {
                REPLY_SOCKET.unbind(getReplyUri());
                REPLY_SOCKET.disconnect(getReplyUri());
                REPLY_SOCKET.close();
            }
        } catch (Exception e) {
            LogHelper.error("StopException", e.getMessage());
        }
    }

    private String getPublishUri() {
        return "tcp://*:" + ProverConfiguration.COMMUNICATION_CONFIGURER.getPublishPort();
    }

    private String getReplyUri() {
        return "tcp://*:" + ProverConfiguration.COMMUNICATION_CONFIGURER.getReplyPort();
    }

    private ZMQ.Socket getPublishSocket() {
        try (ZContext context = new ZContext()) {
            PUBLISH_SOCKET = context.createSocket(SocketType.PUB);
            PUBLISH_SOCKET.bind(getPublishUri());
            LogHelper.info(StringUtils.EMPTY, "Connected to: " + getReplyUri());
            return PUBLISH_SOCKET;
        }
    }

    private void publish(String topic, String message) {
        getPublishSocket().sendMore(topic);
        getPublishSocket().send(message);
    }

    private void listenOnReplyPort() {
        try {
            ZContext context = new ZContext();
            REPLY_SOCKET = context.createSocket(SocketType.REP);
            REPLY_SOCKET.bind(getReplyUri());
            LogHelper.info(StringUtils.EMPTY, "Listening on: " + getReplyUri());
            while (LISTENING) {
                byte[] reply = REPLY_SOCKET.recv(0);
                parseMessage(reply);
                REPLY_SOCKET.send(StringUtils.EMPTY.getBytes(ZMQ.CHARSET), 0);
            }
        } catch (Exception e) {
            if (e instanceof ClosedSelectorException)
                return;
            LogHelper.error("ListenError", e.getMessage());
        }
    }

    private void parseMessage(byte[] reply) {
        try {
            LogHelper.info(StringUtils.EMPTY, "Received: [" + new String(reply, ZMQ.CHARSET) + "]");
            Set<Class<? extends EventHandler>> listeners = getReflection().getSubTypesOf(EventHandler.class);
            if (listeners == null || listeners.isEmpty())
                return;
            for (Class<? extends EventHandler> listener : listeners) {
                Message.EncryptedPacket encryptedPacket = Message.EncryptedPacket.parseFrom(reply);
                DIDDocument document = findById(encryptedPacket.getDid()).orElse(null);
                if (document == null)
                    continue;
                String packetId = CryptoUtil.sha256(encryptedPacket.getData().toString());
                PacketType packetType = PacketType.valueOf(encryptedPacket.getType());
                //TODO must be calc validity
                boolean validity = true;
                byte[] data = encryptedPacket.getData().toByteArray();
                EventHandler instance = listener.getDeclaredConstructor().newInstance();
                instance.onReceived(new Event(document, packetId, validity, packetType, data));
            }
        } catch (InvalidProtocolBufferException | InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            LogHelper.error("ParseMessageError", e.getMessage());
        }
    }

    private Reflections getReflection() {
        return new Reflections(getBasePackageName());
    }

    private String getBasePackageName() {
        String[] packages = getClass().getPackageName().split("\\.");
        return (packages.length <= 2) ? packages[0] : packages[0] + '.' + packages[1];
    }
}
