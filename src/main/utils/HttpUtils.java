package main.utils;

import main.protocol.HttpHeader;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class HttpUtils {
    public static HttpHeader extractHeader(String header) {
        try {
            Pattern regex = Pattern.compile("(.*):(.*)");
            Matcher m = regex.matcher(header);
            if (m.matches()) {
                return new HttpHeader(m.group(1).toLowerCase().trim(), m.group(2).trim());
            }

            return null;
        } catch (Exception e) {
            return null;
        }
    }


    public static String getStatusMessage(int code) {
        switch (code) {
            case 200:
                return "OK";
            case 304:
                return "NOT MODIFIED";
            case 400:
                return "BAD REQUEST";
            case 404:
                return "NOT FOUND";
            case 414:
                return "REQUEST URI TOO LONG";
            case 500:
                return "INTERNAL SERVER ERROR";
            case 501:
                return "NOT IMPLEMENTED";
            default:
                return "";
        }
    }

    public static String generateBodyByByteCount(int count) {
        int i = 0;
        String body;
        long filesize;

        body = "<html>" +
                "<head>" +
                "<title>" + "I am " + count + " bytes long" + "</title>" +
                "</head>" +
                "<body>";


        i = body.length();
        while (i < count - 14) {
            body = body + "a";
            i++;
        }
        body += "</body>" +
                "</html>";

        return body;
    }
}
