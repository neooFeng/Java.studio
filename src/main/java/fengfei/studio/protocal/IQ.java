package fengfei.studio.protocal;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang.StringUtils;

public class IQ extends Base {
    private String id;

    private String type;

    private IQBody body;

    @Override
    public Object clone() throws CloneNotSupportedException {
        IQ cloned = (IQ) super.clone();
        if (this.body != null){
            cloned.setBody((IQBody) this.body.clone());
        }
        return cloned;
    }

    public String toJsonString() {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.writeValueAsString(this);
        } catch (JsonProcessingException e) {
        }

        return StringUtils.EMPTY;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public IQBody getBody() {
        return body;
    }

    public void setBody(IQBody body) {
        this.body = body;
    }
}
