package io.natix.slix.core.exception;


import io.natix.slix.core.message.MessageHandler;
import io.natix.slix.core.metadata.HttpStatus;

public class ConflictException extends HttpException {
    @Override
    public HttpStatus getHttpStatus() {
        return HttpStatus.CONFLICT;
    }

    public ConflictException(MessageHandler message) {
        super(message);
    }

    public ConflictException(String message) {
        super(message);
    }

    public ConflictException(ErrorMessage errorMessage) {
        super(new HttpErrorMessage(HttpStatus.CONFLICT, errorMessage.getMessage(), errorMessage.getStatus(), errorMessage.getPath(), errorMessage.getTimestamp()));
    }
}
