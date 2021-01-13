package main;

import main.types.StatusCodes;
import main.utils.HtmlResponseGenerator;

import java.io.*;
import java.net.Socket;

public class Main extends Thread {
    public static void main(String args[]) throws Exception {

        // Main server
        new Thread(() -> {
            try {
                new Server(5000, (request, response) -> {

                    String method = request.getMethod();
                    String requestString = request.getRequestString();
                    String httpQueryString = request.getHttpQueryString();

                    return response
                            .setContentType("text/html")
                            .addHeader("cache-control", "max-age:5000")
                            .setBody(HtmlResponseGenerator.generateByByteCount(500))
                            .setStatusCode(StatusCodes.OK);
                });
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();

        // Proxy server
        new Thread(() -> {
            try {
                new Server(5001, (request, response) -> {

                    Socket socket = new Socket("127.0.0.1", 5000);
                    // Create input and output streams to read from and write to the server
                    PrintStream out = new PrintStream(socket.getOutputStream());
                    BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

                    // Follow the HTTP protocol of GET <path> HTTP/1.0 followed by an empty line
                    out.println("GET " + request.getHttpQueryString() + " HTTP/1.0");
                    out.println();

                    // Read data from the server until we finish reading the document
                    String line = in.readLine();
                    String responseText = line;
                    while (line != null) {
                        System.out.println("proxy" + line);
                        line = in.readLine();
                        responseText += line + "\n";
                    }

                    // Close our streams
                    in.close();
                    out.close();
                    socket.close();

                    return response
                            .setContentType("text/html")
                            .setBody(responseText)
                            .setStatusCode(StatusCodes.OK);
                });
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();

    }
}