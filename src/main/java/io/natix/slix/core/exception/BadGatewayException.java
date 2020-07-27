package io.natix.slix.core.exception;


import io.natix.slix.core.message.MessageHandler;
import io.natix.slix.core.metadata.HttpStatus;

public class BadGatewayException extends HttpException {
    @Override
    public HttpStatus getHttpStatus() {
        return HttpStatus.BAD_GATEWAY;
    }

    public BadGatewayException(MessageHandler message) {
        super(message);
    }

    public BadGatewayException(String message) {
        super(message);
    }

    public BadGatewayException(ErrorMessage errorMessage) {
        super(new HttpErrorMessage(HttpStatus.BAD_GATEWAY, errorMessage.getMessage(), errorMessage.getStatus(), errorMessage.getPath(), errorMessage.getTimestamp()));
    }
}
