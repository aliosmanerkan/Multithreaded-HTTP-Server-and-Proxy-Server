package main.server;

import main.protocol.HttpRequest;
import main.protocol.HttpResponse;
import main.utils.SocketRequestExtractor;
import main.server.contracts.RequestInterpreter;
import main.utils.StatusCode;
import main.utils.SocketResponseWriter;

import java.net.Socket;

public class Session extends Thread {
    Socket socket = null;
    RequestInterpreter interpreter;

    public Session(RequestInterpreter interpreter, Socket socket) {
        this.interpreter = interpreter;
        this.socket = socket;
    }

    public void run() {
        try {
            HttpRequest request = new SocketRequestExtractor(socket).extract();
            HttpResponse response = new HttpResponse();
            try {
                response = this.interpreter.handle(request, response);
            } catch (Exception e) {
                response.setStatusCode(StatusCode.INTERNAL_SERVER_ERROR).setBody("Internal server error occurred.");
            }

            response.addHeader("connection", "close");
            response.addHeader("server", "Main Server");

            new SocketResponseWriter(socket, response).write();
            socket.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
