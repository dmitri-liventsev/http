package dmitri.liventsev.gateway.http;

/**
 * Created by dmitri on 29/11/17.
 */

public class Response {
    private int responseCode = 0;
    private String content;

    public int getResponseCode() {
        return responseCode;
    }

    void setResponseCode(int responseCode) {
        this.responseCode = responseCode;
    }

    public String getContent() {
        return content;
    }

    void setContent(String content) {
        this.content = content;
    }

    public boolean isSuccess() {
        return responseCode != 0 && responseCode >= 200 && responseCode < 300;
    }
}
