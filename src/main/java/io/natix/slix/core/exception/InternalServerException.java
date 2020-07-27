package io.natix.slix.core.exception;


import io.natix.slix.core.message.MessageHandler;
import io.natix.slix.core.metadata.HttpStatus;

public class InternalServerException extends HttpException {
    @Override
    public HttpStatus getHttpStatus() {
        return HttpStatus.INTERNAL_SERVER_ERROR;
    }

    public InternalServerException(MessageHandler message) {
        super(message);
    }

    public InternalServerException(String message) {
        super(message);
    }

    public InternalServerException(ErrorMessage errorMessage) {
        super(new HttpErrorMessage(HttpStatus.INTERNAL_SERVER_ERROR, errorMessage.getMessage(), errorMessage.getStatus(), errorMessage.getPath(), errorMessage.getTimestamp()));
    }
}
