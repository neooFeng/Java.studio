package fengfei.studio.test;

import org.apache.commons.collections.CollectionUtils;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.jdbc.core.JdbcTemplate;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.*;

public class Tmp {
    public static void main(String[] args) {
        String[] symbols = new String[]{"jssnua", "jssnub", "jssnuc", "jssnud", "jssnue", "jssnuf", "jssnug", "jssnuh", "jssnui", "jssnuj", "jssnuk",
                "jssnul", "jssnum", "jssnun", "jssnuo"};

        String fileName = "/Users/teacher/Documents/gz_live_2020-02-25.xlsx";

        countLiveTeacherCount(symbols, fileName);
    }

    private static void countLiveTeacherCount(String[] symbolList, String fileName) {
        int studentCount = 0;
        int studentStudySecs = 0;

        XSSFWorkbook xssfWorkbook = new XSSFWorkbook();
        XSSFSheet sheet = xssfWorkbook.createSheet();
        int rowIndex = 1;
        for (String symbol : symbolList) {
            try{
                Integer schoolId = DBUtil.getGlobalTemplate().queryForObject("select id from school where symbol = ?", Integer.class, symbol);

                JdbcTemplate template = DBUtil.getSchoolTemplate(schoolId);

                List<Map<String, Object>> result = template.queryForList(
                        "SELECT s.year, m.name as major, c.name as className, lc.name as liveClass, t.display_name as teacher, ll.name as lesson, ll.begin_time as beginTime, ll.status FROM live_class lc\n" +
                        "inner join liveclass_class lcc on lc.id = lcc.live_class_id\n" +
                        "inner join class c on c.id = lcc.class_id\n" +
                        "inner join major m on c.major_id = m.id\n" +
                        "inner join semester s on c.semester_id = s.id\n" +
                        "inner join live_lesson ll on lc.id = ll.live_class_id\n" +
                        "inner join teacher t on lc.teacher_id = t.id\n" +
                        "order by s.year, m.id, c.id, lc.id, ll.id;");

                if (CollectionUtils.isNotEmpty(result)){
                    rowIndex++;
                    XSSFRow row = sheet.createRow(rowIndex++);
                    row.createCell(0).setCellValue(symbol);

                    row = sheet.createRow(rowIndex++);
                    int column = 0;
                    for (String key : result.get(0).keySet()){
                        row.createCell(column++).setCellValue(key);
                    }

                    for (Map<String, Object> dbRow : result){
                        row = sheet.createRow(rowIndex++);

                        column = 0;
                        for (String key : dbRow.keySet()){
                            row.createCell(column++).setCellValue(String.valueOf(dbRow.get(key)));
                        }
                    }
                }

                studentCount += template.queryForObject("SELECT count(distinct student_id) FROM course_study_record csr\n" +
                        "where csr.type = 3 and csr.start_time > '2020-02-25';", Integer.class);

                Integer tempDuration = template.queryForObject("SELECT SUM(TIME_TO_SEC(timediff(csr.end_time, csr.start_time))) FROM course_study_record csr\n" +
                        "where csr.type = 3 and csr.start_time > '2020-02-25';", Integer.class);

                studentStudySecs += tempDuration==null ? 0 : tempDuration;

            }catch (Exception e){
                e.printStackTrace();
                System.out.println(symbol);
            }
        }

        try (FileOutputStream fileOut = new FileOutputStream(fileName)){
            xssfWorkbook.write(fileOut);
        }catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println("学生: " + studentCount);
        System.out.println("学生观看: " + studentStudySecs + ", " + studentStudySecs / 3600  + ":" + (studentStudySecs % 3600) / 60 + ":" + (studentStudySecs%60));
    }
}
