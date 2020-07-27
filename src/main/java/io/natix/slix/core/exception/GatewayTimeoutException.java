package io.natix.slix.core.exception;


import io.natix.slix.core.message.MessageHandler;
import io.natix.slix.core.metadata.HttpStatus;

public class GatewayTimeoutException extends HttpException {
    @Override
    public HttpStatus getHttpStatus() {
        return HttpStatus.GATEWAY_TIMEOUT;
    }

    public GatewayTimeoutException(MessageHandler message) {
        super(message);
    }

    public GatewayTimeoutException(String message) {
        super(message);
    }

    public GatewayTimeoutException(ErrorMessage errorMessage) {
        super(new HttpErrorMessage(HttpStatus.GATEWAY_TIMEOUT, errorMessage.getMessage(), errorMessage.getStatus(), errorMessage.getPath(), errorMessage.getTimestamp()));
    }
}
