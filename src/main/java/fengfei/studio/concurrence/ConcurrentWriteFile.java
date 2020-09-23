package fengfei.studio.concurrence;

import java.io.*;

public class ConcurrentWriteFile {

    final static String filename = "C:\\Users\\fengfei\\Desktop\\a1.txt";

    public static void main(String[] args) throws InterruptedException, IOException {
        File f = new File(filename);
        FileOutputStream os = new FileOutputStream(f);

        Thread writeThread = new Thread(new WriteRunable1(os, "t1 "));
        Thread writeThread2 = new Thread(new WriteRunable1(os, "t2 "));

        System.out.println("start");

        writeThread2.start();
        writeThread.start();

        writeThread.join();
        writeThread2.join();

        os.close();

        System.out.println("exit.");
    }

    static class WriteRunable1 implements Runnable{
        private FileOutputStream os;
        private String prefix;

        public WriteRunable1(FileOutputStream os, String prefix) {
            this.os = os;
            this.prefix = prefix;
        }

        @Override
        public void run() {
            try {
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
