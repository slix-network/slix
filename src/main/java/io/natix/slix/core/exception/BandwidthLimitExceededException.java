package io.natix.slix.core.exception;

import io.natix.slix.core.message.MessageHandler;
import io.natix.slix.core.metadata.HttpStatus;

public class BandwidthLimitExceededException extends HttpException {
    @Override
    public HttpStatus getHttpStatus() {
        return HttpStatus.BANDWIDTH_LIMIT_EXCEEDED;
    }

    public BandwidthLimitExceededException(MessageHandler message) {
        super(message);
    }

    public BandwidthLimitExceededException(String message) {
        super(message);
    }

    public BandwidthLimitExceededException(ErrorMessage errorMessage) {
        super(new HttpErrorMessage(HttpStatus.BANDWIDTH_LIMIT_EXCEEDED, errorMessage.getMessage(), errorMessage.getStatus(), errorMessage.getPath(), errorMessage.getTimestamp()));
    }

}
