package io.natix.slix.core.exception;

import io.natix.slix.core.message.MessageHandler;
import io.natix.slix.core.metadata.HttpStatus;

public class UpgradeRequiredException extends HttpException {
    @Override
    public HttpStatus getHttpStatus() {
        return HttpStatus.UPGRADE_REQUIRED;
    }

    public UpgradeRequiredException(MessageHandler message) {
        super(message);
    }

    public UpgradeRequiredException(String message) {
        super(message);
    }

    public UpgradeRequiredException(ErrorMessage errorMessage) {
        super(new HttpErrorMessage(HttpStatus.UPGRADE_REQUIRED, errorMessage.getMessage(), errorMessage.getStatus(), errorMessage.getPath(), errorMessage.getTimestamp()));
    }
}