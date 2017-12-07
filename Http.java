package dmitri.liventsev.gateway.http;

public class Http {
    private static final String METHOD_POST = "POST";
    private static final String METHOD_GET = "GET";
    private static final String METHOD_PATH = "PATH";
    private static final String METHOD_PUT = "PUT";
    private static final String METHOD_DELETE = "DELETE";

    public static Request post(String requestUrl) {
        return new Request(METHOD_POST, requestUrl);
    }

    public static Request get(String requestUrl) {
        return new Request(METHOD_GET, requestUrl);
    }

    public static Request path(String requestUrl) {
        return new Request(METHOD_PATH, requestUrl);
    }

    public static Request put(String requestUrl) {
        return new Request(METHOD_PUT, requestUrl);
    }

    public static Request DELETE(String requestUrl) {
        return new Request(METHOD_DELETE, requestUrl);
    }
}
