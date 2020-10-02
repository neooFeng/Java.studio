package fengfei.studio.test;

import org.springframework.jdbc.core.JdbcTemplate;

public class MysqlPerformanceTest {
    public static void main(String[] args) {
        JdbcTemplate jdbcTemplate = getTmpTemplate();

        int i = 0;
        long start = System.currentTimeMillis();
        while(i++<10000){
            jdbcTemplate.batchUpdate("INSERT INTO `operate_temp` (`group_id`,`result`,`status`,`comment`,`created_time`,`updated_time`,`c0`,`c1`,`c2`,`c3`,`c4`,`c5`,`c6`,`c7`,`c8`,`c9`,`c10`,`c11`,`c12`,`c13`,`c14`,`c15`,`c16`,`c17`,`c18`,`c19`) \n" +
                    "VALUES (50175,NULL,'454',NULL,'2018-10-24 12:26:10','2018-10-24 12:51:10','50113','550','411','冲刺一教育基础(上)','https://u.qsxt.io/teacher_certification/1030/小学教育知识与能力_冲刺一教育基础(上).mp4','1747','https://u.qsxt.io/teacher_certification/1030/小学教育知识与能力_冲刺一教育基础(上).jpg','https://u.qsxt.io/teacher_certification/1030/小学教育知识与能力_冲刺一教育基础(上).pdf',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL);");
        }
        System.out.print(System.currentTimeMillis() - start);

    }

    public static JdbcTemplate getTmpTemplate(){
        String url = "jdbc:mysql://qs-test-ppe-db.chlhkgnyqqkn.rds.cn-north-1.amazonaws.com.cn:3306?autoReconnect=true&useUnicode=yes&characterEncoding=UTF-8";
        String schema = "tmp";
        String userName = "devuser";
        String password = "qyff2011";
        return new JdbcTemplate(DBUtil.getDataSource(url, schema, userName, password));
    }
}
