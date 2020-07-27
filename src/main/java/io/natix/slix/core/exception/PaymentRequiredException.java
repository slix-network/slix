package io.natix.slix.core.exception;


import io.natix.slix.core.message.MessageHandler;
import io.natix.slix.core.metadata.HttpStatus;

public class PaymentRequiredException extends HttpException {
    @Override
    public HttpStatus getHttpStatus() {
        return HttpStatus.PAYMENT_REQUIRED;
    }

    public PaymentRequiredException(MessageHandler message) {
        super(message);
    }

    public PaymentRequiredException(String message) {
        super(message);
    }

    public PaymentRequiredException(ErrorMessage errorMessage) {
        super(new HttpErrorMessage(HttpStatus.PAYMENT_REQUIRED, errorMessage.getMessage(), errorMessage.getStatus(), errorMessage.getPath(), errorMessage.getTimestamp()));
    }
}
