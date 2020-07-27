package io.natix.slix.core.exception;

import com.google.common.collect.ImmutableMap;
import io.natix.slix.core.message.MessageHelper;
import io.natix.slix.core.metadata.HttpStatus;
import io.natix.slix.core.payload.Payload;
import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;
import java.util.Date;
import java.util.Map;

public class HttpErrorMessage implements Payload {
    private Date timestamp;

    private HttpStatus httpStatus;

    private Integer status;

    private String error;

    private String message;

    private String path;

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    public void setHttpStatus(HttpStatus httpStatus) {
        this.httpStatus = httpStatus;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public HttpErrorMessage() {
    }

    public HttpErrorMessage(HttpStatus httpStatus, String message) {
        this(httpStatus, message, null, StringUtils.EMPTY);
    }

    public HttpErrorMessage(HttpStatus httpStatus, String message, Integer status) {
        this(httpStatus, message, status, StringUtils.EMPTY);
    }

    public HttpErrorMessage(HttpStatus httpStatus, String message, Integer status, String path) {
        this(httpStatus, message, status, path, new Date());
    }

    public HttpErrorMessage(HttpStatus httpStatus, String message, Integer status, String path, Date timestamp) {
        String translatedMessage = MessageHelper.getMessage(message);
        status = status == null ? MessageHelper.getCode(message) : status;
        this.httpStatus = httpStatus;
        this.timestamp = timestamp;
        this.message = StringUtils.isEmpty(translatedMessage) ? message.replace('_', ' ').toLowerCase() : translatedMessage;
        this.status = Integer.valueOf(String.valueOf(httpStatus.value()) + (status == null ? StringUtils.EMPTY : status));
        this.error = httpStatus.getReasonPhrase();
        this.path = path;
    }

    public Map<String, Serializable> body() {
        return ImmutableMap.of(
                "timestamp", timestamp,
                "status", status,
                "error", error,
                "message", message,
                "path", path
        );
    }

    @Override
    public String toString() {
        return "ErrorMessage{" +
                "timestamp=" + timestamp +
                ", httpStatus=" + httpStatus +
                ", status=" + status +
                ", error='" + error + '\'' +
                ", message='" + message + '\'' +
                ", path='" + path + '\'' +
                '}';
    }
}
