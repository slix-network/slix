package io.natix.slix.core.exception;

import io.natix.slix.core.message.MessageHandler;
import io.natix.slix.core.metadata.HttpStatus;

public class RollBackException extends HttpException {
    @Override
    public HttpStatus getHttpStatus() {
        return HttpStatus.CONFLICT;
    }

    public RollBackException(MessageHandler message) {
        super(message);
    }

    public RollBackException(String message) {
        super(message);
    }

    public RollBackException(ErrorMessage errorMessage) {
        super(new HttpErrorMessage(HttpStatus.CONFLICT, errorMessage.getMessage(), errorMessage.getStatus(), errorMessage.getPath(), errorMessage.getTimestamp()));
    }
}
