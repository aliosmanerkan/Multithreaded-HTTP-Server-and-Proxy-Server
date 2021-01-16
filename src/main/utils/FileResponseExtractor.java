package main.utils;

import main.protocol.HttpResponse;
import main.protocol.HttpResponseParser;

import java.io.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Map;

public class FileResponseExtractor {
    private final File file;

    public FileResponseExtractor(File file) {
        this.file = file;
    }

    public HttpResponse extract() throws IOException {
        HttpResponseParser httpResponseParser = new HttpResponseParser();
        BufferedReader bufferedReader = new BufferedReader(new FileReader(file));

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

        // Return request
        return httpResponseParser.getHttpResponse();
    }
}
