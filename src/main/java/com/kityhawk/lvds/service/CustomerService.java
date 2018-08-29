package com.kityhawk.lvds.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Date;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kityhawk.lvds.dao.CustomerDao;
import com.kityhawk.lvds.dao.SysOrgDao;
import com.kityhawk.lvds.model.Customer;

@Service
public class CustomerService {
	
	@Autowired
	private CustomerDao customerDao;
	
	@Autowired
	private SysOrgDao sysOrgDao;
	
	@Autowired
	private ExcelProcess excelProcess;
	
	public String getCustNameByCtf(String cust_ctf)
	{
		Customer cust = customerDao.getCustByCtf(cust_ctf);
		return cust.getCust_name();
	}
	
	public List<Customer> getCustLIstByOrgId(String org_Id)
	{
		List<Customer> custlist = customerDao.getCustListByOrgId(org_Id);
		return custlist;
	}
	
	/**
	 * 从excel表格中读取数据并插入数据库
	 */
	@SuppressWarnings("deprecation")
	public void readCustomerFromFile(String fileName, int sheetNum, String userorg_id, String labelTableName) throws IOException
	{
		Workbook workbook = null;
	    File excelFile = new File(fileName);

        String suffix = fileName.substring(fileName.lastIndexOf(".") + 1);  
	    InputStream ins = new FileInputStream(excelFile);
	   
		if (suffix.equals("xlsx"))		workbook = new XSSFWorkbook(ins);
		else if(suffix.equals("xls"))		workbook = new HSSFWorkbook(ins);
		
	    Sheet sheet = workbook.getSheetAt(sheetNum);									//得到工作薄
	    if(checkHead(sheet))													//判断表头结构
	    {	
		    int lastRowIndex = excelProcess.getMaxRowNum(sheet, 0);
		    System.out.println("文件最大行数为:"+(lastRowIndex));
		    
		    CellStyle cellStyle_ok = workbook.createCellStyle();				//成功提示绿色字体
			Font font_ok = workbook.createFont();
			font_ok.setFontHeightInPoints((short)12); 								//字体大小
			font_ok.setFontName("宋体");
			font_ok.setColor(HSSFColor.GREEN.index);    							//绿字
		    cellStyle_ok.setFont(font_ok);
		    
			CellStyle cellStyle_erro = workbook.createCellStyle();				//失败提示红色字体
			Font font_erro = workbook.createFont();
			font_erro.setFontHeightInPoints((short)12); 							//字体大小
			font_erro.setFontName("宋体");
			font_erro.setColor(HSSFColor.RED.index);    							//红字
		    cellStyle_erro.setFont(font_erro);
		    
		    int successSize = 0;
		    int errorSize = 0;
		    for (int i = 3; i < lastRowIndex; i++)
		    //for (int i = 3; i < 30; i++)
		    {
		    	Row row = sheet.getRow(i);
				if(readRowUpdateToDB(row, userorg_id, labelTableName, cellStyle_ok, cellStyle_erro))		successSize++;
				else												errorSize++;
				if(i % 100 == 0)	System.out.println("操作完毕"+i+"条，其中成功"+successSize +"，失败" + errorSize + "条");
		    }
		    System.out.println("操作完毕，其中成功"+successSize +"，失败" + errorSize + "条");
			FileOutputStream outs = new FileOutputStream(excelFile);
			workbook.write(outs);
		    workbook.close();
		    ins.close();
	    }
	    else System.out.println(fileName+"文件头格式错误");
	}
	
