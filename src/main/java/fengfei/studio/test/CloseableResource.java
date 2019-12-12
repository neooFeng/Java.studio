package fengfei.studio.test;

import java.io.Closeable;
import java.io.IOException;

public class CloseableResource implements Closeable  {
    @Override
    public void close() throws IOException {
        System.out.println("close.");
        throw  new IOException("thorw");
    }
}
