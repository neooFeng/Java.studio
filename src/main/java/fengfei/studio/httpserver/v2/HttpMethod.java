package fengfei.studio.httpserver.v2;

public enum HttpMethod {
    GET("GET"), POST("POST"), PUT("PUT"), DELETE("DELETE");

    HttpMethod(String method) {
        this.method = method;
    }

    private String method;

    public String getMethod() {
        return method;
    }
}
