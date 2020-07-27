package io.natix.slix.core.helper;

import com.squareup.okhttp.*;
import io.natix.slix.core.config.DIDConfiguration;
import io.natix.slix.core.metadata.EncryptionKind;
import io.natix.slix.core.metadata.ServiceKind;
import io.natix.slix.core.metadata.UserType;
import io.natix.slix.core.payload.DIDDocumentRequest;
import io.natix.slix.core.payload.DIDDocument;
import io.natix.slix.core.util.JsonUtil;

import java.io.IOException;
import java.util.Arrays;
import java.util.Optional;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

public interface DIDHelper {

    default Optional<DIDDocument> add(DIDDocumentRequest request, UserType kind) {
        try {
            OkHttpClient client = new OkHttpClient();
            client.setConnectTimeout(90, TimeUnit.SECONDS);
            client.setReadTimeout(90, TimeUnit.SECONDS);
            String APPLICATION_JSON = "application/json";
            MediaType mediaType = MediaType.parse(APPLICATION_JSON);
            RequestBody body = RequestBody.create(mediaType, JsonUtil.toString(request));
            Request httpRequest = new Request.Builder().url(DIDConfiguration.DID_CONFIGURER.getUri() + "/users/" + kind).method("POST", body).addHeader("Content-Type", APPLICATION_JSON).build();
            Response response = client.newCall(httpRequest).execute();
            if (response.isSuccessful())
                return Optional.ofNullable(JsonUtil.toObject(response.body().string(), DIDDocument.class));
            return Optional.empty();
        } catch (IOException e) {
            LogHelper.error("DID Register Error: ", e.getMessage());
            return Optional.empty();
        }
    }

    default Optional<DIDDocument> findById(String id) {
        try {
            OkHttpClient client = new OkHttpClient();
            client.setConnectTimeout(90, TimeUnit.SECONDS);
            client.setReadTimeout(90, TimeUnit.SECONDS);
            String APPLICATION_JSON = "application/json";
            Request request = new Request.Builder().url(DIDConfiguration.DID_CONFIGURER.getUri() + "/users/" + id).method("GET", null).addHeader("Content-Type", APPLICATION_JSON).build();
            Response response = client.newCall(request).execute();
            if (response.isSuccessful())
                return Optional.ofNullable(JsonUtil.toObject(response.body().string(), DIDDocument.class));
            return Optional.empty();
        } catch (IOException e) {
            LogHelper.error("DID Load Error: ", e.getMessage());
            return Optional.empty();
        }
    }

    default Optional<DIDDocument> findByIdAndType(String id, UserType type) {
        try {
            OkHttpClient client = new OkHttpClient();
            client.setConnectTimeout(90, TimeUnit.SECONDS);
            client.setReadTimeout(90, TimeUnit.SECONDS);
            String APPLICATION_JSON = "application/json";
            Request request = new Request.Builder().url(DIDConfiguration.DID_CONFIGURER.getUri() + "/users/" + id).method("GET", null).addHeader("Content-Type", APPLICATION_JSON).build();
            Response response = client.newCall(request).execute();
            if (response.isSuccessful()) {
                DIDDocument document = JsonUtil.toObject(response.body().string(), DIDDocument.class);
                return document != null && type.name().equalsIgnoreCase(document.getType()) ? Optional.of(document) : Optional.empty();
            }
            return Optional.empty();
        } catch (IOException e) {
            LogHelper.error("DID Load Error: ", e.getMessage());
            return Optional.empty();
        }
    }

    default Optional<String> findPublicKey(String id, UserType userType, EncryptionKind encryptionKind) {
        DIDDocument document = findByIdAndType(id, userType).orElse(null);
        return document == null ? Optional.empty() : findPublicKey(document, encryptionKind);
    }

    default Optional<String> findPublicKey(DIDDocument document, EncryptionKind encryptionKind) {
        AtomicReference<Optional<String>> response = new AtomicReference<>(Optional.empty());
        if (document.getPublicKey() != null)
            Arrays.stream(document.getPublicKey()).filter(x -> encryptionKind.name().equalsIgnoreCase(x.getType())).findFirst().ifPresent(p -> response.set(Optional.ofNullable(p.getPublicKey())));
        return response.get();
    }

    default Optional<String> findServiceEndpoint(String id, UserType userType, ServiceKind serviceKind) {
        DIDDocument document = findByIdAndType(id, userType).orElse(null);
        return document == null ? Optional.empty() : findServiceEndpoint(document, serviceKind);
    }

    default Optional<String> findServiceEndpoint(DIDDocument document, ServiceKind serviceKind) {
        AtomicReference<Optional<String>> response = new AtomicReference<>(Optional.empty());
        if (document.getService() != null)
            Arrays.stream(document.getService()).filter(x -> serviceKind.name().equalsIgnoreCase(x.getType())).findFirst().ifPresent(p -> response.set(Optional.ofNullable(p.getServiceEndpoint())));
        return response.get();
    }
}
