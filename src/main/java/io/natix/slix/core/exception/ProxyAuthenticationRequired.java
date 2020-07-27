package io.natix.slix.core.exception;

import io.natix.slix.core.message.MessageHandler;
import io.natix.slix.core.metadata.HttpStatus;

public class ProxyAuthenticationRequired extends HttpException {
    @Override
    public HttpStatus getHttpStatus() {
        return HttpStatus.PROXY_AUTHENTICATION_REQUIRED;
    }

    public ProxyAuthenticationRequired(MessageHandler message) {
        super(message);
    }

    public ProxyAuthenticationRequired(String message) {
        super(message);
    }

    public ProxyAuthenticationRequired(ErrorMessage errorMessage) {
        super(new HttpErrorMessage(HttpStatus.PROXY_AUTHENTICATION_REQUIRED, errorMessage.getMessage(), errorMessage.getStatus(), errorMessage.getPath(), errorMessage.getTimestamp()));
    }
}