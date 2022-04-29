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
		testSheet.getRow(row).createCell(col).setCellValue(cellValue);
		FileOutputStream fout =new FileOutputStream(src);
		wb.write(fout);
		wb.close();
		fout.close();	
	}
}
