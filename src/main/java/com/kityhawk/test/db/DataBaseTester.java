package com.kityhawk.test.db;

import static org.junit.Assert.assertNotNull;

import java.util.List;

import org.apache.commons.dbcp2.BasicDataSource;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes=DataBaseConfig.class)
//@ActiveProfiles("production")
public class DataBaseTester {
	
	@Autowired
	@Qualifier("development")
	private BasicDataSource DB_a;
	
	@Autowired
	@Qualifier("production")
	private BasicDataSource DB_b;
	
	
	@Test
	public void bdsNotBeNull()
	{
		JdbcTemplate jdbc_a = new JdbcTemplate(DB_a);
		JdbcTemplate jdbc_b = new JdbcTemplate(DB_b);
		String sql = "select count(*) from customer";
		List<Integer> result =  jdbc_a.queryForList(sql, Integer.class);
		System.out.println("查询开发库客户数为:"+result.get(0));
		result =  jdbc_b.queryForList(sql, Integer.class);
		System.out.println("查询培训库客户数为:"+result.get(0));
		assertNotNull(DB_b);
	}

}
