package io.natix.slix.core.exception;


import io.natix.slix.core.metadata.HttpStatus;
import java.io.Serializable;
import java.util.Map;

public interface IHttpException {

    HttpErrorMessage getErrorMessage();

    void setPath(String path);

    HttpStatus getHttpStatus();

    Map<String, Serializable> body();

    String toString();
}
