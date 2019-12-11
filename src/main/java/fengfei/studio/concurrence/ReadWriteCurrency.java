package fengfei.studio.concurrence;

import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.concurrent.LinkedBlockingQueue;

public class ReadWriteCurrency {
    final static String filename = "/Users/teacher/a.txt";

    public static void main(String[] args) throws InterruptedException {
        Thread readThread = new Thread(new ReadRunable(filename));
        Thread writeThread = new Thread(new WriteRunable(filename));

        System.out.println("start");

        readThread.start();
        writeThread.start();

        readThread.join();
        writeThread.join();

        System.out.println("exit.");
    }

    static class ReadRunable implements Runnable{
        private String fileName;

        public ReadRunable(String fileName){
            this.fileName = fileName;
        }

        @Override
        public void run() {
            try (FileReader fileReader = new FileReader(filename)) {
                while (true){
                    int c = fileReader.read();
                    System.out.print((char)c);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            System.out.println("read end.");
        }
    }

    static class WriteRunable implements Runnable{
        private String fileName;

        public WriteRunable(String fileName){
            this.fileName = fileName;
        }

        @Override
        public void run() {
            for (int i=0; i<1000; i++){
                try (FileWriter fw = new FileWriter(fileName)) {
                    String line = "t " + System.nanoTime();
                    fw.write(line);
                    System.out.println("write:  " + line);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                try {
                    Thread.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

        }
    }
}
