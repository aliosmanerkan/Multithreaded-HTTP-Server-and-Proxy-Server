package main.utils;

import main.protocol.HttpRequest;

import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import java.util.Map;

public class SocketRequestWriter {
    private final Socket socket;
    private final HttpRequest request;

    public SocketRequestWriter(Socket socket, HttpRequest request) {
        this.socket = socket;
        this.request = request;
    }

    public void write() throws IOException {
        PrintStream proxyOut = new PrintStream(this.socket.getOutputStream());

        String path = request.getPath();

        if (!path.startsWith("/")) {
            path = String.format("/%s", path);
        }

        proxyOut.printf("%s %s %s\r\n", request.getMethod(), path, Constants.HTTP_VER_1_1);
        for (Map.Entry<String, String> header : request.getHeaders().entrySet()) {
            proxyOut.printf("%s: %s\r\n", header.getKey(), header.getValue());
        }
        proxyOut.print("\r\n");
        if (request.hasBody()) {
            proxyOut.printf("%s\r\n", request.getBody());
        }
    }
}
