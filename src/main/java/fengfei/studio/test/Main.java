package fengfei.studio.test;

import org.glassfish.grizzly.compression.zip.GZipEncoder;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {

        try(CloseableResource cr = new CloseableResource()) {
            System.out.println("hi.");
        }

        System.out.println("exit.");
    }
}
