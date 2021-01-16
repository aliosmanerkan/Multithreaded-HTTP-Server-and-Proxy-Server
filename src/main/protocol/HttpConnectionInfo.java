package main.protocol;

public class HttpConnectionInfo {
    private String host;
    private int port;

    public HttpConnectionInfo(String host, int port) {
        this.host = host;
        this.port = port;
    }

    public String getHost() {
        return host;
    }

    public HttpConnectionInfo setHost(String host) {
        this.host = host;
        return this;
    }

    public int getPort() {
        return port;
    }

    public HttpConnectionInfo setPort(int port) {
        this.port = port;
        return this;
    }
}
