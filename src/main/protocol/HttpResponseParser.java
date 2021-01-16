package main.protocol;

import main.utils.HttpUtils;

import java.util.StringTokenizer;

public class HttpResponseParser {
    private final HttpResponse httpResponse = new HttpResponse();
    private boolean isFirstLine = true;
    private boolean stopFetchingHeaders = false;

    public void handleNewLine(String line) {
        System.err.println("RESPONSE_LINE:" + line + "!");

        if (line.isEmpty() && !stopFetchingHeaders) {
            stopFetchingHeaders = true;
            return;
        }

        if (isFirstLine) {
            StringTokenizer tokenizer = new StringTokenizer(line);
            String httpVersion = tokenizer.nextToken();
            String statusCode = tokenizer.nextToken();
            String statusMessage = tokenizer.nextToken("\r\n");

            httpResponse.setHttpVersion(httpVersion.trim());
            httpResponse.setStatusCode(Integer.parseInt(statusCode));
            httpResponse.setStatusMessage(statusMessage.trim());

            this.isFirstLine = false;
        } else if (!stopFetchingHeaders) {
            HttpHeader header = HttpUtils.extractHeader(line);
            if (header != null) {
                httpResponse.addHeader(header.getKey(), header.getValue());
            }
        } else {
            if (httpResponse.getBody() == null) {
                httpResponse.setBody("");
            }
            httpResponse.setBody(httpResponse.getBody() + line);
        }
    }

    public HttpResponse getHttpResponse() {
        return httpResponse;
    }
}
