package fengfei.studio.file;

import java.io.*;

public class WriteCurrency {

    final static String filename = "/Users/teacher/a.txt";
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

        public WriteRunable(String fileName, String prefix){
            this.fileName = fileName;
            this.prefix = prefix;
        }

        @Override
        public void run() {
            try (FileWriter fileWriter = new FileWriter(filename)) {
                for (int i = 0; i < 1000; i++) {
                    String line = prefix + i + "\r\n";
                    fileWriter.append(line);
                    System.out.println("write:  " + line);
                    fileWriter.flush();
                    Thread.sleep(1);
                }
            } catch (IOException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
