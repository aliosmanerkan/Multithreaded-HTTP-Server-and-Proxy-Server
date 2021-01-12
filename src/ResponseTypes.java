public enum ResponseTypes {
    OK(200),
    BAD_REQUEST(400),
    NOT_IMPLEMENTED(501),
    REQUEST_URI_TOO_LONG(414),
    NOT_FOUND(404),
    ;

    private int code;

    ResponseTypes(int code) {
        this.code = code;
    }

    public int getValue() {
        return code;
    }
}
