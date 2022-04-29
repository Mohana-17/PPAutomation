package Util;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

public class ReadExcel {
	static Workbook wb;
	static Sheet sh;
	static Cell cell;
	static Row row;
	static String excelFilePath;
	static Map<String, Integer> columns = new HashMap<>();
	
	public static String EXCEL_PATH ="C:\\Users\\t462204\\eclipse-workspace\\PSPrint\\src\\test\\java\\TestResult\\TestResult.xlsx";
	
	public void setExcelFile(String sheetName) throws InvalidFormatException, EncryptedDocumentException, IOException {
		FileInputStream f = null;
		try {
			f = new FileInputStream(EXCEL_PATH);
			wb= WorkbookFactory.create(f);
			sh = wb.getSheet(sheetName);
			sh.getRow(0).forEach(cell->{
				columns.put(cell.getStringCellValue(), cell.getColumnIndex());
			});
		}
		catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	public String getCellData(int rownum, int column) {
		try {
			cell=sh.getRow(rownum).getCell(column);
			String CellData=null;
			switch(cell.getCellType()) {
			case STRING:
				CellData = cell.getStringCellValue();
				break;
			case BLANK:
				CellData="";
				break;
			default:
				break;
			}
			return CellData;
		}
		catch(Exception e) {
			return"";
		}
	}
	public String getCellData(String columnName, int rownum) {
		return getCellData(rownum, columns.get(columnName));
	}
		
				
		

}

