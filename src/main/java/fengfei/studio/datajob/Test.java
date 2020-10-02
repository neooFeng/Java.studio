package fengfei.studio.test;

public class Test {

    public static void main(String[] args) {

        ARDeque<String> deque = new ARDeque<>();
        deque.addLast("a");
        deque.addLast("b");
        deque.addLast("a");

        deque.printDeque();

        try{
            UniqueARDeque<String> ud = new UniqueARDeque<>(deque);

            ud.printDeque();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
