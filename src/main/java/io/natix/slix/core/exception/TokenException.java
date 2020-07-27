package io.natix.slix.core.exception;

import io.natix.slix.core.message.MessageHandler;
import io.natix.slix.core.metadata.HttpStatus;

public class TokenException extends HttpException {
    @Override
    public HttpStatus getHttpStatus() {
        return HttpStatus.UNAUTHORIZED;
    }

    public TokenException(MessageHandler message) {
        super(message);
    }

    public TokenException(String message) {
        super(message);
    }

    public TokenException(ErrorMessage errorMessage) {
        super(new HttpErrorMessage(HttpStatus.UNAUTHORIZED, errorMessage.getMessage(), errorMessage.getStatus(), errorMessage.getPath(), errorMessage.getTimestamp()));
    }
}
