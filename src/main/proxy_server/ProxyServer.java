package main.proxy_server;

import main.protocol.HttpConnectionInfo;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class ProxyServer implements Runnable {
    private final int port;
    private final HttpConnectionInfo defaultServer;

    public ProxyServer(HttpConnectionInfo defaultServer, int port) {
        this.defaultServer = defaultServer;
        this.port = port;
        new Thread(this).start();
    }

    @Override
    public void run() {
        try {
            ServerSocket serverSocket = new ServerSocket(port, 10, InetAddress.getByName("127.0.0.1"));

            System.out.println("Proxy server started on http://localhost:" + port);

            while (true) {
                Socket connectedSocket = serverSocket.accept();
                (new ProxySession(this.defaultServer, connectedSocket)).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
