import java.io.*;
import java.net.*;

public class Main {

    public static void main(String[] args) {
        Server server = new Server(Integer.parseInt(args[0]));
        server.run();
    }
}