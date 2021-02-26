package fengfei.studio.datajob;

import java.io.*;

public class FilterGitDiff {

    public static void main(String[] args) {
        String filePath = "D:\\QingShuSchoolPlatform\\diff.txt";

        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(filePath))){

            String line;
            while ((line = bufferedReader.readLine()) != null){
                if (line.contains("diff --git") && line.contains("/database/") && !line.contains("/bpartner/")){
                    System.out.println(line);
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
