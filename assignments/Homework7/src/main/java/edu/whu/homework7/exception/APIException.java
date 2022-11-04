package edu.whu.homework7.exception;

public class APIException extends Exception {
    private String message;

    public APIException(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public static APIException badRequest(String message) {
        return new APIException(message);
    }
}
