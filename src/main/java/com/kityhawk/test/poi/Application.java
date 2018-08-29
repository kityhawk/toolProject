package com.kityhawk.test.poi;

import java.io.IOException;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;


@Configuration
@ComponentScan
public class Application {

	public static void main(String[] args) {
        AnnotationConfigApplicationContext  context = new AnnotationConfigApplicationContext();
        //Sets the active profiles
        context.getEnvironment().setActiveProfiles("production");
        //Scans the mentioned package[s] and register all the @Component available to Spring
        context.scan("com.kityhawk.test.poi");
        context.refresh();
        ExcelProcess ep = context.getBean(ExcelProcess.class);
        try {
			ep.readSheetFromFile("D:/download/deptSeq.xls",0);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
        context.close();
	}

}
