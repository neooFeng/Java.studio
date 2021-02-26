package fengfei.studio.temp;

import javafx.scene.control.TableRow;
import org.apache.commons.lang3.StringUtils;

import java.io.*;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.List;

public class SplitSprocFiles {

    public static void main(String[] args) throws IOException {
        String root = "/Users/teacher/dumps/Dump20201221/";
        String routinesFile = "routines.sql";
        String[] dalPaths = new String[]{"/Users/teacher/Documents/Github/qingshu/QingShuSchoolPlatform/src/server/QingShuSchoolPlatform/QingShuCommonDAL/src/main/java/com/feifanuniv/common/dal/global"
               /* "/Users/teacher/Documents/Github/qingshu/account/QingShuAccountDAL/src/main/java/com/feifanuniv/account/dal"*/};

       // splitSprocFiles(root, routinesFile);

        //renameTableScript(root, "global_", "");

        //clearTableScript(root);

        List<String> allSprocs = getSprocNames(root + routinesFile);

        List<String> sprocInUsing = getUsingSprocs(dalPaths);

        mvUsingSproc(root, sprocInUsing);

        //saveDropUnusedSproc(allSprocs, sprocInUsing, root);
    }

    private static void mvUsingSproc(String root, List<String> sprocInUsing) {
        String sourceFolder = root + "sproc/";
        String targetFolder = root + "sproc2/";

        for (String sprocName : sprocInUsing){
            Path sourcePath = Paths.get(sourceFolder + sprocName + ".sql");
            Path targetPath = Paths.get(targetFolder + sprocName + ".sql");
            try {
                Files.copy(sourcePath, targetPath, StandardCopyOption.REPLACE_EXISTING);
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    private static void clearTableScript(String root) throws IOException {
        File folder = new File(root);

        File[] sqlFiles = folder.listFiles();
        for (File sqlFile : sqlFiles){
            if (sqlFile.isDirectory()){
                continue;
            }
            if (!sqlFile.getPath().contains("sql")){
                continue;
            }

            StringBuilder stringBuilder = new StringBuilder();

            System.out.println(sqlFile.toURI());

            List<String> allLines = Files.readAllLines(Paths.get(sqlFile.toURI()));
            for (String line : allLines){
                if (line.startsWith("--")){
                    continue;
                }
                if (line.startsWith("/*!")){
                    continue;
                }
                if (StringUtils.isBlank(line)){
                    continue;
                }

                if (line.contains("AUTO_INCREMENT=")){
                    stringBuilder.append(line.replaceFirst("AUTO_INCREMENT=(\\d+)", "AUTO_INCREMENT=1"));
                }else{
                    stringBuilder.append(line);
                }
                stringBuilder.append("\r");

                Files.write(Paths.get(root + "tables/" + sqlFile.getName()), stringBuilder.toString().getBytes(), StandardOpenOption.CREATE);
            }
        }
    }

    private static void renameTableScript(String folderpath, String replace, String target) {
        File folder = new File(folderpath);

        File[] sqlFiles = folder.listFiles();
        for (File sqlFile : sqlFiles){
            sqlFile.renameTo(new File(sqlFile.getPath().replaceFirst(replace, target)));
        }
    }

    private static void saveDropUnusedSproc(List<String> allSprocs, List<String> sprocInUsing, String savePath) {
        String DROP_FORMAT = "DROP PROCEDURE IF EXISTS `%s`;";
        String CREATE_FORMAT = "CREATE PROCEDURE `%s`;";

        StringBuilder stringBuilder = new StringBuilder();
        for (String sproc : allSprocs){
            if (!sprocInUsing.contains(sproc)){
                stringBuilder.append(String.format(DROP_FORMAT, sproc)).append("\r");
            }
        }

        for (String sproc : sprocInUsing){
            if (!allSprocs.contains(sproc)){
                stringBuilder.append(String.format(CREATE_FORMAT, sproc)).append("\r");
            }
        }

        if (stringBuilder.length() > 0){
            Path path = Paths.get(savePath + "0.drop_unused_routines.sql");
            try {
                Files.write(path, stringBuilder.toString().getBytes(), StandardOpenOption.CREATE);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private static List<String> getUsingSprocs(String[] dalPaths) {
        List<String> sprocNames = new ArrayList<>();

        for (String path : dalPaths) {
            sprocNames.addAll(getUsingSprocs(path));
        }

        return sprocNames;
    }

    private static List<String> getUsingSprocs(String dalPath) {
        List<String> sprocNames = new ArrayList<>();

        File folder = new File(dalPath);

        File[] dalFiles = folder.listFiles();
        if (dalFiles == null){
            return sprocNames;
        }

        for (File dalFile : dalFiles){
            if (dalFile.isDirectory()){
                sprocNames.addAll(getUsingSprocs(dalFile.getPath()));
            }else{
                sprocNames.addAll(getSprocNamesFromDalFile(dalFile));
            }
        }

        return sprocNames;
    }

    private static List<String> getSprocNamesFromDalFile(File dalFile) {
        List<String> sprocNames = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(dalFile))){
            String line;
            while ((line = br.readLine()) != null){
                if (line.contains("Execute(") || line.contains("ExecuteReturnList(") || line.contains("ExecuteReturnObject(")) {
                    String[] arrs = line.split("\"");
                    sprocNames.add(arrs[1]);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return sprocNames;
    }

    private static List<String> getSprocNames(String routinesFile) {
        List<String> sprocNames = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(routinesFile))){
            String line;
            while ((line = br.readLine()) != null){
                if (line.startsWith("CREATE PROCEDURE ")) {
                    String sprocName = line.substring(line.indexOf("`") + 1, line.lastIndexOf("`"));
                    sprocNames.add(sprocName);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return sprocNames;
    }

    private static void splitSprocFiles(String root, String routinesFile) {
        try (BufferedReader br = new BufferedReader(new FileReader(root + routinesFile))){
            String line;
            boolean isBody = false;
            String sprocName = "nu";
            StringBuilder stringBuilder = null;
            while ((line = br.readLine()) != null){
                if (line.startsWith("CREATE PROCEDURE ") || line.startsWith("CREATE FUNCTION ")){
                    isBody = true;
                    sprocName = line.substring(line.indexOf("`")+1, line.lastIndexOf("`"));

                    stringBuilder = new StringBuilder();
                    if(line.startsWith("CREATE PROCEDURE ")){
                        stringBuilder.append("DROP PROCEDURE IF EXISTS `").append(sprocName).append("`;").append("\r\r");
                    }else{
                        stringBuilder.append("DROP FUNCTION IF EXISTS `").append(sprocName).append("`;").append("\r\r");
                    }
                    stringBuilder.append("DELIMITER ;;").append("\r");
                }else if (line.equals("DELIMITER ;")){
                    if (!isBody){
                        continue;
                    }

                    isBody = false;
                    stringBuilder.append(line).append("\r");

                    Path path = Paths.get(root + "sproc/" + sprocName + ".sql");
                    Files.write(path, stringBuilder.toString().getBytes(), StandardOpenOption.CREATE);
                }

                if (isBody){
                    stringBuilder.append(line).append("\r");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
