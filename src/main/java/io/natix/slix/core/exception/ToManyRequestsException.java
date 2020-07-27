package io.natix.slix.core.exception;

import io.natix.slix.core.message.MessageHandler;
import io.natix.slix.core.metadata.HttpStatus;

public class ToManyRequestsException extends HttpException {
    @Override
    public HttpStatus getHttpStatus() {
        return HttpStatus.TOO_MANY_REQUESTS;
    }

    public ToManyRequestsException(MessageHandler message) {
        super(message);
    }

    public ToManyRequestsException(String message) {
        super(message);
    }

    public ToManyRequestsException(ErrorMessage errorMessage) {
        super(new HttpErrorMessage(HttpStatus.TOO_MANY_REQUESTS, errorMessage.getMessage(), errorMessage.getStatus(), errorMessage.getPath(), errorMessage.getTimestamp()));
    }
}