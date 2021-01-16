package main.protocol;

public class HttpHeader {
    private String key;
    private String value;

    public HttpHeader(String key, String value) {
        this.key = key;
        this.value = value;
    }

    public String getKey() {
        return key;
    }

    public HttpHeader setKey(String key) {
        this.key = key;
        return this;
    }

    public String getValue() {
        return value;
    }

    public HttpHeader setValue(String value) {
        this.value = value;
        return this;
    }
}
