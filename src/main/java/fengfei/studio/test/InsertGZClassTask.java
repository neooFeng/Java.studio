package fengfei.studio.test;

import com.mysql.cj.x.protobuf.MysqlxExpr;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.jdbc.core.JdbcTemplate;

import java.io.File;
import java.io.FileInputStream;
import java.util.*;

public class InsertGZClassTask {

    public static void main(String[] args) {
        String filePath = "/Users/teacher/Documents/需要添加的班级1.xlsx";

        Map<String, Integer> schoolMap = new HashMap<>();
        schoolMap.put("基础部", 4076);
        schoolMap.put("电子信息系", 4071);
        schoolMap.put("经贸旅游系", 4072);
        schoolMap.put("汽车技术系", 4073);
        schoolMap.put("艺术教育系", 4075);
        schoolMap.put("智能制造系", 4074);

        Map<String, List<GZClass>> classesToInsert = getFromExcel(filePath);

        for(String schoolName : classesToInsert.keySet()){
            System.out.println(schoolName);
            int schoolId = schoolMap.get(schoolName);

            List<GZClass> classes = classesToInsert.get(schoolName);

//            Set<String> majors = new HashSet<>();
//            for (GZClass gzClass : classes){
//                majors.add(gzClass.getMajorName());
//            }
//
//            insertMajors(schoolId, majors);


            Map<String, Integer> majorMap = getSchoolMajors(schoolId);
            Map<String, Integer> semesterMap = getSchoolSemesters(schoolId);

            for (GZClass gzClass : classes){
                Integer majorId = majorMap.get(gzClass.getMajorName());
                if (majorId == null){
                    System.out.println(gzClass.getMajorName());
                }
                gzClass.setMajorId(majorId);

                Integer semesterId = semesterMap.get(gzClass.getSemesterNaem());
                if (semesterId == null){
                    System.out.println(gzClass.getSemesterNaem());
                }
                gzClass.setSemesterId(semesterId);
            }

            insertClasses(schoolId, classes);
        }
    }

    private static void insertMajors(int schoolId, Set<String> majors) {
        String insertSql = "INSERT INTO `major`\n" +
                "(`name`,\n" +
                "`level`,\n" +
                "`level_sequence`,\n" +
                "`cover_image_id`,\n" +
                "`show_in_home_page`,\n" +
                "`global_major_id`)\n" +
                "VALUES\n" +
                "(?, '专科', 0, 0, 0, 0);\n";

        List<Object[]> batchArgs = new ArrayList<>();
        for (String majorName: majors){
            batchArgs.add(new Object[]{majorName});
        }
        DBUtil.getSchoolTemplate(schoolId).batchUpdate(insertSql, batchArgs);
    }

    private static void insertClasses(int schoolId, List<GZClass> classes) {
        String insertSql = "INSERT INTO `class`\n" +
                "(`name`,\n" +
                "`major_id`,\n" +
                "`semester_id`)\n" +
                "VALUES\n" +
                "(?,?,?);\n";

        List<Object[]> batchArgs = new ArrayList<>();
        for (GZClass gzClass: classes){
            batchArgs.add(new Object[]{gzClass.getName(), gzClass.getMajorId(), gzClass.getSemesterId()});
        }
        DBUtil.getSchoolTemplate(schoolId).batchUpdate(insertSql, batchArgs);
    }

    private static Map<String, Integer> getSchoolSemesters(int schoolId) {
        List<Map<String, Object>> map = DBUtil.getSchoolTemplate(schoolId).queryForList("select id, `year`, `term` from semester");

        Map<String, Integer> result = new HashMap<>();
        for(Map<String, Object> row : map){
            result.put(row.get("year") + "0" + row.get("term"), Integer.valueOf(row.get("id").toString()));
        }
        return result;
    }

    private static Map<String, Integer> getSchoolMajors(int schoolId) {
        List<Map<String, Object>> map = DBUtil.getSchoolTemplate(schoolId).queryForList("select id, `name` from major");

        Map<String, Integer> result = new HashMap<>();
        for(Map<String, Object> row : map){
            result.put(row.get("name").toString(), Integer.valueOf(row.get("id").toString()));
        }
        return result;
    }

    private static Map<String, List<GZClass>> getFromExcel(String filePath) {
        Map<String, List<GZClass>> result = new HashMap<>();

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

                GZClass gzClass = new GZClass();

                Cell semesterCell = row.getCell(0);
                semesterCell.setCellType(CellType.STRING);
                gzClass.setSemesterNaem(semesterCell.getStringCellValue());

                gzClass.setMajorName(row.getCell(1).getStringCellValue());
                gzClass.setName(row.getCell(2).getStringCellValue());

                result.putIfAbsent(row.getCell(3).getStringCellValue(), new ArrayList<>());
                result.get(row.getCell(3).getStringCellValue()).add(gzClass);
            }
        }catch (Exception e){
            e.printStackTrace();
        }

        return result;
    }


    static class GZClass{
        private int majorId;
        private String majorName;
        private int semesterId;
        private String semesterNaem;
        private String name;

        public String getMajorName() {
            return majorName;
        }

        public void setMajorName(String majorName) {
            this.majorName = majorName;
        }

        public String getSemesterNaem() {
            return semesterNaem;
        }

        public void setSemesterNaem(String semesterNaem) {
            this.semesterNaem = semesterNaem;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getMajorId() {
            return majorId;
        }

        public void setMajorId(int majorId) {
            this.majorId = majorId;
        }

        public int getSemesterId() {
            return semesterId;
        }

        public void setSemesterId(int semesterId) {
            this.semesterId = semesterId;
        }
    }
}
