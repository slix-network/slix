package io.natix.slix.core.helper;

import com.google.common.primitives.Bytes;
import com.google.protobuf.ByteString;
import com.google.protobuf.InvalidProtocolBufferException;
import io.natix.slix.core.metadata.ConfirmStatus;
import io.natix.slix.core.metadata.PacketType;
import io.natix.slix.core.metadata.QuestionType;
import io.natix.slix.core.payload.proto.Message;
import io.natix.slix.core.util.CryptoUtil;
import io.natix.slix.core.util.StringUtil;

import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.Set;

public interface SerializeHelper {

    default Message.RequesterPacket createRequesterPacket(String privateKey, String requesterDID, String proverDID, QuestionType type, String[] queryArgs) {
        Message.Query query = createQuery(requesterDID, proverDID, type, queryArgs);
        Message.Signature signature = createSignature(privateKey, query.toByteArray());
        return Message.RequesterPacket.newBuilder().setQuery(query).setSignature(signature).build();
    }

    default Message.SubjectPacket createSubjectPacket(String privateKey, Message.RequesterPacket requesterPacket, String subjectDID, String proverDID, Long startValidationTime, Long endValidationTime, Long validityToken, String bundleId, String[] answerArgs) {
        Message.Answer answer = createAnswer(subjectDID, proverDID, startValidationTime, endValidationTime, validityToken, bundleId, answerArgs);
        byte[] data = Bytes.concat(requesterPacket.toByteArray(), answer.toByteArray());
        Message.Signature signature = createSignature(privateKey, data);
        return Message.SubjectPacket.newBuilder().setRequesterPacket(requesterPacket).setAnswer(answer).setSignature(signature).build();
    }

    default Message.ProverPacket createProverPacket(String privateKey, Message.SubjectPacket subjectPacket, Message.Confirmation[] previousConfirmations, Message.Signature[] previousSignatures, String proverDID, ConfirmStatus confirmStatus, Long startValidationTime, Long endValidationTime, Long validityToken, String[] answerArgs) {
        return Message.ProverPacket.newBuilder().build();
    }

    default Message.EncryptedPacket encryptPacket(PacketType type, String did, byte[] data) {
        return Message.EncryptedPacket.newBuilder().setDid(did).setType(type.getValue()).setData(ByteString.copyFrom(data)).build();
    }

    default Message.EncryptedPacket decryptPacket(byte[] msg) {
        try {
            return Message.EncryptedPacket.parseFrom(msg);
        } catch (InvalidProtocolBufferException e) {
            LogHelper.error("ProtocolBufferException", e.getMessage());
            return null;
        }
    }

    private Message.Signature createSignature(String privateKey, byte[] msg) {
        long time = new Date().getTime();
        byte[] timeBytes = Long.toString(time).getBytes(StandardCharsets.UTF_8);
        byte[] array = Bytes.concat(msg, timeBytes);
        byte[] data = CryptoUtil.signEddsa(privateKey, array);
        return Message.Signature.newBuilder().setTime(time).setData(StringUtil.encodeHexString(data)).build();
    }

    private Message.Query createQuery(String requesterDID, String proverDID, QuestionType type, String[] queryArgs) {
        var response = Message.Query.newBuilder().setRequesterDID(requesterDID).setProverDID(proverDID).setType(type.getValue());
        if (queryArgs != null && queryArgs.length > 0)
            response.addAllQueryArgs(Set.of(queryArgs));
        return response.build();
    }

    private Message.Answer createAnswer(String subjectDID, String proverDID, Long startValidationTime, Long endValidationTime, Long validityToken, String bundleId, String[] answerArgs) {
        var response = Message.Answer.newBuilder().setSubjectDID(subjectDID).setProverDID(proverDID).setBundleId(bundleId);
        if (startValidationTime != null)
            response.setStartValidationTime(startValidationTime);
        if (endValidationTime != null)
            response.setEndValidationTime(endValidationTime);
        if (validityToken != null)
            response.setValidityToken(validityToken);
        if (answerArgs != null && answerArgs.length > 0)
            response.addAllAnswerArgs(Set.of(answerArgs));
        return response.build();
    }

}
