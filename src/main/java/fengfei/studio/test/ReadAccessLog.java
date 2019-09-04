package fengfei.studio.test;

import ch.qos.logback.core.util.StringCollectionUtil;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ReadAccessLog {

    private final static String logPath = "C:\\Users\\fengfei\\Documents\\imsocket.access.txt";
    static Map<String, Integer> requestCountPerMin = new HashMap<>();
    static Set<String> distinctRequestToken = new HashSet<>();

    public static void main(String[] args){
        parseLogFile();

        printResult();
    }

    private static void parseLogFile() {
        String thisLine = null;

        try (BufferedReader br = new BufferedReader(new FileReader(logPath))) {
            while ((thisLine = br.readLine()) != null) {
                Pattern pattern = Pattern.compile("(.*?) (.*?) (.*?) \\[(.*?)\\] \"(.*?)\" (.*?) (.*?)");

                Matcher matcher = pattern.matcher(thisLine);
                if (!matcher.find()){
                    continue;
                }

                LogLineInfo lineInfo = new LogLineInfo();
                lineInfo.setIp(matcher.group(1));
                lineInfo.setTime(matcher.group(4));
                lineInfo.setTimeInMin(lineInfo.getTime().substring(0, "01/Sep/2019:02:01".length()));

                lineInfo.setRequest(matcher.group(5));

                int start = lineInfo.getRequest().indexOf("token=");
                int end = lineInfo.getRequest().lastIndexOf(" ");
                if (start > -1 && end > start){
                    lineInfo.setParamUserToken(lineInfo.getRequest().substring(start, end));
                }

                requestCountPerMin.put(lineInfo.getTimeInMin(), requestCountPerMin.getOrDefault(lineInfo.getTimeInMin(), 0) + 1);
                if(lineInfo.getParamUserToken() != null && timeOk(lineInfo.getTimeInMin())){
                    distinctRequestToken.add(lineInfo.getParamUserToken());
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static boolean timeOk(String timeInMin) {
            return timeInMin.compareTo("01/Sep/2019:18:18") > 0
                    && timeInMin.compareTo("01/Sep/2019:19:18") < 0;
    }

    private static void printResult() {
        List<String> keys = new ArrayList<>();
        keys.addAll(requestCountPerMin.keySet());
        Collections.sort(keys);
        for (String minute : keys){
            System.out.println(minute + ", " + requestCountPerMin.get(minute));
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
