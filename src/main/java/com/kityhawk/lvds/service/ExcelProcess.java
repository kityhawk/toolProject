package com.kityhawk.lvds.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Component;

@Component
public class ExcelProcess {
	
	/**
	 * 通过文件名称和表单编号 返回相应的Sheet变量
	 * @param filename
	 * @param sheetNum
	 * @return
	 * @throws IOException
	 */
	public Sheet readSheetFromFile(String filename,int sheetNum) throws IOException
	{
		
		Workbook workbook = null;
		Sheet sheet = null;

	    File excelFile = new File(filename);
	    String fileName = excelFile.getName();  
        String suffix = fileName.substring(fileName.lastIndexOf(".") + 1);  			//文件后缀
	    InputStream ins = new FileInputStream(excelFile);
	   
		if (suffix.equals("xlsx"))		workbook = new XSSFWorkbook(ins);
		else if(suffix.equals("xls"))		workbook = new HSSFWorkbook(ins);
		
		
	    if(workbook != null) 	sheet = workbook.getSheetAt(sheetNum);									//得到工作薄
	    return sheet;
	}
	
	/**
	 * 将某个workbook回写如文件
	 * @param excelFile
	 * @param workbook
	 * @throws IOException
	 */
	public void writeSheetToFile(File excelFile, Workbook workbook)  throws IOException
	{
		FileOutputStream outs = new FileOutputStream(excelFile);
		workbook.write(outs);
	}
	
	
	/**
	 * 获取某单元的内容并强制转换为字符串模式
	 * @param cell
	 * @return
	 */
	public String getCellValue(Cell cell)
	{
		String value = "";
		if(cell != null)
		{
			switch(cell.getCellTypeEnum())
			{
				case STRING: {value = cell.getStringCellValue();break;}
				case NUMERIC: {value = new java.text.DecimalFormat("########").format(cell.getNumericCellValue())+"";break;}
				default:	value = "";				
			}
			value = value.replaceAll("\\s*", "");
		}
		return value;
	}
	
	/**
	 * 获取某sheet中的最大行数
	 * @param cell
	 * @return
	 */
	public int getMaxRowNum(Sheet sheet,int checkCol)
	{
		int lastRowNum = 3;
		for(;lastRowNum<=sheet.getLastRowNum();lastRowNum++)			
		{
			Row row = sheet.getRow(lastRowNum);
			if(row == null)		break;
			if(getCellValue(row.getCell(checkCol)).length() == 0) 	break;
		}
		return lastRowNum;
	}
	
	/**
	 * 对行RowCell赋值
	 * @param cell
	 * @return
	 */
	public void setCellValueString(Row row,int colNum,String value)
	{		
			Cell cell = row.getCell(colNum);
			if(cell == null)	cell = row.createCell(colNum);
			cell.setCellValue(value);
	}
	
	/**
	 * 对某一个单位格进行string类型的赋值
	 * @param row
	 * @param colnum
	 * @param text
	 * @param cellStyle
	 */
	@SuppressWarnings("deprecation")
	public void setCellValue(Row row,int colnum,String text,CellStyle cellStyle)
	{
		Cell cell = row.getCell(colnum);
		if(cell == null)  	cell = row.createCell(colnum);
		cell.setCellStyle(cellStyle);
		cell.setCellType(Cell.CELL_TYPE_STRING);
		cell.setCellValue(text);
	}
	
}
