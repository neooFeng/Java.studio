package fengfei.studio.test;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ReadAccessLog {

    private final static String logPath = "C:\\Users\\fengfei\\Documents\\imsocket.2019-12-05.txt";
    static Map<String, Integer> requestCountPerMin = new HashMap<>();
    static Map<String, Integer> requestCountPerSec = new HashMap<>();
    static Map<Integer, Set<String>> requestDistinctUserPerMin = new HashMap<>();
    static Map<String, Integer> requestCountPerUser = new HashMap<>();
    static List<LogLineInfo> orderedLogs = new ArrayList<>();
    static int totalRequestCount = 0;
    static Set<String> distinctRequestToken = new HashSet<>();

    public static void main(String[] args){
        parseLogFile();

        printResult(requestCountPerUser);
    }

    private static void parseLogFile() {
        String thisLine;
        Map<String, Integer> tempRequestCountPerUser = new HashMap<>();



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

                if(!timeOk(lineInfo.getTimeInMin())){
                    continue;
                }

                int start = lineInfo.getRequest().indexOf("token=");
                int end = lineInfo.getRequest().lastIndexOf(" ");
                if (start > -1 && end > start && end-start > 20){
                    lineInfo.setParamUserToken(lineInfo.getRequest().substring(start, end));

                    orderedLogs.add(lineInfo);
                    tempRequestCountPerUser.put(lineInfo.getParamUserToken(), tempRequestCountPerUser.getOrDefault(lineInfo.getParamUserToken(), 0) + 1);
                    requestCountPerMin.put(lineInfo.getTimeInMin(), requestCountPerMin.getOrDefault(lineInfo.getTimeInMin(), 0) + 1);
                    requestCountPerSec.put(lineInfo.getTime(), requestCountPerSec.getOrDefault(lineInfo.getTime(), 0) + 1);

                    Integer timeSection = getTimeSection(lineInfo.getTime());
                    if (!requestDistinctUserPerMin.containsKey(timeSection)){
                        requestDistinctUserPerMin.put(timeSection, new HashSet<>());
                    }
                    requestDistinctUserPerMin.get(timeSection).add(lineInfo.getParamUserToken());

                    distinctRequestToken.add(lineInfo.getParamUserToken());
                    totalRequestCount++;
                }
            }

            requestCountPerUser = sortByValue(tempRequestCountPerUser);

            orderedLogs.sort(new Comparator<LogLineInfo>() {
                @Override
                public int compare(LogLineInfo o1, LogLineInfo o2) {
                    if (o1.getParamUserToken().equals(o2.getParamUserToken())){
                        return o1.getTime().compareTo(o2.getTime());
                    }else{
                        return o1.getParamUserToken().compareTo(o2.getParamUserToken());
                    }
                }
            });

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Map sortByValue(Map unsortedMap) {
        Map sortedMap = new TreeMap(new ValueComparator(unsortedMap));
        sortedMap.putAll(unsortedMap);
        return sortedMap;
    }

    static class ValueComparator implements Comparator {
        Map map;

        public ValueComparator(Map map) {
            this.map = map;
        }

        public int compare(Object keyA, Object keyB) {
            Comparable valueA = (Comparable) map.get(keyA);
            Comparable valueB = (Comparable) map.get(keyB);
            return valueB.compareTo(valueA);
        }
    }

    private static Integer getTimeSection(String timeInMin) {
        int hour = Integer.valueOf(timeInMin.substring("11/Oct/2019:".length(), "11/Oct/2019:".length() +2));
        int min = Integer.valueOf(timeInMin.substring("11/Oct/2019:00:".length(), "11/Oct/2019:00:".length() +2));

        int value = (hour-19) * 60 + min-25;

        return value / 5;
    }

    private static boolean timeOk(String timeInMin) {
            return timeInMin.compareTo("05/Dec/2019:19:20:00") > 0
                    && timeInMin.compareTo("05/Dec/2019:21:00:55") < 0;
    }

    private static void printResult(Map<String, Integer> requestCountMap) {
        List<String> keys = new ArrayList<>();
        keys.addAll(requestCountMap.keySet());
        for (String key : keys){
            System.out.println(key + ", " + requestCountMap.get(key));
        }

        List<Integer> keys2 = new ArrayList<>();
        keys2.addAll(requestDistinctUserPerMin.keySet());
        Collections.sort(keys2);
        for (Integer key : keys2){
            System.out.println("第" + key + "个5分钟,  " + requestDistinctUserPerMin.get(key).size());
        }

        System.out.println("User Count: " + distinctRequestToken.size());

        System.out.println("Total Request Count: " + totalRequestCount);

        /*for (LogLineInfo lineInfo : orderedLogs){
            System.out.println(lineInfo.getParamUserToken().substring(lineInfo.getParamUserToken().length()-15) + " >> " + lineInfo.getTime());
        }*/
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
