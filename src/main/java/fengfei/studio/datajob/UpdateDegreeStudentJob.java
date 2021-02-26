package fengfei.studio.datajob;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
import java.io.File;
import java.io.FileInputStream;
import java.util.*;

public class UpdateDegreeStudentJob {



    public static void main(String[] args) {

        Map<Integer, String> schoolFileMap = new HashMap<>();
        schoolFileMap.put(4386, "C:\\Users\\fengfei\\Desktop\\学生学号更换及对应学籍照片编号.xlsx");

        for (Map.Entry<Integer, String> entry : schoolFileMap.entrySet()){
            process(entry.getKey(), entry.getValue());
        }
    }

    private static void process(int schoolId, String filePath) {
        List<Student> targetStudents = parseExcel(filePath);

    /*    StringBuilder stringBuilder = new StringBuilder();
        for (Student student : targetStudents){
            stringBuilder.append("").append(student.getGlobalUserName()).append("").append(",");
        }*/

      /*

       Map<Integer, String> centerIdToNameMap = new HashMap<>();
        Map<String, Integer> centerNameToIdMap = new HashMap<>();
        Map<Integer, String> majorIdToNameMap = new HashMap<>();
        Map<String, Integer> majorNameToIdMap = new HashMap<>();


        setSchoolCenters(schoolId, centerIdToNameMap, centerNameToIdMap);
        setSchoolMajors(schoolId, majorIdToNameMap, majorNameToIdMap);

        for (Student student : targetStudents){
            Integer centerId = centerNameToIdMap.get(student.getCenterName());
            Integer majorId = majorNameToIdMap.get(student.getMajorName() + "_" + student.getMajorLevel());

            if (centerId == null || majorId == null){
                System.out.println(student.getCenterName());
                System.out.println(student.getMajorName() + "_" + student.getMajorLevel());
            }

            student.setCenterId(centerId);
            student.setMajorId(majorId);
        }*/

        //updateGlobalUser(targetStudents);

        updateStudent(schoolId, targetStudents);
    }

    private static void updateGlobalUser(List<Student> studentList) {
        List<Object[]> args = new ArrayList<>();
        for (Student student: studentList){
            args.add(new Object[]{student.getDisplayName(), student.getGlobalUserName()});
        }

        JdbcTemplate schoolTemplate = DBUtil.getGlobalTemplate();
        schoolTemplate.batchUpdate("update `global_user` set `display_name` = ? where `user_name` = ?;", args);
    }

    private static List<Student> getStudent(int schoolId) {
        List<Student> students = new ArrayList<>();

        JdbcTemplate schoolTemplate = DBUtil.getSchoolTemplate(schoolId);
        List<Map<String, Object>> studentMapList = schoolTemplate.queryForList("SELECT id, `student_number`, `global_user_name` FROM `student`;");
        for (Map<String, Object> studentMap : studentMapList) {
            Student student = new Student();
            student.setId(Integer.valueOf(studentMap.get("id").toString()));
            student.setStudentNumber(studentMap.get("student_number").toString());
            student.setGlobalUserName(studentMap.get("global_user_name").toString());

            students.add(student);
        }

        return students;
    }

    private static void updateStudent(int schoolId, List<Student> studentList) {
        List<Object[]> args = new ArrayList<>();
        for (Student student: studentList){
            args.add(new Object[]{student.getStudentNumber(), student.getCandidateNumber()});
        }

        JdbcTemplate schoolTemplate = DBUtil.getSchoolTemplate(schoolId);
        schoolTemplate.batchUpdate("update `student` set `student_number` = ? where `candidate_number` = ?", args);
    }

    private static void setSchoolCenters(int schoolId, Map<Integer, String> centerIdToNameMap, Map<String, Integer> centerNameToIdMap) {
        JdbcTemplate schoolTemplate = DBUtil.getSchoolTemplate(schoolId);
        List<Map<String, Object>> centerList = schoolTemplate.queryForList("SELECT id, `name` FROM `center`;");
        for (Map<String, Object> centerMap : centerList) {
            int centerId = Integer.valueOf(centerMap.get("id").toString());
            String name = centerMap.get("name").toString();
            centerIdToNameMap.put(centerId, name);
            centerNameToIdMap.put(name, centerId);
        }
    }

    private static void setSchoolMajors(int schoolId, Map<Integer, String> majorIdToNameMap, Map<String, Integer> majorNameToIdMap) {
        JdbcTemplate schoolTemplate = DBUtil.getSchoolTemplate(schoolId);
        List<Map<String, Object>> studentMapList = schoolTemplate.queryForList("SELECT id, `name`, `level` FROM major;");
        for (Map<String, Object> studentMap : studentMapList) {
            int majorId = Integer.valueOf(studentMap.get("id").toString());
            String nameLevel = studentMap.get("name") + "_" + studentMap.get("level");
            majorIdToNameMap.put(majorId, nameLevel);
            majorNameToIdMap.put(nameLevel, majorId);
        }
    }

    private static List<Student> parseExcel(String filePath) {
        List<Student> students = new ArrayList<>();

        try {
            FileInputStream fis = new FileInputStream(new File(filePath));

            XSSFWorkbook xssfWorkbook = new XSSFWorkbook(fis);

            XSSFSheet sheet = xssfWorkbook.getSheetAt(0);
            Iterator<Row> rowIterator = sheet.rowIterator();

            while (rowIterator.hasNext()) {
                Row row = rowIterator.next();
                if (row.getRowNum() < 1) {
                    continue;
                }

                Student student = new Student();

                Cell cell1 = row.getCell(0);
                cell1.setCellType(CellType.STRING);
                student.setStudentNumber(cell1.getStringCellValue());

                Cell cell2 = row.getCell(1);
                cell2.setCellType(CellType.STRING);
                student.setCandidateNumber(cell2.getStringCellValue());

                students.add(student);
            }
        }catch (Exception e){
            e.printStackTrace();
        }

        return students;
    }

    private static class Student {
        private int id;
        private String globalUserName;
        private String studentNumber;
        private String displayName;
        private String identityCard;
        private String candidateNumber;
        private int centerId;
        private String centerName;
        private int majorId;
        private String majorName;
        private String majorLevel;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getGlobalUserName() {
            return globalUserName;
        }

        public void setGlobalUserName(String globalUserName) {
            this.globalUserName = globalUserName;
        }

        public String getDisplayName() {
            return displayName;
        }

        public void setDisplayName(String displayName) {
            this.displayName = displayName;
        }

        public String getIdentityCard() {
            return identityCard;
        }

        public void setIdentityCard(String identityCard) {
            this.identityCard = identityCard;
        }

        public String getCandidateNumber() {
            return candidateNumber;
        }

        public void setCandidateNumber(String candidateNumber) {
            this.candidateNumber = candidateNumber;
        }

        public int getCenterId() {
            return centerId;
        }

        public void setCenterId(int centerId) {
            this.centerId = centerId;
        }

        public int getMajorId() {
            return majorId;
        }

        public void setMajorId(int majorId) {
            this.majorId = majorId;
        }

        public String getStudentNumber() {
            return studentNumber;
        }

        public void setStudentNumber(String studentNumber) {
            this.studentNumber = studentNumber;
        }

        public String getCenterName() {
            return centerName;
        }

        public void setCenterName(String centerName) {
            this.centerName = centerName;
        }

        public String getMajorName() {
            return majorName;
        }

        public void setMajorName(String majorName) {
            this.majorName = majorName;
        }

        public String getMajorLevel() {
            return majorLevel;
        }

        public void setMajorLevel(String majorLevel) {
            this.majorLevel = majorLevel;
        }
    }
}
