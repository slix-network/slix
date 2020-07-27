package io.natix.slix.core.exception;

import io.natix.slix.core.message.MessageHandler;
import io.natix.slix.core.metadata.HttpStatus;

public class UnSupportMediaTypeException extends HttpException {
    @Override
    public HttpStatus getHttpStatus() {
        return HttpStatus.UNSUPPORTED_MEDIA_TYPE;
    }

    public UnSupportMediaTypeException(MessageHandler message) {
        super(message);
    }

    public UnSupportMediaTypeException(String message) {
        super(message);
    }

    public UnSupportMediaTypeException(ErrorMessage errorMessage) {
        super(new HttpErrorMessage(HttpStatus.UNSUPPORTED_MEDIA_TYPE, errorMessage.getMessage(), errorMessage.getStatus(), errorMessage.getPath(), errorMessage.getTimestamp()));
    }
}
