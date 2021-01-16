package main.server;

import main.server.contracts.RequestInterpreter;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class Server implements Runnable {
    private final int port;
    private final RequestInterpreter interpreter;

    public Server(int port, RequestInterpreter interpreter) {
        this.port = port;
        this.interpreter = interpreter;
        new Thread(this).start();
    }

    @Override
    public void run() {
        try {
            ServerSocket serverSocket = new ServerSocket(port, 10, InetAddress.getByName("127.0.0.1"));

            System.out.println("Server started on http://localhost:" + port);

            while (true) {
                Socket connectedSocket = serverSocket.accept();
                (new Session(interpreter, connectedSocket)).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
