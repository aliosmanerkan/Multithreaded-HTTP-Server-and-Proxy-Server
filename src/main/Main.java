package main;

import main.proxy_server.ProxyServer;
import main.protocol.HttpConnectionInfo;
import main.server.Server;
import main.utils.StatusCode;
import main.utils.HttpUtils;

import static main.utils.Constants.CONTENT_TYPE_HTML;

public class Main extends Thread {
    public static void main(String[] args) throws Exception {
        int serverPort = Integer.parseInt(args[0]);

        // Main server
        new Server(serverPort, (request, response) -> {
            String method = request.getMethod();

            switch (method) {
                case "GET":
                    break;
                case "OPTIONS":
                case "POST":
                case "PUT":
                case "PATCH":
                case "DELETE":
                case "COPY":
                case "HEAD":
                case "LINK":
                case "UNLINK":
                case "PURGE":
                case "LOCK":
                case "UNLOCK":
                case "PROPFIND":
                case "VIEW":
                    return response
                            .setBody("Requested method is not supported.")
                            .setStatusCode(StatusCode.NOT_IMPLEMENTED);
                default:
                    return response
                            .setBody("Requested method is invalid.")
                            .setStatusCode(StatusCode.BAD_REQUEST);
            }

            try {
                int number = Integer.parseInt(request.getPath());

                if (number < 100) {
                    return response
                            .setBody("Requested file size should be greater than 100.")
                            .setStatusCode(StatusCode.BAD_REQUEST);
                }

                if (number > 20000) {
                    return response
                            .setBody("Requested file size should be less than 20000.")
                            .setStatusCode(StatusCode.BAD_REQUEST);
                }

                return response
                        .setBody(HttpUtils.generateBodyByByteCount(number), CONTENT_TYPE_HTML)
                        .setStatusCode(StatusCode.OK);
            } catch (Exception e) {
                return response
                        .setBody("Wrong request")
                        .setStatusCode(StatusCode.BAD_REQUEST);
            }
        });

        // Proxy server
        new ProxyServer(new HttpConnectionInfo("127.0.0.1", serverPort), 5001);

    }
}