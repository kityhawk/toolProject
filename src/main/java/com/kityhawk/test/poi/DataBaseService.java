package com.kityhawk.test.poi;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class DataBaseService 
{
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	public String getOrgNameById(String org_id)
	{ 
		  Object [] args = new Object [] {org_id};
		  String sql = "select ORG_NAME from SYS_ORG where ORG_ID = ?";
		  List<String> result =  jdbcTemplate.queryForList(sql, String.class, args);
		  if(result.size() == 0) return "";
		  else return result.get(0);
	}
	
	public String getOrgIdByName(String org_name)
	{ 
		  Object [] args = new Object [] {org_name};
		  String sql = "select ORG_ID from SYS_ORG where ORG_NAME = ?";
		  List<String> result = jdbcTemplate.queryForList(sql, String.class, args);
		  if(result.size() == 0) return "";
		  else return result.get(0);
	}
	
	public String getRemarkById(String org_id)
	{ 
		  Object [] args = new Object [] {org_id};
		  String sql = "select REMARK from SYS_ORG where ORG_ID = ?";
		  List<String> result  =  jdbcTemplate.queryForList(sql, String.class, args);
		  if(result.size() == 0) return "";
		  else return result.get(0);
	}
	
	public void setSeq(String org_id, String seq)
	{ 
		String sql = "update SYS_ORG set RPT_SEQ = ? where ORG_ID = ?";
		this.jdbcTemplate.update(sql, seq, org_id);
	}
}
