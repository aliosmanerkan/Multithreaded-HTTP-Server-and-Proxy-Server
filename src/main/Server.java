package main;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;

public class Server {
    public Server(RequestInterpreter interpreter) throws IOException {
        ServerSocket Server = null;
        Server = new ServerSocket(5000, 10, InetAddress.getByName("127.0.0.1"));

        System.out.println("TCPServer Waiting for client on port 5000");

        while (true) {
            Socket connected = Server.accept();
            (new Session(interpreter, connected)).start();
        }
    }
}
