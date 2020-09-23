package fengfei.studio.datajob;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.jdbc.core.JdbcTemplate;

import java.io.File;
import java.io.FileInputStream;
import java.util.*;
import java.util.List;

public class ImportCenterScore {

    public static void main(String[] args) {

        JdbcTemplate schoolTemplate = DBUtil.getSchoolTemplate(1782);

        List<Map<String, Object>> studentList = schoolTemplate.queryForList("SELECT s.id, s.global_user_name, tp.id as tp_id FROM qingshu_xauat.student s\n" +
                "INNER JOIN teachingplan tp ON s.major_id = tp.major_id AND s.semester_id = tp.semester_id where s.semester_id = 9");

        List<Map<String, Object>> courseList = schoolTemplate.queryForList("select id, name FROM course;");

        List<Map<String, Object>> tpcList = schoolTemplate.queryForList("select id, teachingplan_id, course_id, term from teachingplan_course;");


        List<Map<String, Object>> importDatas = getFromExcel("C:\\Users\\fengfei\\Desktop\\设计素材\\2018级专科、专科补考成绩录入 平时成绩.xlsx");


        List<Map<String, Object>> insertDatas = schoolTemplate.queryForList("SELECT s.global_user_name, c.name, tpc.term FROM qingshu_xauat.student_course_activity_score scc\n" +
                "        INNER JOIN student s ON scc.student_id = s.id\n" +
                "        INNER JOIN teachingplan_course tpc ON scc.teachingplan_course_id = tpc.id\n" +
                "        inner join course c on tpc.course_id = c.id\n" +
                "        where scc.updated_time > '2020-06-23 17:00';");


        for (Map<String, Object> map1 : importDatas){
            if (!inserted(map1, insertDatas)){
                System.out.println(map1.get("user_name") + "," + map1.get("course_name") + "," + map1.get("term"));
            }
        }


        //List<Map<String, Object>> resultDatas = genResult(importDatas, studentList, courseList, tpcList);
        //update(schoolTemplate, resultDatas);
    }

    private static boolean inserted(Map<String, Object> map1, List<Map<String, Object>> insertDatas) {
        for (Map<String, Object> insertedMap : insertDatas){
            if (insertedMap.get("global_user_name").toString().equals(map1.get("user_name").toString())
            && insertedMap.get("name").toString().equals(map1.get("course_name").toString())
            && insertedMap.get("term").toString().equals(map1.get("term").toString())){
                return true;
            }
        }

        return  false;
    }

    private static void update(JdbcTemplate schoolTemplate, List<Map<String, Object>> resultDatas) {
        List<Object[]> args = new ArrayList<>();
        for (Map<String, Object> map: resultDatas){
            args.add(new Object[]{map.get("score"), map.get("student_id"), map.get("teachingplan_course_id"), map.get("student_id"), map.get("teachingplan_course_id")});
        }

        int[] result = schoolTemplate.batchUpdate("INSERT INTO `student_course_activity_score`(`score`,`student_id`,`teachingplan_course_id`)\n" +
                "SELECT ?,?,? FROM student_course_activity_score WHERE not exists (select 1 from student_course_activity_score where student_id = ? and teachingplan_course_id = ?);", args);
        for (int id : result){
            if (id == 1){
                System.out.print(id);
            }
        }
    }

    private static List<Map<String, Object>> genResult(List<Map<String, Object>> importDatas, List<Map<String, Object>> studentList, List<Map<String, Object>> courseList, List<Map<String, Object>> tpcList) {
        Map<String, String> studentMap = new HashMap<>();
        Map<String, String> studentMap2 = new HashMap<>();
        for (Map<String, Object> map : studentList){
            studentMap.put(map.get("global_user_name").toString(), (map.get("id").toString()));
            studentMap2.put(map.get("global_user_name").toString(), (map.get("tp_id").toString()));
        }

        Map<String, String> courseMap = new HashMap<>();
        for (Map<String, Object> map : courseList){
            courseMap.put(map.get("name").toString(), map.get("id").toString());
        }

        Map<String, String> tpcMap = new HashMap<>();
        for (Map<String, Object> map : tpcList){
            tpcMap.put(map.get("teachingplan_id").toString() + "_" + map.get("course_id").toString() + "_" + map.get("term").toString(), map.get("id").toString());
        }

        List<Map<String, Object>> resultDatas = new ArrayList<>();
        for (Map<String, Object> studentScoreMap : importDatas){
            Map<String, Object> resultData = new HashMap<>();

            Object tpId = studentMap2.get(studentScoreMap.get("user_name"));
            Object courseId = courseMap.get(studentScoreMap.get("course_name"));
            Object term = studentScoreMap.get("term");
            resultData.put("teachingplan_course_id", tpcMap.get(tpId + "_" + courseId + "_" + term));

            resultData.put("student_id", studentMap.get(studentScoreMap.get("user_name")));
            resultData.put("score", studentScoreMap.get("score"));

            resultDatas.add(resultData);
        }

        return resultDatas;
    }


    private static List<Map<String, Object>> getFromExcel(String filePath) {
        List<Map<String, Object>> result = new ArrayList<>();

        try {
            FileInputStream fis = new FileInputStream(new File(filePath));

            XSSFWorkbook xssfWorkbook = new XSSFWorkbook(fis);

            XSSFSheet sheet = xssfWorkbook.getSheetAt(0);

            Iterator<Row> rowIterator = sheet.rowIterator();

            while (rowIterator.hasNext()){
                Row row = rowIterator.next();
                if(row.getRowNum() < 1){
                    continue;
                }

                Map<String, Object> rowMap = new HashMap<>();

                Cell semesterCell = row.getCell(0);
                semesterCell.setCellType(CellType.STRING);
                rowMap.put("user_name", semesterCell.getStringCellValue());

                rowMap.put("course_name", row.getCell(3).getStringCellValue());

                rowMap.put("term", row.getCell(4).getStringCellValue());

                rowMap.put("score", row.getCell(5).getStringCellValue());

                result.add(rowMap);
            }
        }catch (Exception e){
            e.printStackTrace();
        }

        return result;
    }
}
