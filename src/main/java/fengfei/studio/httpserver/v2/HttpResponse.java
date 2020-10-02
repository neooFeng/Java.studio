package fengfei.studio.httpserver.v2;

import java.util.List;

public class HttpResponse {
    private List<HttpHeader> headers;
    private String body;

    public List<HttpHeader> getHeaders() {
        return headers;
    }

    public void setHeaders(List<HttpHeader> headers) {
        this.headers = headers;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }
}
