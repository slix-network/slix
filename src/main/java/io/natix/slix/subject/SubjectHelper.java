package io.natix.slix.subject;

import com.google.protobuf.ByteString;
import com.google.protobuf.InvalidProtocolBufferException;
import io.natix.slix.core.context.AppContextProvider;
import io.natix.slix.core.event.EventHandler;
import io.natix.slix.core.exception.HttpErrorMessage;
import io.natix.slix.core.exception.HttpException;
import io.natix.slix.core.exception.NotFoundException;
import io.natix.slix.core.exception.ServiceUnavailableException;
import io.natix.slix.core.helper.DIDHelper;
import io.natix.slix.core.helper.LogHelper;
import io.natix.slix.core.helper.SerializeHelper;
import io.natix.slix.core.helper.ZMQHelper;
import io.natix.slix.core.metadata.*;
import io.natix.slix.core.payload.*;
import io.natix.slix.core.payload.proto.Message;
import io.natix.slix.core.util.CryptoUtil;
import io.natix.slix.core.util.JsonUtil;
import io.natix.slix.core.util.StringUtil;
import io.natix.slix.subject.metadata.Messages;
import io.natix.slix.subject.model.SubjectDetail;
import io.natix.slix.subject.payload.ProverDID;
import io.natix.slix.subject.service.SubjectLoaderService;
import org.apache.commons.lang3.StringUtils;
import java.nio.charset.StandardCharsets;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;

public interface SubjectHelper extends DIDHelper, ZMQHelper, SerializeHelper {

    default Optional<DIDDocument> registerToDID(String name, String ecdhPublicKey, String eddsaPublicKey) {
        PublicKey[] publicKeys = new PublicKey[]{
                new PublicKey(CryptoUtil.sha256(ecdhPublicKey), EncryptionKind.ECDH.name().toLowerCase(), ecdhPublicKey),
                new PublicKey(CryptoUtil.sha256(eddsaPublicKey), EncryptionKind.EDDSA.name().toLowerCase(), eddsaPublicKey)
        };
        DIDDocumentRequest requestBody = new DIDDocumentRequest(name, publicKeys, null);
        return add(requestBody, UserType.SUBJECT);
    }

    default Optional<DIDDocument> loadDID(String id) {
        return findById(id);
    }

    default Optional<DIDDocument> loadDID(String id, UserType userType) {
        return findByIdAndType(id, userType);
    }

    default ProverDID getProverDID(String did) throws NotFoundException {
        DIDDocument document = findByIdAndType(did, UserType.PROVER).orElseThrow(() -> new NotFoundException(Messages.PROVER_NOT_FOUND));
        String replyUri = findServiceEndpoint(document, ServiceKind.REPLY).orElseThrow(() -> new NotFoundException(Messages.PROVER_NOT_FOUND));
        String subscribeUri = findServiceEndpoint(document, ServiceKind.SUBSCRIBE).orElseThrow(() -> new NotFoundException(Messages.PROVER_NOT_FOUND));
        String ecdhPublicKey = findPublicKey(document, EncryptionKind.ECDH).orElseThrow(() -> new NotFoundException(Messages.PROVER_NOT_FOUND));
        String eddsaPublicKey = findPublicKey(document, EncryptionKind.EDDSA).orElseThrow(() -> new NotFoundException(Messages.PROVER_NOT_FOUND));
        return new ProverDID(document.getId(), document.getType(), document.getName(), replyUri, subscribeUri, ecdhPublicKey, eddsaPublicKey, document.getStatus());
    }

    default void connectToProver(String did, EventHandler event) throws NotFoundException {
        ProverDID proverDID = getProverDID(did);

        SubjectDetail subject = getCurrentSubject();
        if (subject == null || StringUtils.isBlank(subject.getDid()) || loadDID(subject.getDid(), UserType.SUBJECT).isEmpty())
            throw new NotFoundException(Messages.SUBJECT_MUST_BE_REGISTER_IN_DID);
        subscribe(proverDID.getSubscribeUri(), subject.getDid(), (topic, msg) -> {
            try {
                Message.EncryptedPacket encryptedPacket = decryptPacket(msg);
                DIDDocument doc = findByIdAndType(encryptedPacket.getDid(), UserType.PROVER).orElse(null);
                if (doc == null)
                    return;
                String packetId = CryptoUtil.sha256(encryptedPacket.getData().toString());
                PacketType packetType = PacketType.valueOf(encryptedPacket.getType());
                //TODO must be calc validity
                boolean validity = false;
                byte[] data = CryptoUtil.ecdhDecrypt(subject.getEcdh().getPrivateKey(), proverDID.getEcdhPublicKey(), encryptedPacket.getData().toByteArray());
                event.onReceived(new Event(doc, packetId, validity, packetType, data));
            } catch (Exception e) {
                LogHelper.error("OnReceivedMessage", e.getMessage());
            }
        });
    }

    default <Subject extends SubjectDetail> Optional<Subject> findCurrentSubject() {
        Optional<Subject> subject = (Optional<Subject>) getSubjectLoader().load();
        return subject;
    }

