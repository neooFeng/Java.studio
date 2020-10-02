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

        String fileName = "/Users/teacher/Documents/gz_live_2020-03-22.xlsx";

        countLiveTeacherCount(symbols, fileName);
    }

    private static void countLiveTeacherCount(String[] symbolList, String fileName) {
        int teacherCount = 0;
        int lessonCount = 0;
        int liveStudentCount = 0;
        int allStudentCount = 0;
        int totalViewTimes = 0;
        int totalViewDuration = 0;


        XSSFWorkbook xssfWorkbook = new XSSFWorkbook();
        XSSFSheet sheet = xssfWorkbook.createSheet();
        int rowIndex = 1;
        for (String symbol : symbolList) {
            try{
                Integer schoolId = DBUtil.getGlobalTemplate().queryForObject("select id from school where symbol = ?", Integer.class, symbol);

                JdbcTemplate template = DBUtil.getSchoolTemplate(schoolId);

                /*List<Map<String, Object>> result = template.queryForList(
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
                }*/

                Integer tempTeacherCount = template.queryForObject("select count(distinct lc.teacher_id) from live_lesson ls \n" +
                        "inner join live_class lc on ls.live_class_id = lc.id\n" +
                        "inner join liveclass_class lcc on lc.id = lcc.live_class_id \n" +
                        "inner join class c on lcc.class_id = c.id\n" +
                        "where ls.status != 0 and c.name not like '%督导%';", Integer.class);
                teacherCount += tempTeacherCount==null ? 0 : tempTeacherCount;

                Integer tempLessonCount = template.queryForObject("select count(distinct ls.id) from live_lesson ls \n" +
                        "inner join live_class lc on ls.live_class_id = lc.id\n" +
                        "inner join liveclass_class lcc on lc.id = lcc.live_class_id \n" +
                        "inner join class c on lcc.class_id = c.id\n" +
                        "where ls.status != 0 and c.name not like '%督导%';", Integer.class);
                lessonCount += tempLessonCount==null ? 0 : tempLessonCount;

                Integer templiveStudentCount = template.queryForObject("select count(distinct student_id) from course_study_record where type = 4;", Integer.class);
                liveStudentCount += templiveStudentCount==null ? 0 : templiveStudentCount;

                Integer tempallStudentCount = template.queryForObject("select count(distinct student_id) from course_study_record where type in (3,4);", Integer.class);
                allStudentCount += tempallStudentCount==null ? 0 : tempallStudentCount;

                Integer temptotalViewTimes = template.queryForObject("select count(*) from course_study_record where type in (3,4);", Integer.class);
                totalViewTimes += temptotalViewTimes==null ? 0 : temptotalViewTimes;

                Float tmptotalViewDuration = template.queryForObject("SELECT SUM(TIME_TO_SEC(timediff(csr.end_time, csr.start_time))) / 3600  FROM course_study_record csr\n" +
                        "where csr.type in (3,4);", Float.class);
                totalViewDuration += tmptotalViewDuration==null ? 0 : tmptotalViewDuration;

            }catch (Exception e){
                e.printStackTrace();
                System.out.println(symbol);
            }
        }

       /* try (FileOutputStream fileOut = new FileOutputStream(fileName)){
            xssfWorkbook.write(fileOut);
        }catch (Exception e) {
            e.printStackTrace();
        }*/

        System.out.println("teacherCount: " + teacherCount);
        System.out.println("lessonCount: " + lessonCount);
        System.out.println("liveStudentCount: " + liveStudentCount);
        System.out.println("allStudentCount: " + allStudentCount);
        System.out.println("totalViewTimes: " + totalViewTimes);
        System.out.println("totalViewDuration: " + totalViewDuration);
    }
}
