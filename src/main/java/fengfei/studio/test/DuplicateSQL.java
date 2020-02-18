package fengfei.studio.test;

import java.io.*;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DuplicateSQL {

    private final static String saveDir = "C:\\Users\\fengfei\\Desktop\\迁移\\prod\\";
    private final static String syncBasicDataSqlFile = "C:\\Users\\fengfei\\Desktop\\迁移\\sync_basic_data.sql";
    private final static String syncMajorByCollegeFile = "C:\\Users\\fengfei\\Desktop\\迁移\\sync_major_by_college.sql";
    private final static String syncMajorByAcademyFile = "C:\\Users\\fengfei\\Desktop\\迁移\\sync_major_by_academy.sql";
    private final static String syncMajorByClassFile = "C:\\Users\\fengfei\\Desktop\\迁移\\sync_major_by_class.sql";
    private final static String syncOtherDataSqlFile = "C:\\Users\\fengfei\\Desktop\\迁移\\sync_other_data.sql";

    public static void main(String[] args) {
        List<SchoolMap> schoolMaps = new ArrayList<>();


        schoolMaps.add(new SchoolMap(SchoolMap.SchoolMapType.Class, "qingshu_bbjjzy", "扩19财会1班,扩19财会2班,扩19财会3班,扩19电商1班,扩19电商2班,扩19连锁,扩19物流1班,扩19物流2班,扩19营销1班,扩19营销2班,扩19营销3班,扩19营销4班,扩19营销5班", "gz_bbjjzy_cj"));
        schoolMaps.add(new SchoolMap(SchoolMap.SchoolMapType.Class, "qingshu_bbjjzy", "扩19城轨1班,扩19城轨2班,扩19高铁,扩19高铁空乘,扩19酒管,扩19旅游,扩19社区1班,扩19社区2班,扩19社区3班,扩19社区4班,扩19医学,扩19幼儿", "gz_bbjjzy_ggfw"));
        schoolMaps.add(new SchoolMap(SchoolMap.SchoolMapType.Class, "qingshu_bbjjzy", "本校,扩19计算机班,扩19计算机应用二班,扩19计算机应用三班,扩19计算机应用四班,扩19计算机应用五班,扩19计算机应用一班,扩19能源二班,扩19能源三班,扩19能源一班\n", "gz_bbjjzy_xxjs"));
        schoolMaps.add(new SchoolMap(SchoolMap.SchoolMapType.Class, "qingshu_bbjjzy", "扩19艺术设计班", "gz_bbjjzy_ys"));

        schoolMaps.add(new SchoolMap(SchoolMap.SchoolMapType.College, "qingshu_hgszkz", "校本部", "gz_hgszkz_xy"));

        schoolMaps.add(new SchoolMap(SchoolMap.SchoolMapType.College, "qingshu_gxjrxy", "null", "gz_gxjrxy_xy"));


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
        String[] symbols = "bbjjzycj,bbjjzyggfw,bbjjzyxxjs,bbjjzyys,hgszkzxy,gxjrxyxy".split(",");
        String[] schemas = "gz_bbjjzy_cj,gz_bbjjzy_ggfw,gz_bbjjzy_xxjs,gz_bbjjzy_ys,gz_hgszkz_xy,gz_gxjrxy_xy".split(",");

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
