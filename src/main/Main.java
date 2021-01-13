package main;

import main.types.StatusCodes;

public class Main extends Thread {
    public static void main(String args[]) throws Exception {

        new Server((request, response) -> {

            String method = request.getMethod();
            String requestString = request.getRequestString();
            String httpQueryString = request.getHttpQueryString();

            return response
                    .setBody("Test" + "</br>Method: " + method + "</br>Request String:" + requestString + "</br>Http Request String" + httpQueryString)
                    .setStatusCode(StatusCodes.OK);
        });

    }
}