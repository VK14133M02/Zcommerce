package org.example;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.testng.annotations.DataProvider;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class DP {
    @DataProvider (name = "data-provider")
    public Object[][] dpMethod (Method m) throws IOException{
        int rowIndex=0;
        int cellIndex=0;
        List<List> outputList=new ArrayList<List>();

        FileInputStream excelFile = new FileInputStream(new File("src\\test\\resources\\DatasetsforZcommerce.xlsx"));
        Workbook workbook = new XSSFWorkbook(excelFile);
        Sheet selectedSheet = workbook.getSheet(m.getName());
        Iterator<Row> iterator = selectedSheet.iterator();
        while (iterator.hasNext()) {
            Row nextRow = iterator.next();
            Iterator<Cell> cellIterator = nextRow.cellIterator();
             List<String> innerList=new ArrayList<String>();
            while (cellIterator.hasNext()) {
                Cell cell = cellIterator.next();                
                if(rowIndex>0 && cellIndex>0)
                {
                    if(cell.getCellType()==CellType.STRING) {
                        innerList.add(cell.getStringCellValue());
                    } else if (cell.getCellType()==CellType.NUMERIC) {
                        innerList.add( String.valueOf(cell.getNumericCellValue()));
                    }
                }
                cellIndex = cellIndex+1;
            }
            rowIndex=rowIndex+1;
            cellIndex=0;
            if(innerList.size()>0)
                outputList.add(innerList);

        }
         
        excelFile.close();

        String[][] stringArray = outputList.stream().map(u -> u.toArray(new String[0])).toArray(String[][]::new);
        return stringArray;
        
    }

}
