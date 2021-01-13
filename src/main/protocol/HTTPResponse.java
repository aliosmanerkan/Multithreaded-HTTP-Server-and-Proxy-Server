package main.protocol;

import main.types.StatusCodes;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.*;
import java.util.HashMap;
import java.util.Map;


public class HTTPResponse {
    private final Socket client;
    private final HashMap<String, String> headers = new HashMap<>();
    private String body = "";
    private String status = "";

    public HTTPResponse(Socket client) {
        this.client = client;
    }

    public HTTPResponse setBody(String body) {
        this.body = body;
        this.addHeader("content-length", String.valueOf(this.body.length()));
        return this;
    }

    public HTTPResponse addHeader(String key, String value) {
        this.headers.put(key, value);
        return this;
    }

    public HTTPResponse setContentType(String type) {
        this.addHeader("content-type", type);
        return this;
    }

    public HTTPResponse setStatusCode(StatusCodes code) throws Exception {
        switch (code) {
            case OK:
                this.status = "HTTP/1.1 200 OK";
                break;
            case NOT_FOUND:
                this.status = "HTTP/1.1 404 NOT FOUND";
                break;
            case BAD_REQUEST:
                this.status = "HTTP/1.1 400 BAD REQUEST";
                break;
            case NOT_IMPLEMENTED:
                this.status = "HTTP/1.1 501 NOT IMPLEMENTED";
                break;
            case REQUEST_URI_TOO_LONG:
                this.status = "HTTP/1.1 414 REQUEST URI TOO LONG";
                break;
            default:
                throw new Exception(String.format("Unkown status code \"%s\"", code.toString()));
        }
        return this;
    }

    public HTTPResponse end() throws IOException {
        DataOutputStream outputStream = new DataOutputStream(this.client.getOutputStream());

        // Set status code
        outputStream.writeBytes(String.format("%s\r\n", this.status));

        // Set headers
        for (Map.Entry<String, String> header : this.headers.entrySet()) {
            outputStream.writeBytes(String.format("%s: %s\r\n", header.getKey().toLowerCase(), header.getValue()));
        }

        // Set trailing headers
        outputStream.writeBytes("Connection: close\r\n");
        outputStream.writeBytes("\r\n");

        // Set body
        outputStream.writeBytes(this.body);

        outputStream.close();
        this.client.close();

        return this;
    }

}