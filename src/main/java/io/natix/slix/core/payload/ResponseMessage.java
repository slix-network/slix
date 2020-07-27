package io.natix.slix.core.payload;

public class ResponseMessage implements Payload {
    private Integer statusCode;

    private String error;

    private String message;

    public Integer getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(Integer statusCode) {
        this.statusCode = statusCode;
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

    public ResponseMessage() {
    }

    public ResponseMessage(Integer statusCode, String error, String message) {
        this.statusCode = statusCode;
        this.error = error;
        this.message = message;
    }

    @Override
    public String toString() {
        return "RegistrationResponse{" +
                "statusCode=" + statusCode +
                ", error='" + error + '\'' +
                ", message='" + message + '\'' +
                '}';
    }
}
