package main.protocol;

import java.util.HashMap;

public class HttpRequest {
    String method = null;
    String httpVersion = null;
    String host = null;
    int port = -1;
    String path = null;
    String body;
    HashMap<String, String> headers = new HashMap<>();

    public String getMethod() {
        return method;
    }

    public HttpRequest setMethod(String method) {
        this.method = method;
        return this;
    }

    public String getHttpVersion() {
        return httpVersion;
    }

    public HttpRequest setHttpVersion(String httpVersion) {
        this.httpVersion = httpVersion;
        return this;
    }

    public String getPath() {
        return path;
    }

    public HttpRequest setPath(String path) {
        this.path = path;
        return this;
    }

    public HttpRequest addHeader(String key, String value) {
        headers.put(key, value);
        return this;
    }

    public String getBody() {
        return body;
    }

    public HttpRequest setBody(String body) {
        this.body = body;
        this.addHeader("content-length", String.valueOf(this.body.length()));
        return this;
    }

    public HashMap<String, String> getHeaders() {
        return headers;
    }

    public String getHeader(String key) {
        return headers.get(key.toLowerCase().trim());
    }

    public boolean hasBody() {
        return getBody() != null && getBody().trim().length() > 0;
    }

    public String getHost() {
        return host;
    }

    public HttpRequest setHost(String host) {
        this.host = host;
        return this;
    }

    public int getPort() {
        return port;
    }

    public HttpRequest setPort(int port) {
        this.port = port;
        return this;
    }
}
