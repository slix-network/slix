package io.natix.slix.core.exception;


import io.natix.slix.core.message.MessageHandler;
import io.natix.slix.core.metadata.HttpStatus;

public class NotAcceptableException extends HttpException {
    @Override
    public HttpStatus getHttpStatus() {
        return HttpStatus.NOT_ACCEPTABLE;
    }

    public NotAcceptableException(MessageHandler message) {
        super(message);
    }

    public NotAcceptableException(String message) {
        super(message);
    }

    public NotAcceptableException(ErrorMessage errorMessage) {
        super(new HttpErrorMessage(HttpStatus.NOT_ACCEPTABLE, errorMessage.getMessage(), errorMessage.getStatus(), errorMessage.getPath(), errorMessage.getTimestamp()));
    }
}
