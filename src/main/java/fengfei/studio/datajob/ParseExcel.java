package fengfei.studio.datajob;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.jdbc.core.JdbcTemplate;

import java.io.File;
import java.io.FileInputStream;
import java.util.*;

public class ParseExcel {

    public static void main(String[] args) {
        String filePath = "C:\\Users\\fengfei\\Downloads\\student_delete.xlsx";

        parseExcel(filePath);
    }

    private static void parseExcel(String filePath) {

        try {
            FileInputStream fis = new FileInputStream(new File(filePath));

            XSSFWorkbook xssfWorkbook = new XSSFWorkbook(fis);

            XSSFSheet sheet = xssfWorkbook.getSheetAt(0);
            Iterator<Row> rowIterator = sheet.rowIterator();

            StringBuilder stringBuilder = new StringBuilder();
            while (rowIterator.hasNext()){
                Row row = rowIterator.next();
                if(row.getRowNum() < 1){
                    continue;
                }


                Cell cell0 = row.getCell(0);
                cell0.setCellType(CellType.STRING);

                stringBuilder.append("'").append(cell0.getStringCellValue()).append("'").append(",");
            }

            System.out.println(stringBuilder.toString());
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
