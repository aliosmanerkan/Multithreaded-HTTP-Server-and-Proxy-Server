package main;

import main.protocol.HttpRequest;
import main.types.StatusCodes;
import main.utils.MessageUtils;

import java.io.*;
import java.net.*;

public class Main extends Thread {

    static final String HTML_START =
            "<html>" +
                    "<title>HTTP Server in java</title>" +
                    "<body>";

    static final String HTML_END =
            "</body>" +
                    "</html>";

    Socket connectedClient = null;
    DataOutputStream outToClient = null;

    public Main(Socket client) {
        connectedClient = client;
    }

    public void run() {

        try {
            System.out.println("The Client " + connectedClient.getInetAddress() + ":" + connectedClient.getPort() + " is connected");

            HttpRequest httpRequest = new HttpRequest(connectedClient);

            //after here alios' business :)

            outToClient = new DataOutputStream(connectedClient.getOutputStream());
            StringBuffer responseBuffer = new StringBuffer();
            responseBuffer.append("<b> This is the HTTP Server Home Page.... </b><BR>");
            responseBuffer.append("The HTTP Client request is ....<BR>");
            responseBuffer.append(httpRequest.getRequestString()).append("<BR>");

            //todo: delete this - for test purposes
            System.out.println("RESPONSE STATUS CODE TO SEND " + MessageUtils.getResponseStatusCode(httpRequest.getMethod(), httpRequest.getHttpQueryString()).getValue());

            // requested byte size can obtained with MessageUtils.getRequestedByteSize(query)
            sendResponse(MessageUtils.getResponseStatusCode(httpRequest.getMethod(), httpRequest.getHttpQueryString()),
                    responseBuffer.toString(), false);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void sendResponse(StatusCodes statusCode, String responseString, boolean isFile) throws Exception {
        //todo: send response according to status code

        String statusLine = null;
        String serverdetails = "Server: Java HTTPServer";
        String contentLengthLine = null;
        String fileName = null;
        String contentTypeLine = "Content-Type: text/html" + "\r\n";
        FileInputStream fin = null;

        if (statusCode == StatusCodes.OK)
            statusLine = "HTTP/1.1 200 OK" + "\r\n";
        else
            statusLine = "HTTP/1.1 404 Not Found" + "\r\n";

        if (isFile) {
            fileName = responseString;
            fin = new FileInputStream(fileName);
            contentLengthLine = "Content-Length: " + Integer.toString(fin.available()) + "\r\n";
            if (!fileName.endsWith(".htm") && !fileName.endsWith(".html"))
                contentTypeLine = "Content-Type: \r\n";
        } else {
            responseString = Main.HTML_START + responseString + Main.HTML_END;
            contentLengthLine = "Content-Length: " + responseString.length() + "\r\n";
        }

        outToClient.writeBytes(statusLine);
        outToClient.writeBytes(serverdetails);
        outToClient.writeBytes(contentTypeLine);
        outToClient.writeBytes(contentLengthLine);
        outToClient.writeBytes("Connection: close\r\n");
        outToClient.writeBytes("\r\n");

        if (isFile) sendFile(fin, outToClient);
        else outToClient.writeBytes(responseString);

        outToClient.close();
    }

    public void sendFile(FileInputStream fin, DataOutputStream out) throws Exception {
        byte[] buffer = new byte[1024];
        int bytesRead;

        while ((bytesRead = fin.read(buffer)) != -1) {
            out.write(buffer, 0, bytesRead);
        }
        fin.close();
    }

    public static void main(String args[]) throws Exception {

        //String portNumber = args[0];
        //ServerSocket Server = new ServerSocket(Integer.parseInt(args[0]));
        //System.out.println("TCPServer Waiting for client on port " + portNumber);

        ServerSocket Server = new ServerSocket (5000, 10, InetAddress.getByName("127.0.0.1"));
        System.out.println("TCPServer Waiting for client on port 5000");

        while (true) {
            Socket connected = Server.accept();
            (new Main(connected)).start();
        }
    }
}