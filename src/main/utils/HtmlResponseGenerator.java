package main.utils;

public class HtmlResponseGenerator {
    public static String generateByByteCount(int count) {
        int i = 0;
        String body;
        long filesize;

       body = "<html>\n " +
                "<head> \n" +
                "<title>\n" + "this file " + count + " length " + "</title> " +
                "</head>\n " +
                "<body>\n";

        while (i < (count - 113)) {
            body = body + "a";
            i++;
        }
        body += "\t\n" +
                "\t</body>\n" +
                "</html>";

        return body;
    }
}
