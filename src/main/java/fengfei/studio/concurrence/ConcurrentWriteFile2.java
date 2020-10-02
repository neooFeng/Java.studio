package fengfei.studio.concurrence;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class ConcurrentWriteFile2 {

    final static String filename = "C:\\Users\\fengfei\\Desktop\\a2.txt";

    public static void main(String[] args) throws InterruptedException {

        Thread writeThread = new Thread(new WriteRunable(filename, "t1 "));
        Thread writeThread2 = new Thread(new WriteRunable(filename, "t2 "));

        System.out.println("start");

        writeThread2.start();
        writeThread.start();

        writeThread.join();
        writeThread2.join();

        System.out.println("exit.");
    }

    static class WriteRunable implements Runnable{
        private String fileName;
        private String prefix;

        public WriteRunable(String fileName, String prefix) {
            this.fileName = fileName;
            this.prefix = prefix;
        }

        @Override
        public void run() {
            try (FileOutputStream os = new FileOutputStream(new File(filename))){
                for (int i = 0; i < 1000; i++) {
                    String line = prefix + i + "\r\n";
                    os.write(line.getBytes());
                    // os.flush();
                    System.out.println("write:  " + line);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