	/**
	 * 对row中每一列进行检查，只要是填入内容都进行检查，
	 * 检查不通过的，在erroMsg中添加相应的出错信息
	 * @param customer
	 * @param row
	 * @return
	 */
	public String checkBuildCustomer(Customer customer, Row row)
	{
		String erroMsg = "";									//保存出错列信息
		String tmpValue = "";	

		tmpValue = excelProcess.getCellValue(row.getCell(1));		//机构号码
		if(StringUtils.isNotBlank(tmpValue)){
			if(tmpValue.length() == 8)		{customer.setOrg_id(tmpValue.trim());  customer.setOffice_id(tmpValue.trim());}
			else 		erroMsg += "网点编号,";	
		}
		else erroMsg += "网点编号为空,";
		
		tmpValue = excelProcess.getCellValue(row.getCell(2));		//客户姓名
		if(StringUtils.isNotBlank(tmpValue)){
			if(tmpValue.length()>0&&tmpValue.length() <= 100)		customer.setCust_name(tmpValue.trim());
			else 		erroMsg += "客户姓名,";	
		}
		else erroMsg += "客户姓名为空,";	
		
		tmpValue = excelProcess.getCellValue(row.getCell(3));			//性别
		if (StringUtils.isNotBlank(tmpValue))
		{
			if(tmpValue.equals("男")||tmpValue.equals("女"))	customer.setCust_gender(tmpValue.equals("男")?1:0);
			else 		erroMsg += "性别,";
		}		
				
		tmpValue = excelProcess.getCellValue(row.getCell(4));		//手机
		if(StringUtils.isNotBlank(tmpValue))
		{
			if(Pattern.compile("^1[3-9][0-9]{9}$").matcher(tmpValue).matches())			customer.setCust_cphone(tmpValue);
			else 		erroMsg += "手机,";
		}
										
		tmpValue = excelProcess.getCellValue(row.getCell(5));		//地址
		if(StringUtils.isNotBlank(tmpValue))
		{
			if(tmpValue.length() <= 190)  customer.setCust_addr(tmpValue);
			else		erroMsg += "地址,";
		}
							
		tmpValue = excelProcess.getCellValue(row.getCell(6));		//身份证
		if(StringUtils.isNotBlank(tmpValue))
		{
			if(Pattern.compile("^\\d{15}$|^\\d{17}[0-9Xx]$").matcher(tmpValue).matches())		//身份证合法
			{	
				customer.setCust_ctf(tmpValue.toUpperCase());			
				/*
				DateFormat sdfyear = new SimpleDateFormat("yyyy");
				DateFormat sdfmonth = new SimpleDateFormat("MM-dd");						
				Date briday = parseCtfToBrithday(tmpValue.toUpperCase());
				
				customer.setBirthday(briday);											
				customer.setThebirthday(sdfmonth.format(briday));
				customer.setThebirthyear(sdfyear.format(briday));
				
				//如果客户性别未填，用身份证号自动识别
				if(StringUtils.isNotBlank(customer.getCust_gender()+""))  customer.setCust_gender(Integer.parseInt(getGenderByIdCard(tmpValue)));
				*/
			}
			else 	 	erroMsg += "身份证,";
		}
						
		tmpValue = excelProcess.getCellValue(row.getCell(7));		//是否邮政客户
		if(StringUtils.isNotBlank(tmpValue))
		{
			if(tmpValue.equals("是")||tmpValue.equals("否"))	customer.setBt_post(tmpValue.equals("是")?1:0);
			else 		erroMsg += "是否邮政客户,";
		}
				
		tmpValue = excelProcess.getCellValue(row.getCell(8));		//主要开户行
		if(StringUtils.isNotBlank(tmpValue))
		{
			if (tmpValue.equals("邮储"))    					customer.setMain_bank(1);
			else if (tmpValue.equals("农行")) 				customer.setMain_bank(2);
			else if (tmpValue.equals("农信社"))			customer.setMain_bank(3);
			else if (tmpValue.equals("其他"))				customer.setMain_bank(4);
			else 		erroMsg += "主要开户行,";
		}
		
		tmpValue = excelProcess.getCellValue(row.getCell(9));		//工作性质
		if(StringUtils.isNotBlank(tmpValue))
		{
			if (tmpValue.equals("国企"))					customer.setJob_des(1);
			else if (tmpValue.equals("外企"))			customer.setJob_des(2);
			else if (tmpValue.equals("私企"))			customer.setJob_des(3);
			else if(tmpValue.equals("公职单位"))	customer.setJob_des(4);
			else if(tmpValue.equals("其他"))			customer.setJob_des(5);
			else 		erroMsg += "工作性质,";
		}
		
		tmpValue = excelProcess.getCellValue(row.getCell(10));		//职务
		if(StringUtils.isNotBlank(tmpValue))
		{
			if (tmpValue.equals("白领")) 					customer.setDuty(1);
			else if (tmpValue.equals("公务员")) 		customer.setDuty(2);
			else if (tmpValue.equals("教师"))			customer.setDuty(3);
			else if(tmpValue.equals("销售"))			customer.setDuty(4);
			else if(tmpValue.equals("农民"))			customer.setDuty(5);
			else if(tmpValue.equals("商人"))			customer.setDuty(6);
			else if(tmpValue.equals("务工"))			customer.setDuty(7);
			else if(tmpValue.equals("学生"))			customer.setDuty(8);
			else if(tmpValue.equals("退休人员"))	customer.setDuty(9);
			else if(tmpValue.equals("其他"))			customer.setDuty(0);
			else 		erroMsg += "职务,";
		}
		
		tmpValue = excelProcess.getCellValue(row.getCell(11));		//年收入
		if(StringUtils.isNotBlank(tmpValue))
		{
			if (tmpValue.equals("5万元以下")) 			customer.setAnnual(1);
			else if (tmpValue.equals("5-10万元"))		customer.setAnnual(2);
			else if (tmpValue.equals("10万元以上"))	customer.setAnnual(3);
			else 		erroMsg += "年收入,";
		}
	
		tmpValue = excelProcess.getCellValue(row.getCell(12));		//家庭情况
		if(StringUtils.isNotBlank(tmpValue))
		{
			if (tmpValue.equals("未婚"))						customer.setFm_cond(1);
			else if (tmpValue.equals("已婚无子女"))	customer.setFm_cond(2);
			else if (tmpValue.equals("已婚有子女"))	customer.setFm_cond(3);
			else if (tmpValue.equals("子女上学"))		customer.setFm_cond(4);
			else if (tmpValue.equals("子女就业"))		customer.setFm_cond(5);
			else 		erroMsg += "家庭情况,";
		}
		
		tmpValue = excelProcess.getCellValue(row.getCell(13));		//重要程度
		if(StringUtils.isNotBlank(tmpValue))
		{
			if (tmpValue.equals("高价值客户"))			customer.setImportance_degree(1);
			else if (tmpValue.equals("价值客户"))		customer.setImportance_degree(2);
			else if (tmpValue.equals("普通客户"))		customer.setImportance_degree(3);
			else 		erroMsg += "重要程度,";
		}
	
		tmpValue = excelProcess.getCellValue(row.getCell(14));		//方便通话时间段
		if(StringUtils.isNotBlank(tmpValue))
		{
			if(tmpValue.equals("8:00-12:00")||tmpValue.equals("12:00-14:00")||tmpValue.equals("14:00-17:00")||tmpValue.equals("17:00-21:00"))		
				customer.setRelation_time(tmpValue);
			else 		erroMsg += "方便通话时间段,";
		}
		
		return erroMsg;
	}
	
