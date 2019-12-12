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


        /*schoolMaps.add(new SchoolMap(SchoolMap.SchoolMapType.Academy, "qingshu_hpu", "A函授站", "gz_hpu_a"));
        schoolMaps.add(new SchoolMap(SchoolMap.SchoolMapType.Academy, "qingshu_hpu", "B函授站", "gz_hpu_b"));
        schoolMaps.add(new SchoolMap(SchoolMap.SchoolMapType.Academy, "qingshu_hpu", "C函授站", "gz_hpu_c"));
        schoolMaps.add(new SchoolMap(SchoolMap.SchoolMapType.Academy, "qingshu_hpu", "D函授站", "gz_hpu_d"));*/

/*

        schoolMaps.add(new SchoolMap(SchoolMap.SchoolMapType.College, "qingshu_ahdzxx", "安徽化工学校", "gz_aqzy_ahhg"));
        schoolMaps.add(new SchoolMap(SchoolMap.SchoolMapType.College, "qingshu_ahdzxx", "怀宁县宜城科技学校", "gz_aqzy_hncykj"));
        schoolMaps.add(new SchoolMap(SchoolMap.SchoolMapType.College, "qingshu_ahdzxx", "宿松县宿松中德职业技术学校", "gz_aqzy_sszd"));
        schoolMaps.add(new SchoolMap(SchoolMap.SchoolMapType.College, "qingshu_ahdzxx", "太湖县太湖职业技术学院", "gz_aqzy_thzy"));
        schoolMaps.add(new SchoolMap(SchoolMap.SchoolMapType.College, "qingshu_ahdzxx", "桐城市江淮工业学校", "gz_aqzy_tcjh"));
        schoolMaps.add(new SchoolMap(SchoolMap.SchoolMapType.College, "qingshu_ahdzxx", "望江县皖江中等专业学校", "gz_aqzy_wjwj"));
        schoolMaps.add(new SchoolMap(SchoolMap.SchoolMapType.College, "qingshu_ahdzxx", "校本部", "gz_aqzy_xy"));
        schoolMaps.add(new SchoolMap(SchoolMap.SchoolMapType.College, "qingshu_ahdzxx", "岳西县大别山科技学校", "gz_aqzy_yxdbs"));

        schoolMaps.add(new SchoolMap(SchoolMap.SchoolMapType.College, "qingshu_ahnd", "长丰党校", "gz_ahnd_cf"));
        schoolMaps.add(new SchoolMap(SchoolMap.SchoolMapType.College, "qingshu_ahnd", "池阳", "gz_ahnd_cy"));
        schoolMaps.add(new SchoolMap(SchoolMap.SchoolMapType.College, "qingshu_ahnd", "广德电大", "gz_ahnd_gddd"));
        schoolMaps.add(new SchoolMap(SchoolMap.SchoolMapType.College, "qingshu_ahnd", "郭河", "gz_ahnd_gh"));
        schoolMaps.add(new SchoolMap(SchoolMap.SchoolMapType.College, "qingshu_ahnd", "黄山职业学院", "gz_ahnd_hszy"));
        schoolMaps.add(new SchoolMap(SchoolMap.SchoolMapType.College, "qingshu_ahnd", "校本部", "gz_ahnd_xy"));
        schoolMaps.add(new SchoolMap(SchoolMap.SchoolMapType.College, "qingshu_ahnd", "宣城党校", "gz_ahnd_xcdx"));
        schoolMaps.add(new SchoolMap(SchoolMap.SchoolMapType.College, "qingshu_ahnd", "岳西党校", "gz_ahnd_yxdx"));
        schoolMaps.add(new SchoolMap(SchoolMap.SchoolMapType.College, "qingshu_ahnd", "泾县", "gz_ahnd_jx"));
        schoolMaps.add(new SchoolMap(SchoolMap.SchoolMapType.College, "qingshu_ahnd", "旌德", "gz_ahnd_jd"));
        schoolMaps.add(new SchoolMap(SchoolMap.SchoolMapType.College, "qingshu_ahnd", "砀山党校", "gz_ahnd_ysdx"));
        schoolMaps.add(new SchoolMap(SchoolMap.SchoolMapType.College, "qingshu_ahnd", "埇桥党校", "gz_ahnd_yqdx"));

        schoolMaps.add(new SchoolMap(SchoolMap.SchoolMapType.College, "qingshu_hudazx", "湖北大学知行学院", "gz_huda_zx"));

        schoolMaps.add(new SchoolMap(SchoolMap.SchoolMapType.Academy, "qingshu_qlg", "信息工程系", "gz_qlg_xxgc"));
        schoolMaps.add(new SchoolMap(SchoolMap.SchoolMapType.Academy, "qingshu_qlg", "公共服务系", "gz_qlg_ggfw"));
        schoolMaps.add(new SchoolMap(SchoolMap.SchoolMapType.Academy, "qingshu_qlg", "建筑工程系", "gz_qlg_jzgc"));
        schoolMaps.add(new SchoolMap(SchoolMap.SchoolMapType.Academy, "qingshu_qlg", "机电工程系", "gz_qlg_jdgc"));
        schoolMaps.add(new SchoolMap(SchoolMap.SchoolMapType.Academy, "qingshu_qlg", "经济管理系", "gz_qlg_jjgl"));

        schoolMaps.add(new SchoolMap(SchoolMap.SchoolMapType.College, "qingshu_cyszgz", "朝阳师范高等专科学校", "gz_cysf_xy"));

        schoolMaps.add(new SchoolMap(SchoolMap.SchoolMapType.College, "qingshu_bbjjzy", "蚌埠经济职业技术学院", "gz_bbjjzy"));

        schoolMaps.add(new SchoolMap(SchoolMap.SchoolMapType.Class, "qingshu_hjgcxy", "供热K191,建电K191", "gz_lnjz_hjgc"));
        schoolMaps.add(new SchoolMap(SchoolMap.SchoolMapType.Class, "qingshu_jdgcxy", "机械K191,机械K192,机械K193,机械K194,机械K195,机械K196,机械K197,机械K198,自动化K191,自动化K192,自动化K193,自动化K194,自动化K195,自动化K196,自动化K197,自动化K198,自动化K199", "gz_lnjz_jdgc"));
        schoolMaps.add(new SchoolMap(SchoolMap.SchoolMapType.Class, "qingshu_jzjjxy", "造价K191,造价K1910,造价K192,造价K193,造价K194,造价K195,造价K196,造价K197,造价K198,造价K199", "gz_lnjz_jzjj"));
        schoolMaps.add(new SchoolMap(SchoolMap.SchoolMapType.Class, "qingshu_gsglxy", "物流K191,电商K191", "gz_lnjz_gsgl"));
        schoolMaps.add(new SchoolMap(SchoolMap.SchoolMapType.Class, "qingshu_xxgcxy", "大数据K191,网络K191,网络K1910,网络K1911,网络K1912,网络K1913,网络K192,网络K193,网络K194,网络K195,网络K196,网络K197,网络K198,网络K199", "gz_lnjz_xxgc"));
        schoolMaps.add(new SchoolMap(SchoolMap.SchoolMapType.Class, "qingshu_jzysxy", "室设K191,室设K192,室设K193,装饰K191", "gz_lnjz_jzys"));
        schoolMaps.add(new SchoolMap(SchoolMap.SchoolMapType.Class, "qingshu_gcglxy", "建管K191,建管K1910,建管K192,建管K193,建管K194,建管K195,建管K196,建管K197,建管K198,建管K199", "gz_lnjz_gcgl"));
        schoolMaps.add(new SchoolMap(SchoolMap.SchoolMapType.Class, "qingshu_cjglxy", "会计K191,会计K192,会计K193,会计K194,会计K195,会计K196,会计K197", "gz_lnjz_cjgl"));
        schoolMaps.add(new SchoolMap(SchoolMap.SchoolMapType.Class, "qingshu_jtgcxy", "道桥K191", "gz_lnjz_jtgc"));
        schoolMaps.add(new SchoolMap(SchoolMap.SchoolMapType.Class, "qingshu_tmgcxy", "建工K191,建工K1910,建工K1911,建工K192,建工K193,建工K194,建工K195,建工K196,建工K197,建工K198,建工K199,监理K191,监理K192,监理K193,监理K194,监理K195", "gz_lnjz_tmgc"));
        schoolMaps.add(new SchoolMap(SchoolMap.SchoolMapType.Class, "qingshu_qcgcxy", "新能源汽车K191,新能源汽车K192,新能源汽车K193,新能源汽车K194,新能源汽车K195,新能源汽车K196,新能源汽车K197", "gz_lnjz_qcgc"));
*/

        schoolMaps.add(new SchoolMap(SchoolMap.SchoolMapType.College, "qingshu_ceshia", "自动化测试函授站", "gz_ceshia"));

        duplicateFiles(schoolMaps);

        //duplicateSyncUserSchoolFile();
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
        String[] symbols = "aqzyahhg,aqzyhncykj,aqzysszd,aqzytcjh,aqzythzy,aqzywjwj,aqzyxy,aqzyyxdbs,ahndcf,ahndcy,ahndgddd,ahndgh,ahndhszy,ahndjd,ahndjx,ahndxcdx,ahndxy,ahndyqdx,ahndysdx,ahndyxdx,cysfgz,bbjjgz,hudazxgz,qlgggfw,qlgjdgc,qlgjjgl,qlgjzgc,qlgxxgc,lnjzhjgc,lnjzjdgc,lnjzjzjj,lnjzgsgl,lnjzxxgc,lnjzjzys,lnjzgcgl,lnjzcjgl,lnjzjtgc,lnjztmgc,lnjzqcgc".split(",");
        String[] schemas = "gz_aqzy_ahhg,gz_aqzy_hncykj,gz_aqzy_sszd,gz_aqzy_tcjh,gz_aqzy_thzy,gz_aqzy_wjwj,gz_aqzy_xy,gz_aqzy_yxdbs,gz_ahnd_cf,gz_ahnd_cy,gz_ahnd_gddd,gz_ahnd_gh,gz_ahnd_hszy,gz_ahnd_jd,gz_ahnd_jx,gz_ahnd_xcdx,gz_ahnd_xy,gz_ahnd_yqdx,gz_ahnd_ysdx,gz_ahnd_yxdx,gz_cysf_xy,gz_bbjjzy,gz_huda_zx,gz_qlg_ggfw,gz_qlg_jdgc,gz_qlg_jjgl,gz_qlg_jzgc,gz_qlg_xxgc,gz_lnjz_hjgc,gz_lnjz_jdgc,gz_lnjz_jzjj,gz_lnjz_gsgl,gz_lnjz_xxgc,gz_lnjz_jzys,gz_lnjz_gcgl,gz_lnjz_cjgl,gz_lnjz_jtgc,gz_lnjz_tmgc,gz_lnjz_qcgc".split(",");

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
