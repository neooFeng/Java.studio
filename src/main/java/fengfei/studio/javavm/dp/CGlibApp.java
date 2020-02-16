package fengfei.studio.javavm.dp;

public class CGlibApp {
    public static void main(String[] args) {
        BookFacadeProxyCglib cglib=new BookFacadeProxyCglib();
        BookFacadeImpl bookCglib=(BookFacadeImpl)cglib.getInstance(new BookFacadeImpl());
        bookCglib.addBook();
        bookCglib.removeBook();
    }
}
