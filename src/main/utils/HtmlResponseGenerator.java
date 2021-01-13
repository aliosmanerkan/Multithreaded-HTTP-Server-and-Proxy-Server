package main.utils;

public class HtmlResponseGenerator {
    public static String generateByByteCount(int count) {
        int i = 0;
        String body;
        long filesize;

        body = "<html>\n " +
                "<head> \n" +
                "<title>\n" + "I am " + count + " bytes long " + "</title> " +
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
