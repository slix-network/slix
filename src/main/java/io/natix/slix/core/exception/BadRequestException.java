package io.natix.slix.core.exception;


import io.natix.slix.core.message.MessageHandler;
import io.natix.slix.core.metadata.HttpStatus;

public class BadRequestException extends HttpException {
    @Override
    public HttpStatus getHttpStatus() {
        return HttpStatus.BAD_REQUEST;
    }

    public BadRequestException(MessageHandler message) {
        super(message);
    }

    public BadRequestException(String message) {
        super(message);
    }

    public BadRequestException(ErrorMessage errorMessage) {
        super(new HttpErrorMessage(HttpStatus.BAD_REQUEST, errorMessage.getMessage(), errorMessage.getStatus(), errorMessage.getPath(), errorMessage.getTimestamp()));
    }
}
