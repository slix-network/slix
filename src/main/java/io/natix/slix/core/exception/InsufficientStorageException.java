package io.natix.slix.core.exception;


import io.natix.slix.core.message.MessageHandler;
import io.natix.slix.core.metadata.HttpStatus;

public class InsufficientStorageException extends HttpException {
    @Override
    public HttpStatus getHttpStatus() {
        return HttpStatus.INSUFFICIENT_STORAGE;
    }

    public InsufficientStorageException(MessageHandler message) {
        super(message);
    }

    public InsufficientStorageException(String message) {
        super(message);
    }

    public InsufficientStorageException(ErrorMessage errorMessage) {
        super(new HttpErrorMessage(HttpStatus.INSUFFICIENT_STORAGE, errorMessage.getMessage(), errorMessage.getStatus(), errorMessage.getPath(), errorMessage.getTimestamp()));
    }

}
