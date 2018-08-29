package com.kityhawk.test.db;

import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class DataBaseService 
{
	public Integer getOrgNameById(JdbcTemplate jdbcTemplate)
	{
		  String sql = "select count(*) from customer";
		  List<Integer> result =  jdbcTemplate.queryForList(sql, Integer.class);
		  if(result.size() == 0) return 0;
		  else return result.get(0);
	}

}
