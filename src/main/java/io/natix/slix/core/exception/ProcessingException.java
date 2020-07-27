package io.natix.slix.core.exception;

import io.natix.slix.core.message.MessageHandler;
import io.natix.slix.core.metadata.HttpStatus;

public class ProcessingException  extends HttpException {
    @Override
    public HttpStatus getHttpStatus() {
        return HttpStatus.PROCESSING;
    }

    public ProcessingException(MessageHandler message) {
        super(message);
    }

    public ProcessingException(String message) {
        super(message);
    }

    public ProcessingException(ErrorMessage errorMessage) {
        super(new HttpErrorMessage(HttpStatus.PROCESSING, errorMessage.getMessage(), errorMessage.getStatus(), errorMessage.getPath(), errorMessage.getTimestamp()));
    }
}