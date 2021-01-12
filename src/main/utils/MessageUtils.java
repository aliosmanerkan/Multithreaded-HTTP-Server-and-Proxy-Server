package main.utils;

import main.types.StatusCodes;

public class MessageUtils {

    public static StatusCodes getResponseStatusCode(String method, String query) {
        if (method == null) return StatusCodes.BAD_REQUEST;
        if (!method.equals("GET")) return StatusCodes.NOT_IMPLEMENTED;

        String cleanQuery = getCleanRequestQuery(query);                     // remove / from beginning
        if (cleanQuery.length() == 0) return StatusCodes.BAD_REQUEST;           // if there is no query

        // check requested byte size - should be between 100 and 20000
        int byteSize = Integer.parseInt(cleanQuery);
        if (Constants.MIN_FILE_SIZE >= byteSize || byteSize >= Constants.MAX_FILE_SIZE)
            return StatusCodes.BAD_REQUEST;

        return StatusCodes.OK;
    }

    public static String getCleanRequestQuery(String query) {
        return query.replaceFirst("/", "");
    }

    public static int getRequestedByteSize(String query) {
        if (query != null) {
            return Integer.parseInt(query);
        }
        return 0;
    }


}
