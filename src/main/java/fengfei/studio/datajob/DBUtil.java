package fengfei.studio.datajob;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;
import java.util.Map;

public final class DBUtil {
    public static JdbcTemplate getGlobalTemplate(){
        String url = "jdbc:mysql://qs-pro-inst1.chlhkgnyqqkn.rds.cn-north-1.amazonaws.com.cn:3306?autoReconnect=true&useUnicode=yes&characterEncoding=UTF-8";
        String schema = "global";
        String userName = "feifanuniv";
        String password = "qyffRocks!9";
        return new JdbcTemplate(getDataSource(url, schema, userName, password));
    }

    public static JdbcTemplate getNpdbTemplate(){
        String url = "jdbc:mysql://qs-pro-inst1.chlhkgnyqqkn.rds.cn-north-1.amazonaws.com.cn:3306?autoReconnect=true&useUnicode=yes&characterEncoding=UTF-8";
        String schema = "npdb";
        String userName = "feifanuniv";
        String password = "qyffRocks!9";
        return new JdbcTemplate(getDataSource(url, schema, userName, password));
    }

    public static JdbcTemplate getSchoolTemplate(int schoolId){
        JdbcTemplate npdbTemplate =getNpdbTemplate();

        Map<String, Object> resultMap = npdbTemplate.queryForMap("select `url`, `schema_name` from school_db_setting WHERE school_id = ?", schoolId);

        String url = resultMap.get("url").toString();
        String schema = resultMap.get("schema_name").toString();
        String userName = "feifanuniv";
        String password = "qyffRocks!9";
        return new JdbcTemplate(getDataSource(url, schema, userName, password));
    }


    public static DataSource getDataSource(String url, String catalog, String userName, String password){
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("com.mysql.jdbc.Driver");
        dataSource.setUrl(url);
        dataSource.setCatalog(catalog);
        dataSource.setUsername(userName);
        dataSource.setPassword(password);

        return dataSource;
    }
}
