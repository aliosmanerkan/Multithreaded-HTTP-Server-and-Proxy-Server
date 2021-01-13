package main;

import main.protocol.HTTPResponse;
import main.protocol.HttpRequest;
import main.types.StatusCodes;

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
            System.out.println("The Client " + socket.getInetAddress() + ":" + socket.getPort() + " is connected");
            HttpRequest request = new HttpRequest(socket);
            HTTPResponse response = new HTTPResponse(socket);
            //todo: delete - for test purposes
            System.out.println("TEST upper->" + request.getHeaderValue("Accept"));
            System.out.println("TEST lower->" + request.getHeaderValue("accept"));
            this.interpreter.handle(request, response).end();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
