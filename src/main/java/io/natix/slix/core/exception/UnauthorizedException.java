package io.natix.slix.core.exception;

import io.natix.slix.core.message.MessageHandler;
import io.natix.slix.core.metadata.HttpStatus;

public class UnauthorizedException extends HttpException {
    @Override
    public HttpStatus getHttpStatus() {
        return HttpStatus.UNAUTHORIZED;
    }

    public UnauthorizedException(MessageHandler message) {
        super(message);
    }

    public UnauthorizedException(String message) {
        super(message);
    }

    public UnauthorizedException(ErrorMessage errorMessage) {
        super(new HttpErrorMessage(HttpStatus.UNAUTHORIZED, errorMessage.getMessage(), errorMessage.getStatus(), errorMessage.getPath(), errorMessage.getTimestamp()));
    }
}