	/**
	 * 将excel中的某一列插入到数据库中
	 * @param row
	 * @param userOrgId
	 * @param cellStyle_ok
	 * @param cellStyle_erro
	 * @return
	 */
	public boolean readRowUpdateToDB(Row row,String userOrgId,String labelTableName,CellStyle cellStyle_ok,CellStyle cellStyle_erro)
	{
		try
		{					
				Customer customer = new Customer();
				customer.setCust_id(UUID.randomUUID().toString().replace("-", ""));
				//对所填行的数据进行格式检查并返回错误信息
				String erroMsg = checkBuildCustomer(customer, row);
				
				if(StringUtils.isNotBlank(erroMsg))					//如果格式检查不通过，直接添加错误信息
				{
					erroMsg = "客户信息:"+erroMsg+"格式错误";				//如果有已填报但是不符合填写标准的数据列
					excelProcess.setCellValue(row,15,erroMsg,cellStyle_erro);
					return false;
				}
				//身份证和手机均为空
				else if(StringUtils.isBlank(excelProcess.getCellValue(row.getCell(4)))&&		//手机号
						StringUtils.isBlank(excelProcess.getCellValue(row.getCell(6))))				//身份证					
				{
					erroMsg = "未填入关键信息";
					excelProcess.setCellValue(row,	15, erroMsg, cellStyle_erro);
					return false;
				}
				else										//如果格式检查正确
				{
					Map<String ,Object> insertResult = insertCustomer(customer,userOrgId, labelTableName);
					if((Integer)insertResult.get("result") == 1)	
					{	
						excelProcess.setCellValue(row,15,"客户更新成功"  ,cellStyle_ok);	
						if((Integer)insertResult.get("labelresult") == 1)	excelProcess.setCellValue(row,16,(String)insertResult.get("labelmsg") ,cellStyle_ok);
						else 																			excelProcess.setCellValue(row,16,(String)insertResult.get("labelmsg") ,cellStyle_erro);
						return true;
					}
					else
					{	
						excelProcess.setCellValue(row,15,(String)insertResult.get("msg"),cellStyle_erro);
						if((Integer)insertResult.get("labelresult") == 1)	excelProcess.setCellValue(row,16,(String)insertResult.get("labelmsg") ,cellStyle_ok);
						else 																			excelProcess.setCellValue(row,16,(String)insertResult.get("labelmsg") ,cellStyle_erro);
						return false;
					}
				}	
		}
    	catch (Exception e)
    	{
    		System.out.println("批量更新CUSTOMER表,批量更新客户数据出错"+e);
    		return false;
		}
	}
	
	
	/**
	 * 通过身份证号码判断性别
	 * @param cust_ctf
	 * @return
	 */
	public  String getGenderByIdCard(String cust_ctf) 
	{
        String sGender = "未知";
        if (cust_ctf.length()==18) 
        {
        	 String sCardNum = cust_ctf.substring(16, 17);
             if (Integer.parseInt(sCardNum) % 2 != 0)		sGender = "1";		//男
             else 	sGender = "2";																	//女            
		}
        else if (cust_ctf.length()==15)
        {
			 String sCardNum = cust_ctf.substring(13, 14);
             if (Integer.parseInt(sCardNum) % 2 != 0)		sGender = "1";		//男
             else		sGender = "2";																	//女          
		}
        return sGender;
    }
	
