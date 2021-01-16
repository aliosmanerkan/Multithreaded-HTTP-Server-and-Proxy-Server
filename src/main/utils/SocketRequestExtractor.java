package main.utils;

import main.protocol.HttpRequest;
import main.protocol.HttpRequestParser;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Map;

public class SocketRequestExtractor {
    private final Socket socket;

    public SocketRequestExtractor(Socket socket) {
        this.socket = socket;
    }

    public HttpRequest extract() throws IOException {
        HttpRequestParser httpRequestParser = new HttpRequestParser();
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));

        // Wait until buffered reader is ready
        while (!bufferedReader.ready()) ;

        // Start to extract lines
        String line;
        while ((line = bufferedReader.readLine()) != null) {
            if (line.isEmpty()) {
                break;
            }
            httpRequestParser.handleNewLine(line);
        }

        // Get parsed http request
        HttpRequest httpRequest = httpRequestParser.getHttpRequest();

        // Print request
        System.out.printf("\n\u001B[32m%s %s: %s /%s\u001B[0m %s\n", LocalDate.now(), LocalTime.now(), httpRequest.getMethod(), httpRequest.getPath(), (socket.getInetAddress() + ":" + socket.getPort()).replaceFirst("/", ""));
        for (Map.Entry<String, String> header : httpRequest.getHeaders().entrySet()) {
            System.out.printf("\t%s: %s\n", header.getKey(), header.getValue());
        }
        System.out.printf("\tBODY: %s\n", httpRequest.getBody());

        // Return request
        return httpRequestParser.getHttpRequest();
    }
}
