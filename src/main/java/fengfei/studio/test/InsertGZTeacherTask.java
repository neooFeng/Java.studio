package fengfei.studio.test;

import org.apache.commons.collections.CollectionUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import java.io.File;
import java.io.FileInputStream;
import java.lang.reflect.Array;
import java.util.*;

public class InsertGZTeacherTask {
    public static void main(String[] args) {
        String filePath = "/Users/teacher/Downloads/江苏二师范老师信息（给封飞）1.xlsx";

        Map<String, Integer> schoolMap = new HashMap<>();
        schoolMap.put("城市与资源环境学院", 4104);
        schoolMap.put("教育科学学院", 4105);
        schoolMap.put("经济与法政学院", 4106);
        schoolMap.put("马克思主义学院", 4107);
        schoolMap.put("美术学院", 4108);
        schoolMap.put("生命科学与化学化工学院", 4109);
        schoolMap.put("数学与信息技术学院", 4110);
        schoolMap.put("体育学院", 4111);
        schoolMap.put("外国语学院", 4112);
        schoolMap.put("文学院", 4113);
        schoolMap.put("物理与电子工程学院", 4114);
        schoolMap.put("现代传媒学院", 4115);
        schoolMap.put("学前教育学院", 4116);
        schoolMap.put("音乐学院", 4117);

        schoolMap.put("测试学院", 4118);


        Collection<Teacher> teachers = getFromExcel(filePath);
        //Collection<Teacher> teachers = genTeachersToInsert(schoolMap);
        for(Teacher teacher : teachers){
            /*Integer userId = getUserId(teacher.getUserName());
            if (userId != null){
                List<Integer> existsSchools = getUserSchools(userId);
                if (CollectionUtils.isNotEmpty(existsSchools) && existsSchools.size() == teacher.getSchools().size()){
                    System.out.println(teacher.getUserName() + " already exists. pass.");
                }else{
                    System.out.println("TODO: add exists teacher into schools.");
                    System.out.println(teacher.getUserName());
                }
            }else{
                userId = insertGlobalUser(teacher);
            }*/

            Integer userId;
            if (teacher.getUserName().equals("jssnu12201916")
                || teacher.getUserName().equals("jssnu12201537")
                || teacher.getUserName().equals("jssnu11199605")
                || teacher.getUserName().equals("jssnu11201518") ){
                userId = getUserId(teacher.getUserName());
            }else{
                userId = insertGlobalUser(teacher);
            }

            if (userId == null){
                System.out.println("insert global user failed. " + teacher.getUserName());
                continue;
            }

            List<Integer> schoolIds = new ArrayList<>();
            for (String schoolName : teacher.getSchools()){
                schoolIds.add(schoolMap.get(schoolName));
            }

            List<Integer> teacherModules = new ArrayList<>();
            teacherModules.add(200);
            teacherModules.add(208);
            teacherModules.add(240);
            teacherModules.add(205);
            teacherModules.add(207);

            teacherModules.add(503);
            teacherModules.add(531);
            teacherModules.add(223);
            teacherModules.add(504);
            teacherModules.add(533);

            for (Integer schoolId : schoolIds){
                System.out.println("insert school: " + schoolId + ", userId: " + userId);
                insertUserSchool(userId, schoolId, 2);

                //List<Integer> teacherModules = getTeacherModules(schoolId);
                insertTeacherToSchool(schoolId, userId, teacherModules);
            }
        }
    }

    private static Integer insertGlobalUser(Teacher teacher) {
        String insertSql = "INSERT INTO `global`.`global_user`\n" +
                "(`user_name`,\n" +
                "`display_name`,\n" +
                "`password`,\n" +
                "`gender`,\n" +
                "`photo_image_id`,\n" +
                "`user_type`,\n" +
                "`phone_valid`)\n" +
                "VALUES\n" +
                "(?, ?, ?, '男', 0, 0, 0);\n";

        System.out.println("insert global USER: " + teacher.getUserName());

        DBUtil.getGlobalTemplate().update(insertSql, teacher.getUserName(), teacher.getDisplayName(), teacher.getPassword());

        return getUserId(teacher.getUserName());
    }

