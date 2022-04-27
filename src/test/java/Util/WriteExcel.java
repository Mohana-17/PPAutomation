package Util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class WriteExcel {
	
	public void writeExcel(String sheetName, String cellValue, int row, int col) throws IOException {		
		File src= new File("C:\\Users\\t462204\\eclipse-workspace\\PSPrint\\src\\test\\java\\TestResult\\TestResult.xlsx");
		FileInputStream fis = new FileInputStream(src);
		XSSFWorkbook wb = new XSSFWorkbook(fis);
		XSSFSheet testSheet = wb.getSheet(sheetName);
//		int rowCount= testSheet.getLastRowNum()-testSheet.getFirstRowNum();
//		
//		 for(int i=0; i<=rowCount;i++)
//			 Cell cell = row.getCell(i);
//		 if(cell == null)
//			{
//				cell=row.createCell(i);
//				cell.setCellValue(OrderNumber);
//			}
		testSheet.getRow(row).createCell(col).setCellValue(cellValue);
		FileOutputStream fout =new FileOutputStream(src);
		wb.write(fout);
		wb.close();
		fout.close();	
	}

}
