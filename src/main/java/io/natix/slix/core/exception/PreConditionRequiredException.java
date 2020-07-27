package io.natix.slix.core.exception;

import io.natix.slix.core.message.MessageHandler;
import io.natix.slix.core.metadata.HttpStatus;

public class PreConditionRequiredException extends HttpException {
    @Override
    public HttpStatus getHttpStatus() {
        return HttpStatus.PRECONDITION_REQUIRED;
    }

    public PreConditionRequiredException(MessageHandler message) {
        super(message);
    }

    public PreConditionRequiredException(String message) {
        super(message);
    }

    public PreConditionRequiredException(ErrorMessage errorMessage) {
        super(new HttpErrorMessage(HttpStatus.PRECONDITION_REQUIRED, errorMessage.getMessage(), errorMessage.getStatus(), errorMessage.getPath(), errorMessage.getTimestamp()));
    }
}
