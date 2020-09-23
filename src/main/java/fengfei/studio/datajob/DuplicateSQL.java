package fengfei.studio.datajob;

import java.io.*;
import java.util.*;

public class DuplicateSQL {

    private final static String saveDir = "/Users/teacher/Documents/迁移/prod/";
    private final static String syncBasicDataSqlFile = "/Users/teacher/Documents/迁移/sync_basic_data.sql";
    private final static String syncMajorByCollegeFile = "/Users/teacher/Documents/迁移/sync_major_by_college.sql";
    private final static String syncMajorByAcademyFile = "/Users/teacher/Documents/迁移/sync_major_by_academy.sql";
    private final static String syncMajorByClassFile = "/Users/teacher/Documents/迁移/sync_major_by_class.sql";
    private final static String syncOtherDataSqlFile = "/Users/teacher/Documents/迁移/sync_other_data.sql";

    public static void main(String[] args) {
        List<SchoolMap> schoolMaps = new ArrayList<>();


        schoolMaps.add(new SchoolMap(SchoolMap.SchoolMapType.Class, "qingshu_peixian",
                "px0001,px0039,px0041,px0048,px0115,px0130,px0140,px0141,px0166,px0205,px0207,px0217,px0230,px0234,px0247,px0304,px0326,px0332,px0340,px0419,px0460,px0461,px0467", "gz_peixian"));


        duplicateFiles(schoolMaps);

        duplicateSyncUserSchoolFile();
    }

    private static void duplicateFiles(List<SchoolMap> schoolMaps) {
        Map<String, String> schemaMap = new HashMap<>();
        for (SchoolMap schoolMap : schoolMaps) {
            schemaMap.put(schoolMap.getGzSchema(), schoolMap.getDgSchema());
        }

        Map<String, String> gzSqlMap = new HashMap<>();

        duplicateFile(schemaMap, syncBasicDataSqlFile, gzSqlMap);

        duplicateFile(schoolMaps, SchoolMap.SchoolMapType.College, syncMajorByCollegeFile, gzSqlMap);
        duplicateFile(schoolMaps, SchoolMap.SchoolMapType.Academy, syncMajorByAcademyFile, gzSqlMap);
        duplicateFile(schoolMaps, SchoolMap.SchoolMapType.Class, syncMajorByClassFile, gzSqlMap);

        duplicateFile(schemaMap, syncOtherDataSqlFile, gzSqlMap);

        for (String gzSchema : gzSqlMap.keySet()) {
            String saveFileName = gzSchema + ".sql";
            writeFile(saveFileName, gzSqlMap.get(gzSchema));
        }
    }

    private static void duplicateSyncUserSchoolFile() {
        String[] symbols = "peixian".split(",");
        String[] schemas = "qingshu_peixian".split(",");

        String result = "set sql_safe_updates= 0;\n" +
                "DELETE FROM `tmp`.`user_school`;\n\n";

        String sql  = "INSERT INTO `tmp`.`user_school`(`user_id`,`school_id`,`user_role`)\n" +
                "select global_user_id, (select id from tmp.school where symbol = 'in_symbol'), 2 FROM `in_schema`.teacher;\n" +
                "INSERT INTO `tmp`.`user_school`(`user_id`,`school_id`,`user_role`)\n" +
                "select global_user_id, (select id from tmp.school where symbol = 'in_symbol'), 1 FROM `in_schema`.student;\n";
        for (int i=0; i<symbols.length; i++) {
            String tmp = sql.replaceAll("in_symbol", symbols[i]);
            tmp = tmp.replaceAll("in_schema", schemas[i]);

            result += tmp;
        }

        writeFile("sync_user_school_prod.sql", result);
    }

    private static void duplicateFile(List<SchoolMap> schoolMaps, SchoolMap.SchoolMapType type, String file, Map<String, String> gzSqlMap) {
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String thisLine;
            while ((thisLine = br.readLine()) != null) {
                for (SchoolMap schoolMap: schoolMaps){
                    if (schoolMap.getType() != type){
                        continue;
                    }

                    String tmpLine = thisLine.replaceAll("from_db", schoolMap.getDgSchema());
                    tmpLine = tmpLine.replaceAll("to_db", schoolMap.getGzSchema());
                    if(type == SchoolMap.SchoolMapType.Class){
                        tmpLine = tmpLine.replaceAll("in_center_names", wrapC(schoolMap.getCenterName()));
                    }else{
                        tmpLine = tmpLine.replaceAll("in_center_name", schoolMap.getCenterName());
                    }

                    gzSqlMap.put(schoolMap.getGzSchema(), gzSqlMap.getOrDefault(schoolMap.getGzSchema(), "") + "\n" + tmpLine);
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static String wrapC(String centerName) {
        String result= "'" + centerName + "'";
        result = result.replaceAll(",", "', '");
        return result;
    }

    private static void duplicateFile(Map<String, String> schemaMap, String file, Map<String, String> gzSqlMap) {
        //String fileName = file.substring(file.lastIndexOf("\\") + 1);

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String thisLine;
            while ((thisLine = br.readLine()) != null) {
                for (String gzSchema : schemaMap.keySet()) {
                    String tmpLine = thisLine.replaceAll("from_db", schemaMap.get(gzSchema));
                    tmpLine = tmpLine.replaceAll("to_db", gzSchema);

                    gzSqlMap.put(gzSchema, gzSqlMap.getOrDefault(gzSchema, "") + "\n" + tmpLine);
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void writeFile(String fileName, String content) {
        try (BufferedWriter br = new BufferedWriter(new FileWriter(saveDir + fileName))) {
            br.write(content);
            br.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
