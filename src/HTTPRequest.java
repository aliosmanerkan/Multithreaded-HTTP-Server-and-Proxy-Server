import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.StringTokenizer;

public class HTTPRequest implements Runnable {

    private Socket socket;

    public HTTPRequest(Socket serverSocket) {
        this.socket = serverSocket;
    }

    @Override
    public void run() {
        try {
            process();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    private void process() throws Exception {
        InputStreamReader inputStreamReader = new InputStreamReader(socket.getInputStream());
        DataOutputStream outputStream = new DataOutputStream(socket.getOutputStream());
        BufferedReader br = new BufferedReader(inputStreamReader);

        // Get the request line of the HTTP request message.
        String requestLine = br.readLine();

        StringTokenizer tokens = new StringTokenizer(requestLine);
        String method = tokens.nextToken();  // should be "GET"
        String rawParameter = tokens.nextToken();

        // Get and display the header lines.
        String headerLine = null;
        System.out.println("---RECEIVED MESSAGE---");
        while ((headerLine = br.readLine()).length() != 0) {
            System.out.println(headerLine);
        }

        ResponseTypes responseType = MessageHandler.handleIncomingMessage(method, rawParameter);

        //todo: prepare response message by passing responseType and byte size - html file should be filled to specified size

        inputStreamReader.close();
        outputStream.close();
        socket.close(); //todo: where should we close the socket?
    }
}
