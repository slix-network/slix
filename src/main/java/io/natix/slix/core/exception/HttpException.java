package io.natix.slix.core.exception;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.natix.slix.core.message.MessageHandler;
import io.natix.slix.core.metadata.HttpStatus;

import java.io.Serializable;
import java.util.Map;

public class HttpException extends Exception implements IHttpException {

    private final HttpErrorMessage errorMessage;

    public HttpException(MessageHandler message) {
        super(message.getMessage());
        errorMessage = new HttpErrorMessage(getHttpStatus(), message.getMessage(), message.getCode());
    }

    public HttpException(String message) {
        super(message);
        errorMessage = new HttpErrorMessage(getHttpStatus(), message, null);
    }

    public HttpException(HttpErrorMessage errorMessage) {
        super(errorMessage.getMessage());
        this.errorMessage = errorMessage;
    }

    public void setPath(String path) {
        if (errorMessage != null)
            errorMessage.setPath(path);
    }

    public HttpErrorMessage getErrorMessage() {
        return errorMessage;
    }

    public HttpStatus getHttpStatus() {
        return HttpStatus.NOT_IMPLEMENTED;
    }

    public Map<String, Serializable> body() {
        return errorMessage.body();
    }

    @Override
    public String toString() {
        return toString(errorMessage);
    }

    protected String toString(Object value) {
        try {
            ObjectMapper Obj = new ObjectMapper();
            return Obj.writeValueAsString(value);
        } catch (JsonProcessingException e) {
            return "";
        }
    }
}