	/**截取身份证号生日日期
	 * @param cust_ctf
	 * @return
	 */
	public static Date parseCtfToBrithday(String cust_ctf)
	{
		Date brithday = null;
		try 
		{
			if (StringUtils.isNotBlank(cust_ctf)) 
			{
				if (cust_ctf.length() == 15) 
				{
					// 15位身份证号获取生日
					DateFormat sdf15 = new SimpleDateFormat("yyMMdd");
					String brithdayNum=cust_ctf.substring(6, 12);
					if(StringUtils.isNumeric(brithdayNum))	brithday = (Date) sdf15.parse(brithdayNum);
				}
				else if (cust_ctf.length() == 18)
				{
					// 18位身份证号获取生日
					DateFormat sdf18 = new SimpleDateFormat("yyyyMMdd");
					String brithdayNum=cust_ctf.substring(6, 14);
					if(StringUtils.isNumeric(brithdayNum))	brithday = (Date) sdf18.parse(brithdayNum);
				}
			}
		}
		catch (ParseException e)
		{
			e.printStackTrace();
		}
		return brithday;
	}
	
	/**
	 * 检查excel表格头文件
	 * @param sheet
	 * @return
	 */
	public boolean checkHead(Sheet sheet)
	{
		if(sheet != null)   
		{
			Row row = sheet.getRow(2);				//获取表格表头行
			if(row != null )
			{
				if(!excelProcess.getCellValue(row.getCell(0)).equals("序号"))						return false;
				if(!excelProcess.getCellValue(row.getCell(1)).equals("网点机构号"))			return false;
				if(!excelProcess.getCellValue(row.getCell(2)).equals("客户姓名")	)			return false;
				if(!excelProcess.getCellValue(row.getCell(3)).equals("性别"))						return false;
				if(!excelProcess.getCellValue(row.getCell(4)).equals("手机号码")	)			return false;
				if(!excelProcess.getCellValue(row.getCell(5)).equals("地址"))						return false;
				if(!excelProcess.getCellValue(row.getCell(6)).equals("身份证号码"))			return false;
				if(!excelProcess.getCellValue(row.getCell(7)).equals("是否邮政客户"))		return false;
				if(!excelProcess.getCellValue(row.getCell(8)).equals("主要开户行"))			return false;
				if(!excelProcess.getCellValue(row.getCell(9)).equals("工作性质"))				return false;
				if(!excelProcess.getCellValue(row.getCell(10)).equals("职务"))					return false;
				if(!excelProcess.getCellValue(row.getCell(11)).equals("年收入"))				return false;
				if(!excelProcess.getCellValue(row.getCell(12)).equals("家庭情况"))			return false;
				if(!excelProcess.getCellValue(row.getCell(13)).equals("重要程度"))			return false;
				if(!excelProcess.getCellValue(row.getCell(14)).equals("方便通话时间段"))	return false;
				return true;
			}
			else return false;
		}
		else	return false;
	}
	
