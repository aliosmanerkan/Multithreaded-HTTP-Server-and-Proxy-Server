package main.proxy_server;

import main.protocol.HttpRequest;
import main.protocol.HttpConnectionInfo;
import main.utils.*;
import main.protocol.HttpResponse;

import java.io.File;
import java.io.IOException;
import java.net.Socket;

public class ProxySession extends Thread {
    private Socket socket = null;
    private HttpConnectionInfo defaultServer;

    public ProxySession(HttpConnectionInfo defaultServer, Socket socket) {
        this.defaultServer = defaultServer;
        this.socket = socket;
    }

    public void run() {
        try {
            HttpRequest request = new SocketRequestExtractor(socket).extract();
            try {

                try {
                    File file = new File("cache");
                    if (!file.exists()) {
                        file.mkdir();
                    }
                } catch (Exception e) {
                    System.out.println("An error occurred.");
                    e.printStackTrace();
                    if (true)
                        return;
                }


                File cacheFile = new File("cache/" + request.getPath() + ".cache");

                if (cacheFile.exists()) {

                    try {
                        int number = Integer.parseInt(request.getPath());

                        if (number % 2 == 1) {
                            HttpResponse response = new HttpResponse();
                            response.setStatusCode(StatusCode.NOT_MODIFIED);
                            response.addHeader("server", "Proxy Server");
                            new SocketResponseWriter(socket, response).write(cacheFile);
                            return;
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    HttpResponse response = new FileResponseExtractor(cacheFile).extract();
                    response.addHeader("server", "Proxy Server");
                    new SocketResponseWriter(socket, response).write(cacheFile);
                    return;
                } else {
                    cacheFile.createNewFile();
                }

                Socket proxySocket;
                try {
                    String host = request.getHost() != null ? request.getHost() : this.defaultServer.getHost();
                    int port = request.getPort() != -1 ? request.getPort() : this.defaultServer.getPort();

                    System.err.println("proxying to " + host + ":" + port);

                    proxySocket = new Socket(host, port);
                } catch (Exception e) {
                    new SocketResponseWriter(socket, new HttpResponse()
                            .setStatusCode(StatusCode.NOT_FOUND)
                            .setBody("Not found!")
                            .addHeader("server", "Proxy Server")
                    ).write();
                    return;
                }

                new SocketRequestWriter(proxySocket, request).write();

                try {
                    int number = Integer.parseInt(request.getPath());

                    if (number > 9999) {
                        new SocketResponseWriter(socket, new HttpResponse()
                                .setStatusCode(StatusCode.REQUEST_URI_TOO_LONG)
                                .setBody("Requested file size should be maximum 9999.")
                                .addHeader("server", "Proxy Server")
                        ).write();
                        return;
                    }
                } catch (Exception ignored) {
                }

                HttpResponse response = new SocketResponseExtractor(proxySocket).extract();
                response.addHeader("server", "Proxy Server");
                new SocketResponseWriter(socket, response).write(cacheFile);

                proxySocket.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
