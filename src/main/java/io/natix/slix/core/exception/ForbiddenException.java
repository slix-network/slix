package io.natix.slix.core.exception;


import io.natix.slix.core.message.MessageHandler;
import io.natix.slix.core.metadata.HttpStatus;

public class ForbiddenException  extends HttpException {
    @Override
    public HttpStatus getHttpStatus() {
        return HttpStatus.FORBIDDEN;
    }

    public ForbiddenException(MessageHandler message) {
        super(message);
    }

    public ForbiddenException(String message) {
        super(message);
    }

    public ForbiddenException(ErrorMessage errorMessage) {
        super(new HttpErrorMessage(HttpStatus.FORBIDDEN, errorMessage.getMessage(), errorMessage.getStatus(), errorMessage.getPath(), errorMessage.getTimestamp()));
    }
}