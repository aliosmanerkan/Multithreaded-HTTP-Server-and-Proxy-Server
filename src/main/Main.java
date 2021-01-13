package main;

import main.types.StatusCodes;
import main.utils.HtmlResponseGenerator;

public class Main extends Thread {
    public static void main(String args[]) throws Exception {

        new Server((request, response) -> {

            String method = request.getMethod();
            String requestString = request.getRequestString();
            String httpQueryString = request.getHttpQueryString();

            return response
                    .setContentType("text/html")
                    .addHeader("cache-control", "max-age:5000")
                    .setBody(HtmlResponseGenerator.generateByByteCount(500))
                    .setStatusCode(StatusCodes.OK);
        });

    }
}