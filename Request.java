package dmitri.liventsev.gateway.http;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;

/**
 * Created by dmitri on 29/11/17.
 */

public class Request {
    private String method;
    private HashMap<String, String> body;
    private HashMap<String, String> headers;
    private String requestURL;

    Request(String method, String requestURL) {
        this.method = method;
        this.requestURL = requestURL;

        this.headers = new HashMap<String, String>();
        this.body = new HashMap<String, String>();
    }

    public Request setBody(HashMap<String, String> body) {
        this.body = body;

        return this;
    }

    public Request setHeaders(HashMap<String, String> headers) {
        this.headers = headers;

        return this;
    }

    public Request addHeader(String key, String value) {
        headers.put(key, value);

        return this;
    }

    public Response execute() {
        URL url;
        Response response = new Response();

        try {
            url = new URL(requestURL);

            HttpURLConnection conn = buildConnection(url);
            addHeaders(conn);
            addBody(conn);

            response.setResponseCode(conn.getResponseCode());
            response.setContent(readResponseString(conn));

        } catch (Exception e) {
            e.printStackTrace();
        }

        return response;
    }

    private HttpURLConnection buildConnection(URL url) throws IOException {
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setReadTimeout(15000);
        conn.setConnectTimeout(15000);
        conn.setRequestMethod(method);
        conn.setDoInput(true);
        conn.setDoOutput(true);

        return conn;
    }

    private void addHeaders(HttpURLConnection conn) {
        for(Map.Entry<String, String> entry : headers.entrySet()) {
            conn.setRequestProperty(entry.getKey(), entry.getValue());
        }
    }

    private void addBody(HttpURLConnection conn) throws IOException {
        OutputStream os = conn.getOutputStream();
        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
        writer.write(getPostDataString(body));

        writer.flush();
        writer.close();
        os.close();
    }

    private String readResponseString(HttpURLConnection conn) {
        StringBuilder responseBodyStringBuilder = new StringBuilder();

        try {
            if (conn.getResponseCode() == HttpsURLConnection.HTTP_OK) {
                String line;
                BufferedReader br=new BufferedReader(new InputStreamReader(conn.getInputStream()));
                while ((line=br.readLine()) != null) {
                    responseBodyStringBuilder.append(line);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return responseBodyStringBuilder.toString();
    }

    private String getPostDataString(HashMap<String, String> params) throws UnsupportedEncodingException {
        StringBuilder result = new StringBuilder();
        boolean first = true;
        for(Map.Entry<String, String> entry : params.entrySet()){
            if (first)
                first = false;
            else
                result.append("&");

            result.append(URLEncoder.encode(entry.getKey(), "UTF-8"));
            result.append("=");
            result.append(URLEncoder.encode(entry.getValue(), "UTF-8"));
        }

        return result.toString();
    }
}
