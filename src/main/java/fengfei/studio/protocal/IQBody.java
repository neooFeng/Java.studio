package fengfei.studio.protocal;

public abstract class IQBody implements Cloneable {
    public static final String REQUEST_UPDATE = "update";

    private String request;

    public IQBody() {
    }

    public IQBody(String request) {
        this.request = request;
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    public String getRequest() {
        return request;
    }

    public void setRequest(String request) {
        this.request = request;
    }

}
