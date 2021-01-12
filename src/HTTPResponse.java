import java.io.*;
import java.net.*;
import java.util.StringTokenizer;


public class HTTPResponse {

    private Socket socket;
    public HTTPResponse(Socket socket) {
        this.socket = socket;
    }

    public void createHTML(int byteNumber) {
        int i = 0;
        long filesize;
        File returnHtml = new File("byteNumber.toString().txt");
        try {
            FileWriter fileWriter = new FileWriter("byteNumber.toString().txt");
            fileWriter.write("<!DOCTYPE html>\n " +
                    "<html>\n " +
                    "<head> \n" +
                    "<title>\n" + "this file" + byteNumber + "length " + "</title> " +
                    "</head>\n " +
                    "<body>\n" +
                    "<p>\n");

            filesize = returnHtml.length();
            while (i < (byteNumber - 113)) {
                fileWriter.write("a");
                i++;
            }
            fileWriter.write("\t</p>\n" +
                    "\t\n" +
                    "\t</body>\n" +
                    "</html>");
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        filesize = returnHtml.length();
        System.out.println(filesize);

    }
}
