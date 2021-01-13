import java.io.*;
import java.net.*;
import java.util.StringTokenizer;


public class HTTPResponse {

    BufferedReader inFromClient = null;
    DataOutputStream out = null;
    File returnHtml;
    private Socket socket;
    public HTTPResponse(Socket socket) {
        this.socket = socket;
    }

    public void setBody(int byteNumber) throws IOException {
        int i = 0;
        String body;
        long filesize;

            body = "<!DOCTYPE html>\n " +
                    "<html>\n " +
                    "<head> \n" +
                    "<title>\n" + "this file " + byteNumber + " length " + "</title> " +
                    "</head>\n " +
                    "<body>\n" +
                    "<p>\n";

            while (i < (byteNumber - 113)) {
                body = body + "a";
                i++;
            }
            body += "\t</p>\n" +
                    "\t\n" +
                    "\t</body>\n" +
                    "</html>";

        out.writeBytes(body);
    }

    public void addheader(int byteNumber,String responseString) throws Exception{
        out = null;
        String serverdetails = "Multithread HTTPServer";
        String contentLengthLine = null;
        String contentTypeLine = "Content-Type: text/html" + "\r\n";
        contentLengthLine = "Content-Length: " + byteNumber + "\r\n";

        out.writeBytes(serverdetails);
        out.writeBytes(contentTypeLine);
        out.writeBytes(contentLengthLine);
        out.writeBytes("Connection: close\r\n");
        out.writeBytes("\r\n");

        out.close();
    }

    public void setStatusCode(int Code){

    }

}
