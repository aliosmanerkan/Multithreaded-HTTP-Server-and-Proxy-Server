package main.utils;

public enum StatusCode {
    OK(200),
    NOT_MODIFIED(304),
    BAD_REQUEST(400),
    INTERNAL_SERVER_ERROR(500),
    NOT_IMPLEMENTED(501),
    REQUEST_URI_TOO_LONG(414),
    NOT_FOUND(404);

    private int code;

    StatusCode(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }
}
