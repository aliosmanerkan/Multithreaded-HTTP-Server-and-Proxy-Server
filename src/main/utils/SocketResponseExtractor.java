package main.utils;

import main.protocol.HttpRequest;
import main.protocol.HttpRequestParser;
import main.protocol.HttpResponse;
import main.protocol.HttpResponseParser;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Map;

public class SocketResponseExtractor {
    private final Socket socket;

    public SocketResponseExtractor(Socket socket) {
        this.socket = socket;
    }

    public HttpResponse extract() throws IOException {
        HttpResponseParser httpResponseParser = new HttpResponseParser();
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));

        // Wait until buffered reader is ready
        while (!bufferedReader.ready()) ;

        // Start to extract lines
        String line;
        boolean isEmptyLineReached = false;
        while ((line = bufferedReader.readLine()) != null) {
            if (line.equals("\r\n")) {
                if (isEmptyLineReached) {
                    break;
                } else {
                    isEmptyLineReached = true;
                }
            }
            httpResponseParser.handleNewLine(line);
        }

        // Get parsed http request
        HttpResponse httpResponse = httpResponseParser.getHttpResponse();

        // Print request
        System.out.printf("%s %s: %s %s %s\n", LocalDate.now(), LocalTime.now(), httpResponse.getStatusCode(), httpResponse.getStatusMessage(), (socket.getInetAddress() + ":" + socket.getPort()).replaceFirst("/", ""));
        for (Map.Entry<String, String> header : httpResponse.getHeaders().entrySet()) {
            System.out.printf("\t%s: %s\n", header.getKey(), header.getValue());
        }
        System.out.printf("\tBODY: %s\n", httpResponse.getBody());

        // Return request
        return httpResponse;
    }
}
