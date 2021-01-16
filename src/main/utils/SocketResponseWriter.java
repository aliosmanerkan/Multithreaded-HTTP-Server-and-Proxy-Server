package main.utils;

import main.protocol.HttpResponse;

import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import java.util.Map;

public class SocketResponseWriter {
    private final Socket socket;
    private final HttpResponse response;

    public SocketResponseWriter(Socket socket, HttpResponse response) {
        this.socket = socket;
        this.response = response;
    }

    public void write() throws IOException {
        this.write(null);
    }

    public void write(File cacheFile) throws IOException {
        PrintStream proxyOut = new PrintStream(this.socket.getOutputStream());
        PrintStream fileOut = null;
        if (cacheFile != null) {
            fileOut = new PrintStream(cacheFile);
        }

        proxyOut.printf("%s %s %s\r\n", response.getHttpVersion(), response.getStatusCode(), response.getStatusMessage());
        if (fileOut != null) {
            fileOut.printf("%s %s %s\r\n", response.getHttpVersion(), response.getStatusCode(), response.getStatusMessage());
        }
        for (Map.Entry<String, String> header : response.getHeaders().entrySet()) {
            proxyOut.printf("%s: %s\n", header.getKey(), header.getValue());
            if (fileOut != null) {
                fileOut.printf("%s: %s\n", header.getKey(), header.getValue());
            }
        }
        proxyOut.print("\r\n");
        if (fileOut != null) {
            fileOut.print("\r\n");
        }
        if (response.hasBody()) {
            proxyOut.printf("%s", response.getBody());
            if (fileOut != null) {
                fileOut.printf("%s", response.getBody());
            }
        }

        proxyOut.close();
        if (fileOut != null) {
            fileOut.close();
        }
    }
}
