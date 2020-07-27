package io.natix.slix.core.exception;

import io.natix.slix.core.message.MessageHandler;
import io.natix.slix.core.metadata.HttpStatus;

;

public class NetworkAuthenticationRequiredException extends HttpException {
    @Override
    public HttpStatus getHttpStatus() {
        return HttpStatus.NETWORK_AUTHENTICATION_REQUIRED;
    }

    public NetworkAuthenticationRequiredException(MessageHandler message) {
        super(message);
    }

    public NetworkAuthenticationRequiredException(String message) {
        super(message);
    }

    public NetworkAuthenticationRequiredException(ErrorMessage errorMessage) {
        super(new HttpErrorMessage(HttpStatus.NETWORK_AUTHENTICATION_REQUIRED, errorMessage.getMessage(), errorMessage.getStatus(), errorMessage.getPath(), errorMessage.getTimestamp()));
    }

}
