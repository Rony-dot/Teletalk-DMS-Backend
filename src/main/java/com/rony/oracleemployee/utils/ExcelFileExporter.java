package com.rony.oracleemployee.utils;

import com.rony.oracleemployee.model.User;
import net.sf.jasperreports.engine.export.ooxml.XlsxWorkbookHelper;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.List;

public class ExcelFileExporter {
    public static ByteArrayInputStream exportUserListToExcelFile(List<User> users){
        try(Workbook workbook = new XSSFWorkbook()){
            Sheet sheet = workbook.createSheet("users");
            Row row = sheet.createRow(0);
            CellStyle headerCellStyle = workbook.createCellStyle();
            headerCellStyle.setFillForegroundColor(IndexedColors.AQUA.getIndex());
            headerCellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);

            Cell cell = row.createCell(0);
            cell.setCellValue("ID");
            cell.setCellStyle(headerCellStyle);

            cell = row.createCell(1);
            cell.setCellValue("Name");
            cell.setCellStyle(headerCellStyle);

            for(int i=0; i<users.size(); i++){
                Row dataRow = sheet.createRow(i+1);
                dataRow.createCell(0).setCellValue(users.get(i).getId());
                dataRow.createCell(1).setCellValue(users.get(i).getName());
            }
            sheet.autoSizeColumn(0);
            sheet.autoSizeColumn(1);

            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            workbook.write(outputStream);

            return new ByteArrayInputStream(outputStream.toByteArray());

        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }
}
