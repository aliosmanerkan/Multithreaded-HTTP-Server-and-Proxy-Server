import java.io.File;

public class MessageHandler {

    public static ResponseTypes handleIncomingMessage(String method, String parameter) {

        if (!method.equals("GET")) {
            return ResponseTypes.NOT_IMPLEMENTED;
        }
        String cleanParameter = parameter.substring(1); // remove / from beginning
        if (parameter == null) return ResponseTypes.BAD_REQUEST;

        // check requested byte size - should be between 100 and 20000
        int byteSize = Integer.parseInt(cleanParameter);
        if (Constants.MIN_FILE_SIZE >= byteSize || byteSize >= Constants.MAX_FILE_SIZE)
            return ResponseTypes.BAD_REQUEST;

        return ResponseTypes.OK;
    }

    public static File prepareResponseMessageFile(int byteSize) {
        //todo:
        return null;
    }

    public static byte[] prepareResponseMessage(File file) {
        //todo:
        return null;
    }
}
