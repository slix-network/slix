package io.natix.slix.core.exception;


import io.natix.slix.core.message.MessageHandler;
import io.natix.slix.core.metadata.HttpStatus;

public class PayloadTooLargeException extends HttpException {
    @Override
    public HttpStatus getHttpStatus() {
        return HttpStatus.PAYLOAD_TOO_LARGE;
    }

    public PayloadTooLargeException(MessageHandler message) {
        super(message);
    }

    public PayloadTooLargeException(String message) {
        super(message);
    }

    public PayloadTooLargeException(ErrorMessage errorMessage) {
        super(new HttpErrorMessage(HttpStatus.PAYLOAD_TOO_LARGE, errorMessage.getMessage(), errorMessage.getStatus(), errorMessage.getPath(), errorMessage.getTimestamp()));
    }
}
