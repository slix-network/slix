package io.natix.slix.core.exception;

import io.natix.slix.core.message.MessageHandler;
import io.natix.slix.core.metadata.HttpStatus;

public class NotFoundException extends HttpException {
    @Override
    public HttpStatus getHttpStatus() {
        return HttpStatus.NOT_FOUND;
    }

    public NotFoundException(MessageHandler message) {
        super(message);
    }

    public NotFoundException(String message) {
        super(message);
    }

    public NotFoundException(ErrorMessage errorMessage) {
        super(new HttpErrorMessage(HttpStatus.NOT_FOUND, errorMessage.getMessage(), errorMessage.getStatus(), errorMessage.getPath(), errorMessage.getTimestamp()));
    }
}
