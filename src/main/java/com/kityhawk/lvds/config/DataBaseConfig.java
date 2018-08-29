package com.kityhawk.lvds.config;

import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;

@Configuration
@PropertySource("classpath:/jdbc.properties")
public class DataBaseConfig
{
	
    @Autowired
    private Environment env;
		
	@Profile("practice")
	@Bean(name="practice")
	public BasicDataSource OraclePXdataSource()
	{
		System.out.println("Creating practice database");
		BasicDataSource  dataSource = new BasicDataSource();
		dataSource.setDriverClassName(env.getProperty("orcl.driverClassName"));
		dataSource.setUrl(env.getProperty("orcl.practiceurl"));
		dataSource.setUsername(env.getProperty("orcl.username"));
		dataSource.setPassword(env.getProperty("orcl.password"));
		dataSource.setInitialSize(2);
		dataSource.setMaxTotal(10);
		return dataSource;
	}
	
	@Profile("development")
	@Bean(name="development")
	public BasicDataSource OracleKFdataSource()
	{
		System.out.println("Creating development database");
		BasicDataSource  dataSource = new BasicDataSource();
		dataSource.setDriverClassName(env.getProperty("orcl.driverClassName"));
		dataSource.setUrl(env.getProperty("orcl.devurl"));
		dataSource.setUsername(env.getProperty("orcl.username"));
		dataSource.setPassword(env.getProperty("orcl.password"));
		dataSource.setInitialSize(2);
		dataSource.setMaxTotal(10);
		return dataSource;
	}
	
	@Profile("production")
	@Bean(name="production")
	public BasicDataSource OracleSCdataSource()
	{
		System.out.println("Creating production database");
		BasicDataSource  dataSource = new BasicDataSource();
		dataSource.setDriverClassName(env.getProperty("orcl.driverClassName"));
		dataSource.setUrl(env.getProperty("orcl.producturl"));
		dataSource.setUsername(env.getProperty("orcl.username"));
		dataSource.setPassword(env.getProperty("orcl.password"));
		dataSource.setInitialSize(2);
		dataSource.setMaxTotal(10);
		return dataSource;
	}
	
}
