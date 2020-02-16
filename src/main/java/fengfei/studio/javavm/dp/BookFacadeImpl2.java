package fengfei.studio.javavm.dp;

public class BookFacadeImpl2 implements BookFacade{
    public void addBook() {
        System.out.println("增加图书的普通方法...");
    }

    @Override
    public void removeBook() {
        System.out.println("remove book...");
    }
}
