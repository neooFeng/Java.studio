package fengfei.studio.temp;

import fengfei.studio.datajob.DBUtil;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.jdbc.core.JdbcTemplate;

import java.io.File;
import java.io.FileInputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class Tmp {
    public static void main(String[] args) {
        String filePath = "C:\\Users\\fengfei\\Desktop\\已匹配学校代码.xlsx";

        Map<String, String> schoolCodeMap = parseExecel(filePath);

        JdbcTemplate jdbcTemplate = DBUtil.getGlobalTemplate();

        for (Map.Entry<String, String> entry : schoolCodeMap.entrySet()){
            jdbcTemplate.update("update school set code = ? where id = ?", entry.getValue(), entry.getKey());
        }

    }

    private static Map<String, String> parseExecel(String filePath) {
        Map<String, String> result = new HashMap<>();

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

                Cell cell1 = row.getCell(0);
                cell1.setCellType(CellType.STRING);
                String schoolId = cell1.getStringCellValue();

                Cell cell2 = row.getCell(3);
                cell2.setCellType(CellType.STRING);
                String schoolCode = cell2.getStringCellValue();

                try {
                    Integer.parseInt(schoolCode);
                }catch (NumberFormatException e){
                    // nothing
                    continue;
                }

                result.put(schoolId, schoolCode);
            }
        }catch (Exception e){
            e.printStackTrace();
        }

        return result;
    }
}
