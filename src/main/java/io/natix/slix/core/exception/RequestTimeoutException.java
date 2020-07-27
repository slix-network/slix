package io.natix.slix.core.exception;

import io.natix.slix.core.message.MessageHandler;
import io.natix.slix.core.metadata.HttpStatus;

public class RequestTimeoutException extends HttpException {
    @Override
    public HttpStatus getHttpStatus() {
        return HttpStatus.REQUEST_TIMEOUT;
    }

    public RequestTimeoutException(MessageHandler message) {
        super(message);
    }

    public RequestTimeoutException(String message) {
        super(message);
    }

    public RequestTimeoutException(ErrorMessage errorMessage) {
        super(new HttpErrorMessage(HttpStatus.REQUEST_TIMEOUT, errorMessage.getMessage(), errorMessage.getStatus(), errorMessage.getPath(), errorMessage.getTimestamp()));
    }
}
