package fengfei.studio.javavm.data;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.ArrayList;
import java.util.List;

public class BigList {
    private List<BigObject> list;

    public BigList(int arrLen) {

        this.list = new ArrayList<>(arrLen);

        for (int i = 0; i < arrLen; i++) {
            this.list.add(new BigObject("dkflaklLkdjl老人家客家人了就拉开的叫法"));
        }
    }

    public List<BigObject> getList() {
        return list;
    }

    public void setList(List<BigObject> list) {
        this.list = list;
    }

    public String toJson(){
        String json = null;

        ObjectMapper objectMapper = new ObjectMapper();
        try {
            json = objectMapper.writeValueAsString(this);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        return json;
    }
}
