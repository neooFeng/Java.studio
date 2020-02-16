package fengfei.studio.javavm.dp;

public class JDKProxyApp {
    public static void main(String[] args) {
        BookFacadeProxyJDK proxy = new BookFacadeProxyJDK();
        BookFacade bookProxy = (BookFacade) proxy.bind(new BookFacadeImpl2());
        bookProxy.addBook();
        bookProxy.removeBook();
        bookProxy.addBook();
    }
}
