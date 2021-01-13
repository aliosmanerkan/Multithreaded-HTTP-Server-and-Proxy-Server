package main;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;

public class Server {
    public Server(int port, RequestInterpreter interpreter) throws IOException {
        ServerSocket Server = null;
        Server = new ServerSocket(port, 10, InetAddress.getByName("127.0.0.1"));

        System.out.println("TCPServer Waiting for client on port " + port);

        while (true) {
            Socket connected = Server.accept();
            (new Session(interpreter, connected)).start();
        }
    }
}
