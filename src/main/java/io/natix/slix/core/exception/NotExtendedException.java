package io.natix.slix.core.exception;


import io.natix.slix.core.message.MessageHandler;
import io.natix.slix.core.metadata.HttpStatus;

public class NotExtendedException extends HttpException {
    @Override
    public HttpStatus getHttpStatus() {
        return HttpStatus.NOT_EXTENDED;
    }

    public NotExtendedException(MessageHandler message) {
        super(message);
    }

    public NotExtendedException(String message) {
        super(message);
    }

    public NotExtendedException(ErrorMessage errorMessage) {
        super(new HttpErrorMessage(HttpStatus.NOT_EXTENDED, errorMessage.getMessage(), errorMessage.getStatus(), errorMessage.getPath(), errorMessage.getTimestamp()));
    }

}
