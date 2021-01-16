package main.protocol;

import main.utils.StatusCode;
import main.utils.Constants;
import main.utils.HttpUtils;

import java.util.HashMap;


public class HttpResponse {
    private String httpVersion = Constants.HTTP_VER_1_1;
    private int statusCode;
    private String statusMessage;
    private final HashMap<String, String> headers = new HashMap<>();
    private String body;

    public String getHttpVersion() {
        return httpVersion;
    }

    public HttpResponse setHttpVersion(String httpVersion) {
        this.httpVersion = httpVersion;
        return this;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public HttpResponse setStatusCode(int statusCode) {
        this.statusCode = statusCode;
        return this;
    }

    public HttpResponse setStatusCode(StatusCode statusCode) {
        this.statusCode = statusCode.getCode();
        this.statusMessage = HttpUtils.getStatusMessage(this.statusCode);
        return this;
    }

    public String getStatusMessage() {
        return statusMessage;
    }

    public HttpResponse setStatusMessage(String statusMessage) {
        this.statusMessage = statusMessage;
        return this;
    }

    public HashMap<String, String> getHeaders() {
        return headers;
    }

    public String getHeader(String key) {
        return headers.get(key.toLowerCase().trim());
    }

    public boolean hasHeader(String key) {
        return headers.get(key.toLowerCase().trim()) != null;
    }

    public HttpResponse addHeader(String key, String value) {
        headers.put(key, value);
        return this;
    }

    public String getBody() {
        return body;
    }

    public HttpResponse setBody(String body) {
        this.body = body;
        this.addHeader("content-length", String.valueOf(this.body.length()));
        return this;
    }

    public HttpResponse setBody(String body, String contentType) {
        this.setBody(body);
        this.setContentType(contentType);
        return this;
    }

    public HttpResponse setContentType(String contentType) {
        this.addHeader("content-type", contentType);
        return this;
    }

    public boolean hasBody() {
        return getBody() != null && getBody().trim().length() > 0;
    }
}