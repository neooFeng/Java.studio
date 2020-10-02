package fengfei.studio.datajob;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;
import java.util.Map;

public class GetWrongExamScoreTask {

    public static void main(String[] args) {
        JdbcTemplate globalDB = DBUtil.getGlobalTemplate();

        List<Map<String, Object>> degreeSchoolMap = globalDB.queryForList("SELECT s.id, s.name, s.symbol FROM global.school s\n" +
                "INNER JOIN global.school_category sc ON s.category_id = sc.id\n" +
                "WHERE sc.type = 1 AND s.status = 1");


        for (Map<String, Object> schoolMap : degreeSchoolMap){
            JdbcTemplate schoolTemplate = DBUtil.getSchoolTemplate(Integer.valueOf(schoolMap.get("id").toString()));

            Integer num = schoolTemplate.queryForObject("select count(*) from student_exam se\n" +
                    "inner join exam e on se.exam_id = e.id and e.is_final_exam = 1\n" +
                    "inner join student_exam_score ses on se.student_id = ses.student_id and ses.teachingplan_course_id = e.teachingplan_course_id where se.submit_time > '2020-03-01';", Integer.class, null);

            if (num != null && num > 0){
                System.out.print(schoolMap.get("symbol") + ", exam count: " + num);

                List<Map<String, Object>> wrongExamScoreMap = schoolTemplate.queryForList("select se.score, ses.exam_score from student_exam se\n" +
                        "inner join exam e on se.exam_id = e.id and e.is_final_exam = 1\n" +
                        "inner join student_exam_score ses on se.student_id = ses.student_id and ses.teachingplan_course_id = e.teachingplan_course_id\n" +
                        "where se.submit_time > '2020-03-01'\n" +
                        "group by e.teachingplan_course_id, se.student_id\n" +
                        "having max(se.score) != ses.exam_score and max(se.score) > ses.exam_score\n;");

                if (CollectionUtils.isNotEmpty(wrongExamScoreMap)) {
                    System.out.print(", wrong count: " + wrongExamScoreMap.size());
                }

                System.out.println();
            }
        }
    }
}
