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

    private final static Map<Integer, String> schoolMajorMap = new HashMap<>();
    private final static Map<String, Integer> politicsMap = new HashMap<>();
    private final static Map<String, Integer> nationnalityMap = new HashMap<>();
    static {
        schoolMajorMap.put(8, "电子商务（社）");
        schoolMajorMap.put(9, "机电一体化技术（社）");
        schoolMajorMap.put(10, "计算机网络技术（社）");
        schoolMajorMap.put(11, "建设工程管理（社）");
        schoolMajorMap.put(12, "汽车检测与维修技术（社）");
        schoolMajorMap.put(13, "软件技术（社）");
        schoolMajorMap.put(14, "电子信息工程技术（社）");

        politicsMap.put("中共党员", 1);
        politicsMap.put("中共预备党员", 2);
        politicsMap.put("共青团员", 3);
        politicsMap.put("群众", 13);

        nationnalityMap.put("汉族", 1);
        nationnalityMap.put("土家族", 15);
    }

    public static void main(String[] args) {
        String filePath = "C:\\Users\\fengfei\\Downloads\\wczy.xlsx";

        List<Student> studentList = parseExcel(filePath);

        Map<String, Integer> studentMap = getFromSchool();

        addGlobalUserId(studentList, studentMap);

        updateGlobalUser(studentList);

        updateSchoolUser(studentList);
    }

    private static void updateSchoolUser(List<Student> studentList) {
        List<Object[]> args = new ArrayList<>();
        for (Student student: studentList){
            args.add(new Object[]{student.getGender(), student.getIdentityCard(),
                    nationnalityMap.get(student.getNationnality()), politicsMap.get(student.getPolitics()), student.getBirthday(), student.getGlobalUserId()});
        }

        JdbcTemplate schoolTemplate = DBUtil.getSchoolTemplate(4027);
        schoolTemplate.batchUpdate("update student set `gender` = ?, identity_card = ?, nationality_id = ?, politics_status_id = ?, birthday = ? where global_user_id = ?;", args);
    }

    private static void updateGlobalUser(List<Student> studentList) {
        List<Object[]> args = new ArrayList<>();
        for (Student student: studentList){
            args.add(new Object[]{student.getGender(), student.getIdentityCard(),
                    nationnalityMap.get(student.getNationnality()), politicsMap.get(student.getPolitics()), student.getGlobalUserId()});
        }
        DBUtil.getGlobalTemplate().batchUpdate("update global_user set `gender` = ?, identity_card = ?, " +
                "nationality_id = ?, politics_status_id = ? where id = ?;", args);
    }

    private static void addGlobalUserId(List<Student> studentList, Map<String, Integer> studentMap) {
        for (Student student : studentList){
            student.setGlobalUserId(studentMap.get(student.getCandidateNumber()));
        }
    }

    private static Map<String, Integer> getFromSchool() {
        Map<String, Integer> resultMap = new HashMap<>();

        JdbcTemplate schoolTemplate = DBUtil.getSchoolTemplate(4027);
        List<Map<String, Object>> studentMapList = schoolTemplate.queryForList("SELECT candidate_number, global_user_id FROM student;");
        for (Map<String, Object> studentMap : studentMapList) {
            resultMap.put(studentMap.get("candidate_number").toString(), Integer.valueOf(studentMap.get("global_user_id").toString()));
        }

        return resultMap;
    }

    private static List<Student> parseExcel(String filePath) {
        List<Student> students = new ArrayList<>();

        try {
            FileInputStream fis = new FileInputStream(new File(filePath));

            XSSFWorkbook xssfWorkbook = new XSSFWorkbook(fis);

            XSSFSheet sheet = xssfWorkbook.getSheetAt(1);
            Iterator<Row> rowIterator = sheet.rowIterator();

            while (rowIterator.hasNext()){
                Row row = rowIterator.next();
                if(row.getRowNum() < 1){
                    continue;
                }

                Student student = new Student();

                Cell cell0 = row.getCell(0);
                cell0.setCellType(CellType.STRING);
                student.setCandidateNumber(cell0.getStringCellValue());

                student.setGender(row.getCell(3).getStringCellValue());

                Cell cell4 = row.getCell(4);
                cell4.setCellType(CellType.STRING);
                String dateStr = cell4.getStringCellValue();

                int year = Integer.parseInt(dateStr.substring(0, 4));
                int month = Integer.parseInt(dateStr.substring(5,6));
                int day = Integer.parseInt(dateStr.substring(7,8));
                Calendar calendar = Calendar.getInstance(TimeZone.getDefault());
                calendar.set(year, month, day);
                student.setBirthday(calendar.getTime());

                Cell cell5 = row.getCell(5);
                cell5.setCellType(CellType.STRING);
                student.setIdentityCard(cell5.getStringCellValue());

                student.setPolitics(row.getCell(6).getStringCellValue());
                student.setNationnality(row.getCell(7).getStringCellValue());

                students.add(student);
            }
        }catch (Exception e){
            e.printStackTrace();
        }

        return students;
    }

    private static class Student {
        private String candidateNumber;
        private int globalUserId;
        private String identityCard;
        private Date birthday;
        private String gender;
        private String politics;
        private String nationnality;

        public String getCandidateNumber() {
            return candidateNumber;
        }

        public void setCandidateNumber(String candidateNumber) {
            this.candidateNumber = candidateNumber;
        }

        public int getGlobalUserId() {
            return globalUserId;
        }

        public void setGlobalUserId(int globalUserId) {
            this.globalUserId = globalUserId;
        }

        public String getIdentityCard() {
            return identityCard;
        }

        public void setIdentityCard(String identityCard) {
            this.identityCard = identityCard;
        }

        public Date getBirthday() {
            return birthday;
        }

        public void setBirthday(Date birthday) {
            this.birthday = birthday;
        }

        public String getGender() {
            return gender;
        }

        public void setGender(String gender) {
            this.gender = gender;
        }

        public String getPolitics() {
            return politics;
        }

        public void setPolitics(String politics) {
            this.politics = politics;
        }

        public String getNationnality() {
            return nationnality;
        }

        public void setNationnality(String nationnality) {
            this.nationnality = nationnality;
        }
    }
}