    private static void insertTeacherToSchool(Integer schoolId, Integer userId, List<Integer> teacherModules) {

        String queryUserSql = "SELECT `global_user`.`id`,\n" +
                "    `global_user`.`user_name`,\n" +
                "    `global_user`.`display_name`,\n" +
                "    `global_user`.`gender`,\n" +
                "    `global_user`.`phone_num`,\n" +
                "    `global_user`.`address`,\n" +
                "    `global_user`.`identity_card`,\n" +
                "    `global_user`.`photo_image_id`,\n" +
                "    `global_user`.`photo_image_url`,\n" +
                "    `global_user`.`email`\n" +
                "FROM `global`.`global_user` WHERE id = ?";

        Map<String, Object> userResultMap = DBUtil.getGlobalTemplate().queryForMap(queryUserSql, userId);

        JdbcTemplate schoolTemplate = DBUtil.getSchoolTemplate(schoolId);

        String insertTeacherSql = "INSERT INTO `teacher`\n" +
                "(`global_user_id`,\n" +
                "`global_user_name`,\n" +
                "`display_name`,\n" +
                "`gender`,\n" +
                "`phone_num`,\n" +
                "`address`,\n" +
                "`identity_card`,\n" +
                "`photo_image_id`,\n" +
                "`photo_image_url`,\n" +
                "`email`)\n" +
                "VALUES\n" +
                "(?,?,?,?,?,?,?,?,?,?);\n";

        schoolTemplate.update(insertTeacherSql,
                userResultMap.get("id"),
                userResultMap.get("user_name"),
                userResultMap.get("display_name"),
                userResultMap.get("gender"),
                userResultMap.get("phone_num"),
                userResultMap.get("address"),
                userResultMap.get("identity_card"),
                userResultMap.get("photo_image_id"),
                userResultMap.get("photo_image_url"),
                userResultMap.get("email"));

        Integer teacherId = schoolTemplate.queryForObject("select id FROM teacher where global_user_id = ?", Integer.class, userId);
        if (teacherId == null){
            throw new RuntimeException("insert teacher failed.");
        }

        List<Object[]> args = new ArrayList<>();
        for (Integer moduleId: teacherModules){
            args.add(new Object[]{teacherId, moduleId});
        }
        schoolTemplate.batchUpdate("insert into teacher_module(`teacher_id`, `module_id`) values(?, ?)", args);
    }

    private static List<Integer> getTeacherModules(Integer schoolId) {
        return DBUtil.getNpdbTemplate().queryForList("select module_id from school_module where school_id = ? and role_id = 2 " +
                "AND (module_id = ? OR parent_module_id = ? OR module_id = ? OR parent_module_id = ? )",
                Integer.class, schoolId, 200, 200, 503, 503);
    }

    private static void insertUserSchool(Integer userId, Integer schoolId, int userRole) {
        int result = DBUtil.getGlobalTemplate().update("insert into user_school(`user_id`, `school_id`, `user_role`) values(?, ?, ?)", userId, schoolId, userRole);

        if (result != 1){
            throw new RuntimeException("insertUserSchool failed." );
        }
    }

    private static Map<String, Integer> schooMap = new HashMap<>();
    private static Integer getSchoolId(String schoolName) {
        if (schooMap.containsKey(schoolName)){
            return schooMap.get(schoolName);
        }

        Integer schoolId =  DBUtil.getGlobalTemplate().queryForObject("select id FROM school where name = ?", Integer.class, schoolName);
        schooMap.put(schoolName, schoolId);
        return schoolId;
    }

    private static List<Integer> getUserSchools(int userId) {
        return DBUtil.getGlobalTemplate().queryForList("select school_id from user_school where user_id = ?", Integer.class, userId);
    }

    private static Integer getUserId(String userName) {
        Integer id = null;
        try {
            id = DBUtil.getGlobalTemplate().queryForObject("select id from global_user where user_name = ?", Integer.class, userName);
        }catch (EmptyResultDataAccessException e){
            // ignore
        }catch (Exception e){
            e.printStackTrace();
        }

        return id;
    }

    private static Collection<Teacher> getFromExcel(String filePath) {
        Map<String, Teacher> teacherMap = new HashMap<>();

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

                Teacher teacher = new Teacher();
                teacher.setDisplayName(row.getCell(3).getStringCellValue());
                teacher.setUserName(row.getCell(0).getStringCellValue());
                row.getCell(1).setCellType(CellType.STRING);
                teacher.setPassword(row.getCell(1).getStringCellValue());


                row.getCell(2).setCellType(CellType.STRING);
                Set<String> schools = new HashSet<>();
                schools.add(row.getCell(2).getStringCellValue());
                schools.add("测试学院");
                teacher.setSchools(schools);

                if (teacherMap.containsKey(teacher.getUserName())){
                    teacherMap.get(teacher.getUserName()).getSchools().addAll(teacher.getSchools());
                }else {
                    teacherMap.put(teacher.getUserName(), teacher);
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }

        return teacherMap.values();
    }

    private static Collection<Teacher> genTeachersToInsert(Map<String, Integer> schoolMap) {
        List<Teacher> result = new ArrayList<>();

        for (int i=1; i<7; i++){
            Teacher teacher = new Teacher();
            teacher.setUserName("ahjz00" + i);
            teacher.setPassword("123456");
            teacher.setDisplayName("管理员" + i);
            teacher.setSchools(schoolMap.keySet());

            result.add(teacher);
        }
        return result;

    }

    static class Teacher{
        private String displayName;
        private String userName;
        private String password;
        private Set<String> schools;

        public String getDisplayName() {
            return displayName;
        }

        public void setDisplayName(String displayName) {
            this.displayName = displayName;
        }

        public String getUserName() {
            return userName;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public Set<String> getSchools() {
            return schools;
        }

        public void setSchools(Set<String> schools) {
            this.schools = schools;
        }
    }

}
