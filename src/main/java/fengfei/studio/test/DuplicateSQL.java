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
        /*schoolMaps.add(new SchoolMap(SchoolMap.SchoolMapType.College, "qingshu_demo", "南京学习中心", "gz_testdg_nj"));
        schoolMaps.add(new SchoolMap(SchoolMap.SchoolMapType.College, "qingshu_demo", "苏州学习中心", "gz_testdg_sz"));
        schoolMaps.add(new SchoolMap(SchoolMap.SchoolMapType.College, "qingshu_demo", "12332111111", "gz_testdg_111"));*/

        /*schoolMaps.add(new SchoolMap(SchoolMap.SchoolMapType.Academy, "qingshu_academy", "academy1", "gz_academy_1"));
        schoolMaps.add(new SchoolMap(SchoolMap.SchoolMapType.Academy, "qingshu_academy", "academy2", "gz_academy_2"));*/


        /*schoolMaps.add(new SchoolMap(SchoolMap.SchoolMapType.Class, "qingshu_gllg", "201501交通工程", "gz_gllg_a"));
        schoolMaps.add(new SchoolMap(SchoolMap.SchoolMapType.Class, "qingshu_gllg", "201501软件测试", "gz_gllg_a"));
        schoolMaps.add(new SchoolMap(SchoolMap.SchoolMapType.Class, "qingshu_gllg", "201501市政工程技术", "gz_gllg_a"));
        schoolMaps.add(new SchoolMap(SchoolMap.SchoolMapType.Class, "qingshu_gllg", "201501土木工程", "gz_gllg_b"));
        schoolMaps.add(new SchoolMap(SchoolMap.SchoolMapType.Class, "qingshu_gllg", "201901土木工程", "gz_gllg_b"));
        schoolMaps.add(new SchoolMap(SchoolMap.SchoolMapType.Class, "qingshu_gllg", "201501物理光学", "gz_gllg_c"));
        schoolMaps.add(new SchoolMap(SchoolMap.SchoolMapType.Class, "qingshu_gllg", "201901物理光学", "gz_gllg_c"));*/




        schoolMaps.add(new SchoolMap(SchoolMap.SchoolMapType.Class, "qingshu_cqdxxy", "万州-建经1班", "gz_cqdx_tmgc"));
        schoolMaps.add(new SchoolMap(SchoolMap.SchoolMapType.Class, "qingshu_cqdxxy", "巫溪-建经2班", "gz_cqdx_tmgc"));
        schoolMaps.add(new SchoolMap(SchoolMap.SchoolMapType.Class, "qingshu_cqdxxy", "巫溪-建经3班", "gz_cqdx_tmgc"));
        schoolMaps.add(new SchoolMap(SchoolMap.SchoolMapType.Class, "qingshu_cqdxxy", "电讯本部-建经4班", "gz_cqdx_tmgc"));
        schoolMaps.add(new SchoolMap(SchoolMap.SchoolMapType.Class, "qingshu_cqdxxy", "北碚-建筑10班", "gz_cqdx_tmgc"));
        schoolMaps.add(new SchoolMap(SchoolMap.SchoolMapType.Class, "qingshu_cqdxxy", "北碚-建筑11班", "gz_cqdx_tmgc"));
        schoolMaps.add(new SchoolMap(SchoolMap.SchoolMapType.Class, "qingshu_cqdxxy", "北碚-建筑12班", "gz_cqdx_tmgc"));
        schoolMaps.add(new SchoolMap(SchoolMap.SchoolMapType.Class, "qingshu_cqdxxy", "九龙坡-建筑13班", "gz_cqdx_tmgc"));
        schoolMaps.add(new SchoolMap(SchoolMap.SchoolMapType.Class, "qingshu_cqdxxy", "电讯本部-建筑14班", "gz_cqdx_tmgc"));
        schoolMaps.add(new SchoolMap(SchoolMap.SchoolMapType.Class, "qingshu_cqdxxy", "电讯本部-建筑15班", "gz_cqdx_tmgc"));
        schoolMaps.add(new SchoolMap(SchoolMap.SchoolMapType.Class, "qingshu_cqdxxy", "电讯本部-建筑16班", "gz_cqdx_tmgc"));
        schoolMaps.add(new SchoolMap(SchoolMap.SchoolMapType.Class, "qingshu_cqdxxy", "万州-建筑17班", "gz_cqdx_tmgc"));
        schoolMaps.add(new SchoolMap(SchoolMap.SchoolMapType.Class, "qingshu_cqdxxy", "巫溪-建筑18班", "gz_cqdx_tmgc"));
        schoolMaps.add(new SchoolMap(SchoolMap.SchoolMapType.Class, "qingshu_cqdxxy", "电讯本部-建筑1班", "gz_cqdx_tmgc"));
        schoolMaps.add(new SchoolMap(SchoolMap.SchoolMapType.Class, "qingshu_cqdxxy", "电讯本部-建筑2班", "gz_cqdx_tmgc"));
        schoolMaps.add(new SchoolMap(SchoolMap.SchoolMapType.Class, "qingshu_cqdxxy", "电讯本部-建筑3班", "gz_cqdx_tmgc"));
        schoolMaps.add(new SchoolMap(SchoolMap.SchoolMapType.Class, "qingshu_cqdxxy", "万州-建筑4班", "gz_cqdx_tmgc"));
        schoolMaps.add(new SchoolMap(SchoolMap.SchoolMapType.Class, "qingshu_cqdxxy", "江北区-建筑5班", "gz_cqdx_tmgc"));
        schoolMaps.add(new SchoolMap(SchoolMap.SchoolMapType.Class, "qingshu_cqdxxy", "江北区-建筑6班", "gz_cqdx_tmgc"));
        schoolMaps.add(new SchoolMap(SchoolMap.SchoolMapType.Class, "qingshu_cqdxxy", "江北区-建筑7班", "gz_cqdx_tmgc"));
        schoolMaps.add(new SchoolMap(SchoolMap.SchoolMapType.Class, "qingshu_cqdxxy", "江北区-建筑8班", "gz_cqdx_tmgc"));
        schoolMaps.add(new SchoolMap(SchoolMap.SchoolMapType.Class, "qingshu_cqdxxy", "江北区-建筑9班", "gz_cqdx_tmgc"));
        schoolMaps.add(new SchoolMap(SchoolMap.SchoolMapType.Class, "qingshu_cqdxxy", "渝中区-建筑智能1班", "gz_cqdx_tmgc"));
        schoolMaps.add(new SchoolMap(SchoolMap.SchoolMapType.Class, "qingshu_cqdxxy", "电讯本部-建筑智能2班", "gz_cqdx_tmgc"));
        schoolMaps.add(new SchoolMap(SchoolMap.SchoolMapType.Class, "qingshu_cqdxxy", "电讯本部-市政1班", "gz_cqdx_tmgc"));
        schoolMaps.add(new SchoolMap(SchoolMap.SchoolMapType.Class, "qingshu_cqdxxy", "渝中区-市政2班", "gz_cqdx_tmgc"));
        schoolMaps.add(new SchoolMap(SchoolMap.SchoolMapType.Class, "qingshu_cqdxxy", "电讯本部-市政3班", "gz_cqdx_tmgc"));
        schoolMaps.add(new SchoolMap(SchoolMap.SchoolMapType.Class, "qingshu_cqdxxy", "万州-市政4班", "gz_cqdx_tmgc"));
        schoolMaps.add(new SchoolMap(SchoolMap.SchoolMapType.Class, "qingshu_cqdxxy", "万州-市政5班", "gz_cqdx_tmgc"));
        schoolMaps.add(new SchoolMap(SchoolMap.SchoolMapType.Class, "qingshu_cqdxxy", "巫溪-市政6班", "gz_cqdx_tmgc"));
        schoolMaps.add(new SchoolMap(SchoolMap.SchoolMapType.Class, "qingshu_cqdxxy", "电讯本部-通信1班", "gz_cqdx_tx"));
        schoolMaps.add(new SchoolMap(SchoolMap.SchoolMapType.Class, "qingshu_cqdxxy", "电讯本部-物联网1班", "gz_cqdx_tx"));
        schoolMaps.add(new SchoolMap(SchoolMap.SchoolMapType.Class, "qingshu_cqdxxy", "万州-物联网2班", "gz_cqdx_tx"));
        schoolMaps.add(new SchoolMap(SchoolMap.SchoolMapType.Class, "qingshu_cqdxxy", "巫溪-物联网3班", "gz_cqdx_tx"));
        schoolMaps.add(new SchoolMap(SchoolMap.SchoolMapType.Class, "qingshu_cqdxxy", "万州-新能源1班", "gz_cqdx_qczz"));
        schoolMaps.add(new SchoolMap(SchoolMap.SchoolMapType.Class, "qingshu_cqdxxy", "电讯本部-新能源2班", "gz_cqdx_qczz"));
        schoolMaps.add(new SchoolMap(SchoolMap.SchoolMapType.Class, "qingshu_cqdxxy", "九龙坡-机电1班", "gz_cqdx_qczz"));
        schoolMaps.add(new SchoolMap(SchoolMap.SchoolMapType.Class, "qingshu_cqdxxy", "九龙坡-市场营销1班", "gz_cqdx_swgl"));
        schoolMaps.add(new SchoolMap(SchoolMap.SchoolMapType.Class, "qingshu_cqdxxy", "电讯本部-市场营销2班", "gz_cqdx_swgl"));
        schoolMaps.add(new SchoolMap(SchoolMap.SchoolMapType.Class, "qingshu_cqdxxy", "电讯本部-市场营销3班", "gz_cqdx_swgl"));
        schoolMaps.add(new SchoolMap(SchoolMap.SchoolMapType.Class, "qingshu_cqdxxy", "万州-市场营销4班", "gz_cqdx_swgl"));
        schoolMaps.add(new SchoolMap(SchoolMap.SchoolMapType.Class, "qingshu_cqdxxy", "巫溪-市场营销5班", "gz_cqdx_swgl"));




        schoolMaps.add(new SchoolMap(SchoolMap.SchoolMapType.Class, "qingshu_nyca", "牧医K191班", "gz_nyca_dwkx"));
        schoolMaps.add(new SchoolMap(SchoolMap.SchoolMapType.Class, "qingshu_nyca", "农技K193", "gz_nyca_nykx"));
        schoolMaps.add(new SchoolMap(SchoolMap.SchoolMapType.Class, "qingshu_nyca", "农技K192", "gz_nyca_nykx"));
        schoolMaps.add(new SchoolMap(SchoolMap.SchoolMapType.Class, "qingshu_nyca", "园艺K193", "gz_nyca_nykx"));
        schoolMaps.add(new SchoolMap(SchoolMap.SchoolMapType.Class, "qingshu_nyca", "汽检K191班", "gz_nyca_qcgc"));
        schoolMaps.add(new SchoolMap(SchoolMap.SchoolMapType.Class, "qingshu_nyca", "幼儿健康K191", "gz_nyca_rwys"));
        schoolMaps.add(new SchoolMap(SchoolMap.SchoolMapType.Class, "qingshu_nyca", "电商K191班", "gz_nyca_sxy"));
        schoolMaps.add(new SchoolMap(SchoolMap.SchoolMapType.Class, "qingshu_nyca", "金融K191班", "gz_nyca_sxy"));
        schoolMaps.add(new SchoolMap(SchoolMap.SchoolMapType.Class, "qingshu_nyca", "微机K191", "gz_nyca_xxgc"));
        schoolMaps.add(new SchoolMap(SchoolMap.SchoolMapType.Class, "qingshu_nyca", "微机K192", "gz_nyca_xxgc"));



        schoolMaps.add(new SchoolMap(SchoolMap.SchoolMapType.Class, "qingshu_hnzy", "S19房地产经营与管理", "gz_hnzy_gcgl"));
        schoolMaps.add(new SchoolMap(SchoolMap.SchoolMapType.Class, "qingshu_hnzy", "S19工程造价", "gz_hnzy_gcgl"));
        schoolMaps.add(new SchoolMap(SchoolMap.SchoolMapType.Class, "qingshu_hnzy", "S19建筑工程技术", "gz_hnzy_gcgl"));
        schoolMaps.add(new SchoolMap(SchoolMap.SchoolMapType.Class, "qingshu_hnzy", "S19建筑装饰工程技术", "gz_hnzy_gcgl"));
        schoolMaps.add(new SchoolMap(SchoolMap.SchoolMapType.Class, "qingshu_hnzy", "S19视觉传播设计与制作", "gz_hnzy_gcgl"));
        schoolMaps.add(new SchoolMap(SchoolMap.SchoolMapType.Class, "qingshu_hnzy", "S19物业管理", "gz_hnzy_gcgl"));
        schoolMaps.add(new SchoolMap(SchoolMap.SchoolMapType.Class, "qingshu_hnzy", "S19火电厂", "gz_hnzy_jdgc"));
        schoolMaps.add(new SchoolMap(SchoolMap.SchoolMapType.Class, "qingshu_hnzy", "S19机体3（社）", "gz_hnzy_jdgc"));
        schoolMaps.add(new SchoolMap(SchoolMap.SchoolMapType.Class, "qingshu_hnzy", "S19机体4（社）", "gz_hnzy_jdgc"));
        schoolMaps.add(new SchoolMap(SchoolMap.SchoolMapType.Class, "qingshu_hnzy", "S19机体5（社）", "gz_hnzy_jdgc"));
        schoolMaps.add(new SchoolMap(SchoolMap.SchoolMapType.Class, "qingshu_hnzy", "S19机体6（社）", "gz_hnzy_jdgc"));
        schoolMaps.add(new SchoolMap(SchoolMap.SchoolMapType.Class, "qingshu_hnzy", "S19矿山机电2（社）", "gz_hnzy_jdgc"));
        schoolMaps.add(new SchoolMap(SchoolMap.SchoolMapType.Class, "qingshu_hnzy", "S19矿山机电3（社）", "gz_hnzy_jdgc"));
        schoolMaps.add(new SchoolMap(SchoolMap.SchoolMapType.Class, "qingshu_hnzy", "S19汽修2（社）", "gz_hnzy_jdgc"));
        schoolMaps.add(new SchoolMap(SchoolMap.SchoolMapType.Class, "qingshu_hnzy", "S19电子商务", "gz_hnzy_jjgl"));
        schoolMaps.add(new SchoolMap(SchoolMap.SchoolMapType.Class, "qingshu_hnzy", "S19酒店管理", "gz_hnzy_jjgl"));
        schoolMaps.add(new SchoolMap(SchoolMap.SchoolMapType.Class, "qingshu_hnzy", "S19旅游管理", "gz_hnzy_jjgl"));
        schoolMaps.add(new SchoolMap(SchoolMap.SchoolMapType.Class, "qingshu_hnzy", "S19市场营销", "gz_hnzy_jjgl"));
        schoolMaps.add(new SchoolMap(SchoolMap.SchoolMapType.Class, "qingshu_hnzy", "S19物流管理", "gz_hnzy_jjgl"));
        schoolMaps.add(new SchoolMap(SchoolMap.SchoolMapType.Class, "qingshu_hnzy", "S19安全技术与管理1班", "gz_hnzy_nygc"));
        schoolMaps.add(new SchoolMap(SchoolMap.SchoolMapType.Class, "qingshu_hnzy", "S19安全技术与管理2班", "gz_hnzy_nygc"));
        schoolMaps.add(new SchoolMap(SchoolMap.SchoolMapType.Class, "qingshu_hnzy", "S19安全技术与管理3班", "gz_hnzy_nygc"));
        schoolMaps.add(new SchoolMap(SchoolMap.SchoolMapType.Class, "qingshu_hnzy", "S19安全技术与管理4班", "gz_hnzy_nygc"));
        schoolMaps.add(new SchoolMap(SchoolMap.SchoolMapType.Class, "qingshu_hnzy", "S19测绘工程技术", "gz_hnzy_nygc"));
        schoolMaps.add(new SchoolMap(SchoolMap.SchoolMapType.Class, "qingshu_hnzy", "S19城市轨道交通工程技术", "gz_hnzy_nygc"));
        schoolMaps.add(new SchoolMap(SchoolMap.SchoolMapType.Class, "qingshu_hnzy", "S19城市轨道交通运营管理", "gz_hnzy_nygc"));
        schoolMaps.add(new SchoolMap(SchoolMap.SchoolMapType.Class, "qingshu_hnzy", "S19矿井通风与安全1班", "gz_hnzy_nygc"));
        schoolMaps.add(new SchoolMap(SchoolMap.SchoolMapType.Class, "qingshu_hnzy", "S19矿山地质", "gz_hnzy_nygc"));
        schoolMaps.add(new SchoolMap(SchoolMap.SchoolMapType.Class, "qingshu_hnzy", "S19煤矿开采技术", "gz_hnzy_nygc"));
        schoolMaps.add(new SchoolMap(SchoolMap.SchoolMapType.Class, "qingshu_hnzy", "S19幼儿发展与健康教育", "gz_hnzy_rwjy"));
        schoolMaps.add(new SchoolMap(SchoolMap.SchoolMapType.Class, "qingshu_hnzy", "S19电力系统及自动化", "gz_hnzy_xxdq"));
        schoolMaps.add(new SchoolMap(SchoolMap.SchoolMapType.Class, "qingshu_hnzy", "S19电气自动化技术1班", "gz_hnzy_xxdq"));
        schoolMaps.add(new SchoolMap(SchoolMap.SchoolMapType.Class, "qingshu_hnzy", "S19电气自动化技术2班", "gz_hnzy_xxdq"));
        schoolMaps.add(new SchoolMap(SchoolMap.SchoolMapType.Class, "qingshu_hnzy", "S19电气自动化技术3班", "gz_hnzy_xxdq"));
        schoolMaps.add(new SchoolMap(SchoolMap.SchoolMapType.Class, "qingshu_hnzy", "S19工业机器人技术", "gz_hnzy_xxdq"));
        schoolMaps.add(new SchoolMap(SchoolMap.SchoolMapType.Class, "qingshu_hnzy", "S19计算机应用技术1班", "gz_hnzy_xxdq"));
        schoolMaps.add(new SchoolMap(SchoolMap.SchoolMapType.Class, "qingshu_hnzy", "S19计算机应用技术2班", "gz_hnzy_xxdq"));
        schoolMaps.add(new SchoolMap(SchoolMap.SchoolMapType.Academy, "qingshu_hnzy", "医学院", "gz_hnzy_yxy"));



        schoolMaps.add(new SchoolMap(SchoolMap.SchoolMapType.College, "qingshu_lnlgxy", "辽宁理工学院", "gz_lnlg_xy"));


        duplicateFiles(schoolMaps);


        duplicateSyncUserSchoolFile();
    }

    private static void duplicateFiles(List<SchoolMap> schoolMaps) {
        Map<String, String> schemaMap = new HashMap<>();
        for (SchoolMap schoolMap : schoolMaps) {
            schemaMap.put(schoolMap.getGzSchema(), schoolMap.getDgSchema());
        }

        duplicateFile(schemaMap, syncBasicDataSqlFile);
        duplicateFile(schemaMap, syncOtherDataSqlFile);

        duplicateFile(schoolMaps, SchoolMap.SchoolMapType.College, syncMajorByCollegeFile);
        duplicateFile(schoolMaps, SchoolMap.SchoolMapType.Academy, syncMajorByAcademyFile);
        duplicateFile(schoolMaps, SchoolMap.SchoolMapType.Class, syncMajorByClassFile);
    }

    private static void duplicateSyncUserSchoolFile() {
        String[] symbols = "cqdxqczz,cqdxswgl,cqdxtmgc,cqdxtx,nycadwkx,nycanykx,nycaqcgc,nycarwys,nycasxy,nycaxxgc,hnzygcgl,hnzyjdgc,hnzyjjgl,hnzynygc,hnzyrwjy,hnzyxxdq,hnzyyxy,lnlggz".split(",");
        String[] schemas = "gz_cqdx_qczz,gz_cqdx_swgl,gz_cqdx_tmgc,gz_cqdx_tx,gz_nyca_dwkx,gz_nyca_nykx,gz_nyca_qcgc,gz_nyca_rwys,gz_nyca_sxy,gz_nyca_xxgc,gz_hnzy_gcgl,gz_hnzy_jdgc,gz_hnzy_jjgl,gz_hnzy_nygc,gz_hnzy_rwjy,gz_hnzy_xxdq,gz_hnzy_yxy,gz_lnlg_xy".split(",");

        String result = "set sql_safe_updates= 0;\n" +
                "\n" +
                "DELETE us.* FROM `tmp`.`user_school` us\n" +
                "INNER JOIN tmp.school s ON us.school_id = s.id \n" +
                "WHERE s.symbol in (";
        for (int i=0; i<symbols.length; i++) {
            result += "'" + symbols[i] + "', ";
        }
        result = result.substring(0, result.length()-2);
        result += ");\n\n";


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

    private static void duplicateFile(List<SchoolMap> schoolMaps, SchoolMap.SchoolMapType type, String file) {
        String fileName = file.substring(file.lastIndexOf("\\") + 1);

        Map<String, String> gzSqlMap = new HashMap<>();
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String thisLine;
            while ((thisLine = br.readLine()) != null) {
                for (SchoolMap schoolMap: schoolMaps){
                    if (schoolMap.getType() != type){
                        continue;
                    }

                    String tmpLine = thisLine.replaceAll("from_db", schoolMap.getDgSchema());
                    tmpLine = tmpLine.replaceAll("to_db", schoolMap.getGzSchema());
                    tmpLine = tmpLine.replaceAll("in_center_name", schoolMap.getCenterName());

                    String key = schoolMap.getGzSchema() + "_" + schoolMap.getCenterName();
                    gzSqlMap.put(key, gzSqlMap.getOrDefault(key, "") + "\n" + tmpLine);
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        for (String key : gzSqlMap.keySet()) {
            String saveFileName = key + "__" + fileName;
            writeFile(saveFileName, gzSqlMap.get(key));
        }
    }

    private static void duplicateFile(Map<String, String> schemaMap, String file) {
        String fileName = file.substring(file.lastIndexOf("\\") + 1);

        Map<String, String> gzSqlMap = new HashMap<>();
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

        for (String gzSchema : gzSqlMap.keySet()) {
            String saveFileName = gzSchema + "__" + fileName;
            writeFile(saveFileName, gzSqlMap.get(gzSchema));
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
