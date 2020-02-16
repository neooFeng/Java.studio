package fengfei.studio.protocal;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang.StringUtils;

public class IQBodyChatRoomUpdate extends IQBody {
    private String name;
    private String status;
    private COnfig config;

    public IQBodyChatRoomUpdate(){}

    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    public String toJsonString(){
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.writeValueAsString(this);
        } catch (JsonProcessingException e) {
        }

        return StringUtils.EMPTY;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public COnfig getConfig() {
        return config;
    }

    public void setConfig(COnfig config) {
        this.config = config;
    }
}
