package com.kityhawk.test.poi;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ExcelProcess {
	
	@Autowired
	private DataBaseService dataBaseService;
	
	public void readSheetFromFile(String filename,int sheetNum) throws IOException
	{
		
		Workbook workbook = null;
	    File excelFile = new File(filename);
        String fileName = excelFile.getName();  
        String suffix = fileName.substring(fileName.lastIndexOf(".") + 1);  
	    InputStream ins = new FileInputStream(excelFile);
	   
		if (suffix.equals("xlsx"))		workbook = new XSSFWorkbook(ins);
		else if(suffix.equals("xls"))		workbook = new HSSFWorkbook(ins);
		
	    Sheet sheet = workbook.getSheetAt(sheetNum);									//得到工作薄

	    int lastRowIndex = getMaxRowNum(sheet, 0);
	    System.out.println("文件最大行数为:"+(lastRowIndex));
	    int citySeq = 0;
	    int countySeq = 0;
	    int nodeSeq = 0;
	    java.text.DecimalFormat decimalFormat = new java.text.DecimalFormat("000");
	    for (int i = 0; i < lastRowIndex; i++)
	    {
	        Row row = sheet.getRow(i);
	        if(getCellValue(row.getCell(1)).length() != 0)
	        {
	        	String org_id = getCellValue(row.getCell(1));
	        	String org_name = dataBaseService.getOrgNameById(org_id).replaceAll("\\s*", "");
	        	//if(StringUtils.isNotBlank(org_name))
	        	if(StringUtils.isBlank(org_name))
	        	{
	        		System.out.println((i+1)+"|机构号:"+getCellValue(row.getCell(1))+"未找到机构");
	        	}
	        	else
	        	{
	        		String remark = dataBaseService.getRemarkById(org_id);
	        		if(remark.equals("2"))
	        		{
	        			citySeq++;
	        			countySeq = 1;
	        			nodeSeq = 1;
	        		}
	        		else if(remark.equals("3"))
	        		{
	        			countySeq++;
	        			nodeSeq = 1;
	        		}
	        		else if(remark.equals("4"))		nodeSeq ++;
	        		String seqNo = decimalFormat.format(citySeq)+decimalFormat.format(countySeq)+decimalFormat.format(nodeSeq);
	        		setCellValueString(row, 2, seqNo);
	        		dataBaseService.setSeq(org_id, seqNo);
	        	}
	        	//System.out.println(i+"|机构名称:"+dataBaseService.getOrgNameById(getCellValue(row.getCell(1))));
	        	
	        	//System.out.println(getCellValue(row.getCell(1)));
	        }
	        else 
	        {
	        	String org_id = dataBaseService.getOrgIdByName(getCellValue(row.getCell(0)));
	        	if(StringUtils.isBlank(org_id))
	        	{
	        		System.out.println(i+1+"|机构名称:"+getCellValue(row.getCell(0))+"未找到机构");
	        	}
	        	else
	        	{
	        		setCellValueString(row,1,org_id);
	        	}
	        	//System.out.println(i+"|机构号:"+dataBaseService.getOrgIdByName(getCellValue(row.getCell(0))));
	        	//System.out.println(getCellValue(row.getCell(0)));
	        }
	    }
	    System.out.println("操作完毕");
		FileOutputStream outs = new FileOutputStream(excelFile);
		workbook.write(outs);
	    workbook.close();
	    ins.close();
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
				case STRING: 	{value = cell.getStringCellValue();		break;	}
				case NUMERIC: 	{value = new java.text.DecimalFormat("########").format(cell.getNumericCellValue())+"";		break;	}
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
	 * 对某一个Cell赋值
	 * @param cell
	 * @return
	 */
	public void setCellValueString(Row row,int colNum,String value)
	{		
			Cell cell = row.getCell(colNum);
			if(cell == null)	cell = row.createCell(colNum);
			cell.setCellValue(value);
	}
	
}
