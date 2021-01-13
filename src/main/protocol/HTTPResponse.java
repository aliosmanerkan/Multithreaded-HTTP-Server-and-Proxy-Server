package main.protocol;

import main.types.StatusCodes;

import java.io.*;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.*;
import java.util.StringTokenizer;


public class HTTPResponse {

    BufferedReader inFromClient = null;
    DataOutputStream out = null;
    File returnHtml;
    private Socket socket;
    public HTTPResponse(Socket socket) {
        this.socket = socket;
    }

    public void setBody(int byteNumber) throws IOException {
        int i = 0;
        String body;
        long filesize;

        body = "<!DOCTYPE html>\n " +
                "<html>\n " +
                "<head> \n" +
                "<title>\n" + "this file " + byteNumber + " length " + "</title> " +
                "</head>\n " +
                "<body>\n" +
                "<p>\n";

        while (i < (byteNumber - 113)) {
            body = body + "a";
            i++;
        }
        body += "\t</p>\n" +
                "\t\n" +
                "\t</body>\n" +
                "</html>";

        out.writeBytes(body);
    }

    public void addheader(int byteNumber,String responseString) throws Exception{
        out = null;
        String serverdetails = "Multithread HTTPServer";
        String contentLengthLine = null;
        String contentTypeLine = "Content-Type: text/html" + "\r\n";
        contentLengthLine = "Content-Length: " + byteNumber + "\r\n";

        out.writeBytes(serverdetails);
        out.writeBytes(contentTypeLine);
        out.writeBytes(contentLengthLine);
        out.writeBytes("Connection: close\r\n");
        out.writeBytes("\r\n");

        out.close();
    }

    public void setStatusCode(StatusCodes code) throws IOException {
        String statusLine;
        switch(code){
            case OK:
                statusLine = "HTTP/1.1 200 OK";
                break;
            case NOT_FOUND:
                statusLine = "HTTP/1.1 404 NOT FOUND";
                break;
            case BAD_REQUEST:
                statusLine = "HTTP/1.1 400 BAD REQUEST";
                break;
            case NOT_IMPLEMENTED:
                statusLine = "HTTP/1.1 501 NOT IMPLEMENTED";
                break;
            case REQUEST_URI_TOO_LONG:
                statusLine = "HTTP/1.1 414 REQUEST URI TOO LONG";
                break;
            default:
                statusLine = "HTTP/1.1 404 NOT FOUND";
                break;

        }

        out.writeBytes(statusLine);
    }

    public void send(){

    }

}