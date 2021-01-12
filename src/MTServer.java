import java.io.*;
import java.net.*;

public class MTServer {

    ServerSocket server_socket;
    public int PORT = 80;
    PrintWriter print_writer;
    BufferedReader buffered_reader;

    public static void main(String[] args) throws Exception {

        MTServer web_server = new MTServer();
        web_server.runServer();

    }

    public void runServer() throws Exception {

        System.out.println("Multi-Threaded Web Server has now Started");
        System.out.println("Enter \"localhost:" + PORT + "/abm523.html\" in your web browser.");
        server_socket = new ServerSocket(PORT);
        processHTTPRequest();

    }

    public void processHTTPRequest() {

        while(true) {
            System.out.println("\nConnection established on port: " + PORT);
            Socket socket = null;
            try {
                socket = server_socket.accept();
            } catch (IOException e) {
                e.printStackTrace();
            }

            Client client = null;
            try {
                client = new Client(socket);
            } catch (Exception e) {
                e.printStackTrace();
            }
            client.start();
        }

    }


}