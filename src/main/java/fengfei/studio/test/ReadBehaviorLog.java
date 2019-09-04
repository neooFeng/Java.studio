package fengfei.studio.test;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ReadBehaviorLog {

    private final static String logPath = "C:\\Users\\fengfei\\Documents\\actionLog.txt";
    static Set<String> distinctRequestToken = new HashSet<>();

    public static void main(String[] args){
        parseLogFile();

        printResult();
    }

    private static void parseLogFile() {
        String thisLine = null;
        int lineNumber = 0;
        try (BufferedReader br = new BufferedReader(new FileReader(logPath))) {
            while ((thisLine = br.readLine()) != null) {
                lineNumber++;
                if ((lineNumber-2) % 3 == 0){
                    distinctRequestToken.add(thisLine);
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void printResult() {
        for (String token : distinctRequestToken){
            System.out.println(token);
        }

        System.out.println("User Count: " + distinctRequestToken.size());
    }

    static class LogLineInfo {
        private String ip;
        private String time;
        private String timeInMin;
        private String request;
        private String responseStatus;
        private String paramUserToken;

        public String getIp() {
            return ip;
        }

        public void setIp(String ip) {
            this.ip = ip;
        }

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }

        public String getTimeInMin() {
            return timeInMin;
        }

        public void setTimeInMin(String timeInMin) {
            this.timeInMin = timeInMin;
        }

        public String getRequest() {
            return request;
        }

        public void setRequest(String request) {
            this.request = request;
        }

        public String getResponseStatus() {
            return responseStatus;
        }

        public void setResponseStatus(String responseStatus) {
            this.responseStatus = responseStatus;
        }

        public String getParamUserToken() {
            return paramUserToken;
        }

        public void setParamUserToken(String paramUserToken) {
            this.paramUserToken = paramUserToken;
        }
    }
}