    default <Subject extends SubjectDetail> Subject getCurrentSubject() throws NotFoundException {
        return (Subject) findCurrentSubject().orElseThrow(() -> new NotFoundException(Messages.SUBJECT_MUST_BE_REGISTER_IN_DID));
    }

    default Form getProverForm(String did) throws HttpException {
        ProverDID prover = getProverDID(did);
        SubjectDetail subject = getCurrentSubject();
        CustomMessage customMessage = new CustomMessage("GET_FORMS");
        byte[] encypted = CryptoUtil.ecdhEncrypt(subject.getEcdh().getPrivateKey(), prover.getEcdhPublicKey(), JsonUtil.toString(customMessage));
        Message.EncryptedPacket encryptedPacket = encryptPacket(PacketType.Custom, subject.getDid(), encypted);
        AtomicReference<byte[]> callback = new AtomicReference<>();
        send(prover.getReplyUri(), encryptedPacket.toByteArray(), callback::set);
        Message.EncryptedPacket callbackPacket = decryptPacket(callback.get());
        String strResponse = new String(Objects.requireNonNull(CryptoUtil.ecdhDecrypt(subject.getEcdh().getPrivateKey(), prover.getEcdhPublicKey(), callbackPacket.getData().toByteArray())), StandardCharsets.UTF_8);
        ResponseMessage message = JsonUtil.toObject(strResponse, ResponseMessage.class);
        if (message != null && message.getStatusCode() != null) {
            if (message.getStatusCode() == 200)
                return JsonUtil.toObject(message.getMessage(), Form.class);
            else
                throw new HttpException(new HttpErrorMessage(HttpStatus.valueOf(message.getStatusCode()), message.getMessage()));
        }
        throw new ServiceUnavailableException(Messages.PROVER_SERVICE_IS_UNAVAILABLE);
    }

    default boolean registerToProver(String did, RegistrationRequest request) throws HttpException {
        ProverDID prover = getProverDID(did);
        SubjectDetail subject = getCurrentSubject();
        CustomMessage customMessage = new CustomMessage("REGISTER_SUBJECT", JsonUtil.toString(request));
        byte[] data = CryptoUtil.ecdhEncrypt(subject.getEcdh().getPrivateKey(), prover.getEcdhPublicKey(), JsonUtil.toString(customMessage));
        Message.EncryptedPacket encryptedPacket = encryptPacket(PacketType.Custom, subject.getDid(), data);
        AtomicReference<byte[]> callback = new AtomicReference<>();
        send(prover.getReplyUri(), encryptedPacket.toByteArray(), callback::set);
        Message.EncryptedPacket callbackPacket = decryptPacket(callback.get());
        String strResponse = new String(Objects.requireNonNull(CryptoUtil.ecdhDecrypt(subject.getEcdh().getPrivateKey(), prover.getEcdhPublicKey(), callbackPacket.getData().toByteArray())), StandardCharsets.UTF_8);
        ResponseMessage message = JsonUtil.toObject(strResponse, ResponseMessage.class);
        if (message != null && message.getStatusCode() != null) {
            if (message.getStatusCode() == 200) {
                return true;
            } else
                throw new HttpException(new HttpErrorMessage(HttpStatus.valueOf(message.getStatusCode()), message.getMessage()));
        }
        throw new ServiceUnavailableException(Messages.PROVER_SERVICE_IS_UNAVAILABLE);
    }

    default ReadRegistrationStatusResponse readSubjectStatus(String did, ReadRegistrationStatusRequest request) throws HttpException {
        ProverDID prover = getProverDID(did);
        SubjectDetail subject = getCurrentSubject();
        CustomMessage customMessage = new CustomMessage("GET_SUBJECT_STATUS", JsonUtil.toString(request));
        byte[] data = CryptoUtil.ecdhEncrypt(subject.getEcdh().getPrivateKey(), prover.getEcdhPublicKey(), JsonUtil.toString(customMessage));
        Message.EncryptedPacket encryptedPacket = encryptPacket(PacketType.Custom, subject.getDid(), data);
        AtomicReference<byte[]> callback = new AtomicReference<>();
        send(prover.getReplyUri(), encryptedPacket.toByteArray(), callback::set);
        Message.EncryptedPacket callbackPacket = decryptPacket(callback.get());
        String strResponse = new String(Objects.requireNonNull(CryptoUtil.ecdhDecrypt(subject.getEcdh().getPrivateKey(), prover.getEcdhPublicKey(), callbackPacket.getData().toByteArray())), StandardCharsets.UTF_8);
        ResponseMessage message = JsonUtil.toObject(strResponse, ResponseMessage.class);
        if (message != null && message.getStatusCode() != null) {
            if (message.getStatusCode() == 200) {
                return JsonUtil.toObject(message.getMessage(), ReadRegistrationStatusResponse.class);
            } else
                throw new HttpException(new HttpErrorMessage(HttpStatus.valueOf(message.getStatusCode()), message.getMessage()));
        }
        throw new ServiceUnavailableException(Messages.PROVER_SERVICE_IS_UNAVAILABLE);
    }



    private SubjectLoaderService<? extends SubjectDetail> getSubjectLoader() {
        return AppContextProvider.context.getBean(SubjectLoaderService.class);
    }

}
