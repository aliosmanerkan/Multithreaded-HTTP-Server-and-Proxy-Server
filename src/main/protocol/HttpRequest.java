package main.protocol;

import main.utils.MessageUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.HashMap;
import java.util.StringTokenizer;

public class HttpRequest {
    private Socket socket;
    private BufferedReader bufferedReader = null;
    private String method;
    private String httpQueryString;
    private String requestString;
    private HashMap<String, String> headerPairs;

    public HttpRequest(Socket socket) {
        this.socket = socket;
        headerPairs = new HashMap<>();

        try {
            init();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void init() throws IOException {
        bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        requestString = bufferedReader.readLine();

        // read header values
        if (bufferedReader.readLine() != null) {
            StringTokenizer tokenizer = new StringTokenizer(requestString);
            method = tokenizer.nextToken();   // get http method
            httpQueryString = tokenizer.nextToken(); // get http query
        }

        System.out.println("THE HTTP REQUEST .....");

        // read complete http
        while (bufferedReader.ready()) {
            System.out.println(requestString);
            requestString = bufferedReader.readLine();
            headerPairs = MessageUtils.getKeyValuePair(headerPairs, requestString);
        }
    }

    public String getMethod() {
        return method;
    }

    public String getHttpQueryString() {
        return httpQueryString;
    }

    public String getRequestString() {
        return requestString;
    }

    public String getHeaderValue(String key) {
        for (HashMap.Entry<String, String> entry : headerPairs.entrySet()) {
            if (entry.getKey().equals(key.toLowerCase())) {
                return entry.getValue();
            }
        }
        return null;
    }
}
