package io.natix.slix.core.exception;


import io.natix.slix.core.message.MessageHandler;
import io.natix.slix.core.metadata.HttpStatus;

public class NotImplementedException extends HttpException {
    @Override
    public HttpStatus getHttpStatus() {
        return HttpStatus.NOT_IMPLEMENTED;
    }

    public NotImplementedException(MessageHandler message) {
        super(message);
    }

    public NotImplementedException(String message) {
        super(message);
    }

    public NotImplementedException(ErrorMessage errorMessage) {
        super(new HttpErrorMessage(HttpStatus.NOT_IMPLEMENTED, errorMessage.getMessage(), errorMessage.getStatus(), errorMessage.getPath(), errorMessage.getTimestamp()));
    }
}
