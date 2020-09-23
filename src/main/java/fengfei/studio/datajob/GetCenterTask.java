package fengfei.studio.datajob;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.jdbc.core.JdbcTemplate;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class GetCenterTask {
    static String[] headers = new String[]{"学校", "symbol", "函授站名称", "地址", "管理员", "联系电话", "email", "zip", "introduction"};

    public static void main(String[] args) {
        JdbcTemplate globalDB = DBUtil.getGlobalTemplate();

        List<Map<String, Object>> degreeSchoolMap = globalDB.queryForList("SELECT s.id, s.name, s.symbol FROM global.school s\n" +
                "INNER JOIN global.school_category sc ON s.category_id = sc.id\n" +
                "WHERE sc.type = 1 AND s.status = 1");


        List<SchoolCenter> schoolCenters = new ArrayList<>();
        for (Map<String, Object> schoolMap : degreeSchoolMap){
            JdbcTemplate schoolTemplate = DBUtil.getSchoolTemplate(Integer.valueOf(schoolMap.get("id").toString()));

            List<Map<String, Object>> centerMapList = schoolTemplate.queryForList("SELECT `name`, address, manager, phone, email, zip, introduction FROM center");

            for (Map<String, Object> centerMap : centerMapList) {
                SchoolCenter center = new SchoolCenter();
                center.setSchoolName(schoolMap.get("name").toString());
                center.setSchoolSymbol(schoolMap.get("symbol").toString());

                center.setCenterName(String.valueOf(centerMap.get("name")));
                center.setCenterAddr(String.valueOf(centerMap.get("address")));
                center.setCenterManager(String.valueOf(centerMap.get("manager")));
                center.setCenterPhone(String.valueOf(centerMap.get("phone")));
                center.setCenterEmail(String.valueOf(centerMap.get("email")));
                center.setCenterZIP(String.valueOf(centerMap.get("zip")));
                center.setCenterIntro(String.valueOf(centerMap.get("introduction")));

                schoolCenters.add(center);
            }
        }

        try {
            XSSFWorkbook workbook = new XSSFWorkbook();

            XSSFSheet sheet = workbook.createSheet();

            writeHeaderLine(sheet);

            writeDataLines(schoolCenters, sheet);

            FileOutputStream outputStream = new FileOutputStream("C:\\Users\\fengfei\\Desktop\\centers.xlsx");
            workbook.write(outputStream);
            workbook.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void writeHeaderLine(XSSFSheet sheet) {
        int numberOfColumns = headers.length;

        Row headerRow = sheet.createRow(0);
        for (int i = 0; i < numberOfColumns; i++) {
            Cell headerCell = headerRow.createCell(i);
            headerCell.setCellValue(headers[i]);
        }
    }

    private static void writeDataLines(List<SchoolCenter> result, XSSFSheet sheet) {
        int rowCount = 1;

        for (SchoolCenter center : result){
            Row row = sheet.createRow(rowCount++);

            row.createCell(0).setCellValue(center.getSchoolName());
            row.createCell(1).setCellValue(center.getSchoolSymbol());
            row.createCell(2).setCellValue(center.getCenterName());
            row.createCell(3).setCellValue(center.getCenterAddr());
            row.createCell(4).setCellValue(center.getCenterManager());
            row.createCell(5).setCellValue(center.getCenterPhone());
            row.createCell(6).setCellValue(center.getCenterEmail());
            row.createCell(7).setCellValue(center.getCenterZIP());
            row.createCell(8).setCellValue(center.getCenterIntro());
        }
    }
}
