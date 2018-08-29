package com.kityhawk.lvds.app;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.kityhawk.lvds.model.Customer;
import com.kityhawk.lvds.service.CustomerService;

public class Application {
	
	public static void main(String[] args) {
		
        AnnotationConfigApplicationContext  context = new AnnotationConfigApplicationContext();
        //Sets the active profiles
        context.getEnvironment().setActiveProfiles("development");
        //Scans the mentioned package[s] and register all the @Component available to Spring
        context.scan( new String[] {"com.kityhawk.lvds.config"} );
        context.refresh();
		
        CustomerService customerService = context.getBean(CustomerService.class);
        
        try 
        {
        	System.out.println(customerService.getCustNameByCtf("422723196908104636"));
        	List<Customer> custList = customerService.getCustLIstByOrgId("5227272067422357561");
        	for(Customer cust: custList)	System.out.println(cust.getCust_name()+","+cust.getCust_cphone());
        	//customerService.readCustomerFromFile("D:/download/fileupload/20180725.xlsx", 0, "42000150", "LABEL_7162");
        	//excelProcess.readSheetFromFile("D:/download/deptSeq.xls",0);
		}
        catch (Exception e) 
        {
			e.printStackTrace();
		}
        
        context.close();
	}
	
	public static double getTimeC(String timeA,String timeB){
		double i = 0;
		
		//计算
		double aH,aM,bH,bM;
		aH = Integer.parseInt(timeA.split(":")[0]);
		aM = Integer.parseInt(timeA.split(":")[1]);
		bH = Integer.parseInt(timeB.split(":")[0]);
		bM = Integer.parseInt(timeB.split(":")[1]);
		
		double h = bH - aH;
		double m = bM - aM;
		if(m < 0){
			h = h -1;
			m = 60 + m;
		}
		
		double mm = m/60;
		BigDecimal bg = new BigDecimal(mm+1).setScale(2, RoundingMode.UP);
		
		i = h - 2 + bg.doubleValue()-1;
		System.out.println("i: " + i);
		return i;
	}

}
