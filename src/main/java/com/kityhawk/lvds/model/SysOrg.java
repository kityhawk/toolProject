package com.kityhawk.lvds.model;

public class SysOrg {
	
	private String org_id;			//ORG_ID
	private String org_name;	//ORG_NAME
	private String parent_org_id;	//PARENT_ORG_ID
	private String remark;		//REMARK
	private String rpt_seq;		//RPT_SEQ
	private String org_sname;		//ORG_SNAME
	
	public String getOrg_id() {
		return org_id;
	}
	public void setOrg_id(String org_id) {
		this.org_id = org_id;
	}
	public String getOrg_name() {
		return org_name;
	}
	public void setOrg_name(String org_name) {
		this.org_name = org_name;
	}
	public String getParent_org_id() {
		return parent_org_id;
	}
	public void setParent_org_id(String parent_org_id) {
		this.parent_org_id = parent_org_id;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getRpt_seq() {
		return rpt_seq;
	}
	public void setRpt_seq(String rpt_seq) {
		this.rpt_seq = rpt_seq;
	}
	public String getOrg_sname() {
		return org_sname;
	}
	public void setOrg_sname(String org_sname) {
		this.org_sname = org_sname;
	}
	
}
