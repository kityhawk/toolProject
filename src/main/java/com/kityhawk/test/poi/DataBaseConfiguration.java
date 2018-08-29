package com.kityhawk.test.poi;

import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.core.JdbcTemplate;

@Configuration
public class DataBaseConfiguration 
{
	
	@Profile("development")
	@Bean
	public BasicDataSource OracleCSdataSource()
	{
		System.out.println("Creating development database");
		BasicDataSource  dataSource = new BasicDataSource();
		dataSource.setDriverClassName("oracle.jdbc.driver.OracleDriver");
		dataSource.setUrl("jdbc:oracle:thin:@//10.162.7.179:1521/pdborcl.localdomain");
		dataSource.setUsername("value_promotion");
		dataSource.setPassword("cvpinaction");
		dataSource.setInitialSize(2);
		dataSource.setMaxTotal(10);
		return dataSource;
	}
	
	@Profile("production")
	@Bean
	public BasicDataSource OracleSCdataSource()
	{
		System.out.println("Creating production database");
		BasicDataSource  dataSource = new BasicDataSource();
		dataSource.setDriverClassName("oracle.jdbc.driver.OracleDriver");
		dataSource.setUrl("jdbc:oracle:thin:@//10.162.19.12:1521/orclpdb.master");
		dataSource.setUsername("value_promotion");
		dataSource.setPassword("cvpinaction");
		dataSource.setInitialSize(2);
		dataSource.setMaxTotal(10);
		return dataSource;
	}
	
	@Profile("practice")
	@Bean
	public BasicDataSource OraclePXdataSource()
	{
		System.out.println("Creating practice database");
		BasicDataSource  dataSource = new BasicDataSource();
		dataSource.setDriverClassName("oracle.jdbc.driver.OracleDriver");
		dataSource.setUrl("jdbc:oracle:thin:@//10.162.7.182:1521/pdborcl.localdomain");
		dataSource.setUsername("value_promotion");
		dataSource.setPassword("cvpinaction");
		dataSource.setInitialSize(2);
		dataSource.setMaxTotal(10);
		return dataSource;
	}
	
    @Bean  
    public JdbcTemplate jdbcTemplate(BasicDataSource dataSource)
    {
        return new JdbcTemplate(dataSource);  
    }
	
}
