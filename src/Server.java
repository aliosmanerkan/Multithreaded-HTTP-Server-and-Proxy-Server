import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    private ServerSocket serverSocket;
    private Socket socket;
    private int portNumber;
    private boolean isConnectionOn;

    public Server(int portNumber) {
        this.portNumber = portNumber;
        isConnectionOn = true;

        try {
            serverSocket = new ServerSocket(portNumber);
        } catch (IOException ioe) {
            System.out.println("Could not create server socket on port " + portNumber + ".Quitting.");
            System.exit(-1);
        }
    }


    public void run() {
        System.out.println("Multi-Threaded Web Server has now Started");
        System.out.println("Enter \"localhost:" + portNumber ); //localhost:8080/500  -> example

        while (isConnectionOn) {
            try {
                socket = serverSocket.accept();
            } catch (IOException e) {
                e.printStackTrace();
            }

            // new request arrived
            HTTPRequest request = new HTTPRequest(socket);
            // Create a worker thread to process the request.
            Thread thread = new Thread(request);
            // Start the thread.
            thread.start();
        }
    }
}
