package fengfei.studio.protocal;

public class Atest {

    public static void main(String[] args) throws CloneNotSupportedException {

        COnfig config = new COnfig("confinanan");

        IQBodyChatRoomUpdate update = new IQBodyChatRoomUpdate();
        update.setRequest("hahahah");
        update.setName("nam");
        update.setStatus("status");
        update.setConfig(config);


        IQ iq = new IQ();
        iq.setId("id1");
        iq.setType(IQType.REQUEST.getName());
        iq.setBody(update);

        Base ob = iq;

        IQ cloned = (IQ) ob.clone();

        cloned.getBody().setRequest("kakaa");

        System.out.println(iq.toJsonString());
        System.out.println(cloned.toJsonString());


        System.out.println(update.toJsonString());
        System.out.println(((IQBodyChatRoomUpdate)update.clone()).toJsonString());
    }
}
