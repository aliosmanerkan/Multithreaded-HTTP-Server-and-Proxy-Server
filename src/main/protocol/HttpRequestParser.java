package main.protocol;

import main.utils.HttpUtils;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.StringTokenizer;

public class HttpRequestParser {
    private final HttpRequest httpRequest = new HttpRequest();
    private boolean isFirstLine = true;

    public void handleNewLine(String line) {
        if (isFirstLine) {
            StringTokenizer tokenizer = new StringTokenizer(line);
            String method = tokenizer.nextToken();
            String path = tokenizer.nextToken();

            httpRequest.setMethod(method);

            if(path.startsWith("/")) {
                path = path.replaceFirst("/", "");
            }

            if (path.startsWith("http")) {
                try {
                    path = path.replaceAll("%2F", "/");

                    URL url = new URL(path);
                    httpRequest.setHost(url.getHost());

                    String p = url.getPath();
                    if(p.startsWith("/")) {
                        p = p.replaceFirst("/", "");
                    }
                    httpRequest.setPath(p);

                    if (url.getPort() == -1) {
                        if (path.startsWith("https")) {
                            httpRequest.setPort(443);
                        } else {
                            httpRequest.setPort(80);
                        }
                    } else {
                        httpRequest.setPort(url.getPort());
                    }


                    System.err.println("PATH:" + httpRequest.getPath());
                    System.err.println("HOST:" + httpRequest.getHost());
                    System.err.println("PORT:" + httpRequest.getPort());

                } catch (MalformedURLException e) {
                    e.printStackTrace();
                    httpRequest.setPath(path);
                }
            } else {
                httpRequest.setPath(path);
            }

            this.isFirstLine = false;
        } else {
            HttpHeader header = HttpUtils.extractHeader(line);
            if (header != null) {
                httpRequest.addHeader(header.getKey(), header.getValue());
            } else {
                httpRequest.setBody(line);
            }
        }
    }

    public HttpRequest getHttpRequest() {
        return httpRequest;
    }
}
