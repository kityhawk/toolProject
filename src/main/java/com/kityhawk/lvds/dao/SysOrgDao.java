package com.kityhawk.lvds.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.kityhawk.lvds.model.SysOrg;

public interface SysOrgDao {
	
	//根据机构号获取机构
	public SysOrg getOrgNameById(String orgId);
	
	//根据机构号获取所有下级机构
	public List<SysOrg> getOrglByParentOrgId(String parentOrgId);
	
	//判断网点OrgId是否是userOrgId的管理范围下
	public int isOrgInRangeWd(@Param("wdOrgid") String wdOrgid, @Param("userOrgId") String userOrgId);
	
	//判断OrgId是否是userOrgId的管理范围下
	public int isOrgInRange(@Param("wdOrgid") String wdOrgid, @Param("userOrgId") String userOrgId);
	
	
}
