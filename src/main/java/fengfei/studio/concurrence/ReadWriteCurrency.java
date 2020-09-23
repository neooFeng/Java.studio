package fengfei.studio.concurrence;

import org.apache.commons.lang.math.RandomUtils;

import java.io.*;

public class ReadWriteCurrency {
    final static String filename = "C:\\Users\\fengfei\\Desktop\\a3.txt";

    public static void main(String[] args) throws InterruptedException, IOException {
        File file = new File(filename);

        FileInputStream inputStream = new FileInputStream(file);
        FileOutputStream outputStream = new FileOutputStream(file);

        Thread readThread = new Thread(new ReadRunable(inputStream));
        Thread writeThread = new Thread(new WriteRunable(outputStream));

        System.out.println("start");

        writeThread.start();
        Thread.sleep(10);
        readThread.start();

        readThread.join();
        writeThread.join();

        inputStream.close();
        outputStream.close();


        System.out.println("exit.");
    }

    static class ReadRunable implements Runnable{
        private FileInputStream inputStream;

        public ReadRunable(FileInputStream inputStream){
            this.inputStream = inputStream;
        }

        @Override
        public void run() {
            try {
                int i = 100000;
                while (i-- > 0){
                    int c = inputStream.read();
                    System.out.println(c);
                    Thread.sleep(1);
                }
            } catch (IOException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("read end.");
        }
    }

    static class WriteRunable implements Runnable{
        private FileOutputStream outputStream;

        public WriteRunable(FileOutputStream outputStream){
            this.outputStream = outputStream;
        }

        @Override
        public void run() {
            try {
                int i = 100000;
                while (i-- > 0){
                    int d = RandomUtils.nextInt(50) + 48;
                    outputStream.write(d);
                    outputStream.flush();
                    System.out.println("write:  " + d);
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
