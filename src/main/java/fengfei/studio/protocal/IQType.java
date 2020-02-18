package fengfei.studio.protocal;

public enum IQType {
    REQUEST("request"), RESULT("result"), ERROR("error");

    private String name;

    IQType(String name) {
        this.name = name;
    }

    public static boolean contains(String type){

        for (IQType item : values() ){
            if (item.getName().equals(type)){
                return true;
            }
        }

        return false;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
