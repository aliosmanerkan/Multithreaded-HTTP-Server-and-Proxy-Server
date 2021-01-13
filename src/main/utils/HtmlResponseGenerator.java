package main.utils;

public class HtmlResponseGenerator {
    public static String generateByByteCount(int count) {
        int i = 0;
        String body;
        long filesize;

        body = "<html>" +
                "<head>" +
                "<title>" + "I am" + count + "bytes long" + "</title>" +
                "</head>" +
                "<body>";


        i=body.length();
        while (i < count-14) {
            body = body + "a";
            i++;
        }
        body += "</body>" +
                "</html>";

        return body;
    }
}