	/**
	 * 插入数据库操作
	 * @param customer
	 * @param userOrgId
	 * @return
	 */
	public Map<String ,Object> insertCustomer(Customer customer,String userOrgId, String labelTableName)
	{
		Map<String ,Object> resultMap = new HashMap<String ,Object>();
		//int resultValue = 0;	
		try 
		{
			if(sysOrgDao.isOrgInRangeWd(customer.getOffice_id(), userOrgId) == 1)
			{
				/*
				resultMap.put("result", 1);
				*/
				//同时有身份证和手机号
				if(StringUtils.isNotBlank(customer.getCust_cphone())&&StringUtils.isNotBlank(customer.getCust_ctf()))		
				{
					if(customerDao.addCustomerByPhoneCtf(customer) != 1)
					{
						resultMap.put("result", 0);
						resultMap.put("msg", "手机号或者身份证号重复");
					}
					else	resultMap.put("result", 1);
					
					
					Customer tmpCustCtf = customerDao.getCustByCtf(customer.getCust_ctf());
					if(tmpCustCtf != null)	
					{
						if(sysOrgDao.isOrgInRangeWd(tmpCustCtf.getOffice_id(), userOrgId) == 1)	
						{
							if(customerDao.addCustomerToLabel(labelTableName, tmpCustCtf.getCust_id()) == 1)
							{
								resultMap.put("labelresult", 1);
								resultMap.put("labelmsg", "以身份证加入标签");
							}
							else 
							{
								resultMap.put("labelresult", 0);
								resultMap.put("labelmsg", "以身份证加入标签失败");
							}							
						}
						else
						{
							resultMap.put("labelresult", 0);
							resultMap.put("labelmsg", "以身份证加入标签，客户机构超出权限");							
						}

					}
					else
					{
						Customer tmpCustPhone = customerDao.getCustByPhone(customer.getCust_cphone());
						if(tmpCustPhone != null)
						{
							if(sysOrgDao.isOrgInRangeWd(tmpCustPhone.getOffice_id(), userOrgId) == 1)
							{
								if(customerDao.addCustomerToLabel(labelTableName, tmpCustPhone.getCust_id()) == 1)
								{
									resultMap.put("labelresult", 1);
									resultMap.put("labelmsg", "以手机号加入标签");
								}
								else 
								{
									resultMap.put("labelresult", 0);
									resultMap.put("labelmsg", "以手机号加入标签失败");
								}								
							}
							else
							{
								resultMap.put("labelresult", 0);
								resultMap.put("labelmsg", "以身份证加入标签，客户机构超出权限");							
							}
						}
					}					
				}
				//无手机号 有身份证
				else if(StringUtils.isBlank(customer.getCust_cphone())&&StringUtils.isNotBlank(customer.getCust_ctf()))
				{
					if(customerDao.addCustomerByCtf(customer) != 1)
					{
						resultMap.put("result", 0);
						resultMap.put("msg", "身份证号重复");
					}
					else resultMap.put("result", 1);
					Customer tmpCustCtf = customerDao.getCustByCtf(customer.getCust_ctf());
					if(tmpCustCtf != null)	
					{
						if(sysOrgDao.isOrgInRangeWd(tmpCustCtf.getOffice_id(), userOrgId) == 1)	
						{
							if(customerDao.addCustomerToLabel(labelTableName, tmpCustCtf.getCust_id()) == 1)
							{
								resultMap.put("labelresult", 1);
								resultMap.put("labelmsg", "以身份证加入标签");
							}
							else 
							{
								resultMap.put("labelresult", 0);
								resultMap.put("labelmsg", "以身份证加入标签失败");
							}							
						}
						else
						{
							resultMap.put("labelresult", 0);
							resultMap.put("labelmsg", "以身份证加入标签，客户机构超出权限");							
						}

					}
				}
				//有手机号无身份证
				else if(StringUtils.isNotBlank(customer.getCust_cphone())&&StringUtils.isBlank(customer.getCust_ctf()))
				{
					if(customerDao.addCustomerByPhone(customer) != 1)
					{
						resultMap.put("result", 0);
						resultMap.put("msg", "手机号重复");
					}
					else resultMap.put("result", 1);
					Customer tmpCustPhone = customerDao.getCustByPhone(customer.getCust_cphone());
					if(tmpCustPhone != null)
					{
						if(sysOrgDao.isOrgInRangeWd(tmpCustPhone.getOffice_id(), userOrgId) == 1)
						{
							if(customerDao.addCustomerToLabel(labelTableName, tmpCustPhone.getCust_id()) == 1)
							{
								resultMap.put("labelresult", 1);
								resultMap.put("labelmsg", "以手机号加入标签");
							}
							else 
							{
								resultMap.put("labelresult", 0);
								resultMap.put("labelmsg", "以手机号加入标签失败");
							}								
						}
						else
						{
							resultMap.put("labelresult", 0);
							resultMap.put("labelmsg", "以身份证加入标签，客户机构超出权限");							
						}
					}
				}		
			}
			else 
			{
				resultMap.put("result", 0);
				resultMap.put("msg", "客户机构范围不正确");
			}			
		}
		catch (Exception e) 	{			
			resultMap.put("result", 0);
			resultMap.put("msg", "插入数据库内部错误");			
		}
		return resultMap;
	}
}
