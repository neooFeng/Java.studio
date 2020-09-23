package fengfei.studio.temp;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class ReadIMApplicationLog {

    private final static String logPath = "C:\\Users\\fengfei\\Documents\\im-socket.10-31.log";
    static int maxSessionCount = 0;

    public static void main(String[] args){
        parseLogFile();

        System.out.println("maxSessionCount: " + maxSessionCount);
    }

    private static void parseLogFile() {
        String thisLine = null;
        String keyword = "current count: ";
        try (BufferedReader br = new BufferedReader(new FileReader(logPath))) {
            while ((thisLine = br.readLine()) != null) {
                int index = thisLine.indexOf(keyword);
                if (index != -1){
                    int count = Integer.valueOf(thisLine.substring(index + keyword.length()));
                    if (count > maxSessionCount){
                        maxSessionCount = count;
                    }
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
