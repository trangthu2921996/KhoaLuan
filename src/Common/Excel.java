package Common;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.String;
import java.sql.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Locale;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.CellReference;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.format.CellDateFormatter;
import org.apache.poi.ss.format.CellGeneralFormatter;
import org.apache.poi.ss.format.CellNumberFormatter;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.util.DateFormatConverter;

import bsh.ParseException;

public class Excel {
	private static final String TIME_FORMAT_24_HOUR = null;
	HSSFWorkbook wb = null;
	HSSFSheet sheet = null;
	FileInputStream file = null;
	String pathFile = "";
	String pathOutput = "";
	

	public Excel(String pathfile, String timeBuild) throws Exception {
		pathFile = pathfile;
		pathOutput = pathFile.substring(0, pathFile.length()-4)  + "-"+timeBuild+".xls";
		file = new FileInputStream(new File(pathOutput));
		wb = new HSSFWorkbook(file);
	}
	
	public Excel(String pathfile) throws Exception {
		file = new FileInputStream(new File(pathfile));
		wb = new HSSFWorkbook(file);
		
		pathOutput = pathfile;
	}
	
	public Excel() {
		
	}
	
	public void createFileOutput(String pathfile, String timeBuild) throws Exception {
//		System.out.println("Working Directory = " + System.getProperty("user.dir"));
		pathFile = pathfile;
		pathOutput = pathFile.substring(0, pathFile.length()-4)  + "-"+timeBuild+".xls";
		file = new FileInputStream(new File(pathfile));
		wb = new HSSFWorkbook(file);
		
		FileOutputStream outFile =new FileOutputStream(new File(pathOutput));
		wb.write(outFile);
		outFile.close();
		
		file = new FileInputStream(new File(pathOutput));
		wb = new HSSFWorkbook(file);
	}
	
	public void accessSheet(String sheetName) throws Exception {
		sheet = wb.getSheet(sheetName);
	}
	public void accessSheetIndex(int index) throws Exception {
		sheet = wb.getSheetAt(index);
	}

	public HSSFSheet getSheet() throws Exception {
		return this.sheet;
	}
	public HSSFSheet getSheetName(HSSFSheet sheetName) throws Exception {
		return this.sheet;
	}
	
	public String getStringData(int column, int row) throws Exception {
		try{
			Cell cell = sheet.getRow(row).getCell(column);
			
			if(cell == null)
				return "";
			else {
				cell.setCellType(Cell.CELL_TYPE_STRING);
				return Common.checkNull(cell.getStringCellValue());	
			}
		}catch(NullPointerException e){
			return "";
		}
	}
	public int getNumberictData(int column, int row) throws Exception {
		try{
			Cell cell = sheet.getRow(row).getCell(column);
			
			if(cell == null)
				return -1;
			else {
				cell.setCellType(Cell.CELL_TYPE_NUMERIC);
				return (int) cell.getNumericCellValue();	
			}
		}catch(NullPointerException e){
			return -1;
		}
	}
	public String getCellDate(int column,int row) throws Exception{
        Cell cell = sheet.getRow(row).getCell(column);
        String  cellValue="";
        if (DateUtil.isCellDateFormatted(cell))
        {
           SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
           cellValue  = sdf.format(cell.getDateCellValue());
        }
		return  cellValue;
    }
	
	public String getCellDate1(int column,int row) throws Exception{
        Cell cell = sheet.getRow(row).getCell(column);
        String  cellValue="";
        if (DateUtil.isCellDateFormatted(cell))
        {
           SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
           cellValue  = sdf.format(cell.getDateCellValue());
        }
		return  cellValue;
    }

	
	public short getColumn(String nameColumn) {
		CellReference cell = new CellReference(nameColumn);
		return cell.getCol();
	}
	public String getFormulaCellData(int column1, int row1) throws Exception {
		CellReference cellReference = new CellReference(row1, column1); 
		Row row = sheet.getRow(cellReference.getRow());
		Cell cell = row.getCell(cellReference.getCol());
		FormulaEvaluator formulaEval = wb.getCreationHelper().createFormulaEvaluator();
		String value=formulaEval.evaluate(cell).formatAsString();
		return value.substring(1, value.length()-1);
	}
	
	public void printResultIntoExcel(int column, int row, boolean result) throws Exception {
		HSSFRow rows     = sheet.getRow((short)row); 
		HSSFCell cells   = rows.createCell((short)column); 
		if(result == true)
			cells.setCellValue("SUCCESSED"); 
		else
			cells.setCellValue("FAILED"); 
	}
	
	public void printStringIntoExcel(int column, int row, String string) throws Exception {
		HSSFRow rows     = sheet.getRow((short)row); 
		HSSFCell cells   = rows.createCell((short)column); 
		cells.setCellValue(string); 
	}

	public void compareStringAndPrint(String string1, String string2, int column, int row) throws Exception {
		if(string1.equals(string2))
			this.printResultIntoExcel(column, row, true);
		else
			this.printResultIntoExcel(column, row, false);
	}
	
	public void printResultToFlow(String actualResult, String compareString, Excel excel, int row, int column) throws Exception {
		if(actualResult.equals(compareString))
			printResultIntoExcel(column, row, true);
		else
			printResultIntoExcel(column, row, false);
	}
	public void write() throws Exception {
		FileOutputStream outFile =new FileOutputStream(new File(pathOutput));
		wb.write(outFile);
		outFile.close();
	}
	public void finish() throws Exception {
		file.close();
		write();
		wb.close();
	}
	public void finish1() throws Exception {
		file.close();
		wb.close();
	}
	
}
