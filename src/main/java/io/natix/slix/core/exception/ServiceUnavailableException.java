package io.natix.slix.core.exception;

import io.natix.slix.core.message.MessageHandler;
import io.natix.slix.core.metadata.HttpStatus;

public class ServiceUnavailableException extends HttpException {
    @Override
    public HttpStatus getHttpStatus() {
        return HttpStatus.SERVICE_UNAVAILABLE;
    }

    public ServiceUnavailableException(MessageHandler message) {
        super(message);
    }

    public ServiceUnavailableException(String message) {
        super(message);
    }

    public ServiceUnavailableException(ErrorMessage errorMessage) {
        super(new HttpErrorMessage(HttpStatus.SERVICE_UNAVAILABLE, errorMessage.getMessage(), errorMessage.getStatus(), errorMessage.getPath(), errorMessage.getTimestamp()));
    }
}
