package io.natix.slix.requester;

import com.google.common.primitives.Bytes;
import com.google.protobuf.ByteString;
import io.natix.slix.core.helper.DIDHelper;
import io.natix.slix.core.metadata.EncryptionKind;
import io.natix.slix.core.metadata.QuestionType;
import io.natix.slix.core.metadata.UserType;
import io.natix.slix.core.payload.DIDDocument;
import io.natix.slix.core.payload.DIDDocumentRequest;
import io.natix.slix.core.payload.PublicKey;
import io.natix.slix.core.payload.proto.Message;
import io.natix.slix.core.util.CryptoUtil;
import io.natix.slix.core.util.StringUtil;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.Optional;

public interface RequesterHelper extends DIDHelper {

    default Optional<DIDDocument> registerToDID(String name, String ecdhPublicKey, String eddsaPublicKey) {
        PublicKey[] publicKeys = new PublicKey[]{
                new PublicKey(CryptoUtil.sha256(ecdhPublicKey), EncryptionKind.ECDH.name().toLowerCase(), ecdhPublicKey),
                new PublicKey(CryptoUtil.sha256(eddsaPublicKey), EncryptionKind.EDDSA.name().toLowerCase(), eddsaPublicKey)
        };
        DIDDocumentRequest requestBody = new DIDDocumentRequest(name, publicKeys, null);
        return add(requestBody, UserType.REQUESTER);
    }

    default Optional<DIDDocument> loadDID(String id) {
        return findById(id);
    }


}
