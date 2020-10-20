package fengfei.studio.httpserver.v2;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class HttpRequest {
    private HttpMethod method;
    private URL url;

    private List<HttpHeader> headers;

    private List<NameValuePair> params;

    public HttpMethod getMethod() {
        return method;
    }

    public void setMethod(HttpMethod method) {
        this.method = method;
    }

    public URL getUrl() {
        return url;
    }

    public void setUrl(URL url) {
        this.url = url;
    }

    public void addHeader(HttpHeader header){
        if (this.headers == null) {
            this.headers = new ArrayList<>();
        }
        this.headers.add(header);
    }

    public String getHeader(String name){
        if (this.headers == null) {
            return null;
        }

        for (HttpHeader header : this.headers){
            if (header.getName().equals(name)){
                return header.getValue();
            }
        }

        return null;
    }

    public List<HttpCookie> getCookies(){
        // TODO

        throw new NotImplementedException();
    }

    public HttpCookie getCookie(String name){
        // TODO

        throw new NotImplementedException();
    }

    public void addParamter(String name, Object value){
        if (this.params == null) {
            this.params = new ArrayList<>();
        }

        this.params.add(new NameValuePair(name, value));
    }

    public Object getParamter(String name){
        if (this.params == null) {
            return null;
        }

        for (NameValuePair param : this.params){
            if (param.getName().equals(name)){
                return param.getValue();
            }
        }

        return null;
    }
}
