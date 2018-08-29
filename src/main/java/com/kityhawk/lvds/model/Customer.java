package com.kityhawk.lvds.model;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;


/**客户基本信息
 * @author Administrator
 *
 */
public class Customer
{	
	private String cust_id;					//主键:客户id
	private String org_id;						//网点id:该客户所属网点
	private String org_name;				//机构名称
	private String grid_id;					//网格id:客户所属网格
	private String grid_name;				//网格名称
	private String manger_id;				//经理id:客户所属经理
	private String cust_cphone;			//客户电话
	private String cust_cphone2;		//客户电话
	private String cust_ctf;					//客户身份证号码
	private String cust_name;				//客户姓名
	private Integer cust_gender;			//客户性别:1-男 2-女
	private Integer cust_prop;				//客户类型
	private String other_prop;				//标签
	private String cust_addr;				//地址
	private Integer bt_post;				//是否邮政客户:1-是 2-否
	private Integer has_card;	//是否有卡（邮政卡）:1-是 2-否
	private Integer is_vip;	//是否高价值客户:1-是 2-否
	private Date birthday;		//客户生日
	private Integer fm_cond;	//家庭情况:
	private Integer annual;		//年收入:
	private Integer main_bank;		//主要开户行:1-农行 2-邮储 
	private Integer job_des;		//工作性质:1-国企 2-外企 3-私企 4-公职单位 5-其他
	private Integer duty;		//职务 1-白领 2-公务员 3-教师 4-销售 5-农民 6-商人 7-务工 8-学生 9 退休人员 0 其他 
	private Integer importance_degree;		//重要程度 1-高价值客户 2-价值客户 3-普通客户
	private String relation_time;		//方便通话时间段
	private Integer deleted;		//停用标记:1-已停用 0-正常
	private Integer type_way;		//录入方式:1-pc 2-手机
	private List<String> resvered;		//
	private String resvered1;			//预留字段1
	private String resvered2;			//预留字段2
	private String resvered3;			//预留字段3
	private String leader_identity;			//支局长标识
	private BigDecimal cdm_bal;		//活期资产
	private BigDecimal fix_bal;			//定期资产
	private BigDecimal pol_bal;			//保险资产
	private BigDecimal finance_bal;	//理财资产
	private BigDecimal fund_bal;			//基金资产
	private BigDecimal gov_debt_bal;		//国债资产
	private String fix_number;		//定期账号
	private BigDecimal balance;		//余额
	private Date expiration_time;		//到期时间
	private String pol_number;		//保单号
	private BigDecimal pol_balance;		//保额
	private String  pol_type;		//险种
	private Date pol_expirationTime;		//保险到期时间
	private String reson;						//未导入原因
	private  BigDecimal sum_bal;	//总资产
	private String office_id;			//客户组织机构ID
	private String thebirthday;		//客户生日月日
	private String thebirthyear;		//客户生日年份
    private Integer receive_sms;
	
	public Integer getReceive_sms() {
		return receive_sms;
	}
	public void setReceive_sms(Integer receive_sms) {
		this.receive_sms = receive_sms;
	}
	public BigDecimal getSum_bal() {
		return sum_bal;
	}
	public void setSum_bal(BigDecimal sum_bal) {
		this.sum_bal = sum_bal;
	}
	public String getReson() {
		return reson;
	}
	public void setReson(String reson) {
		this.reson = reson;
	}
	public String getFix_number() {
		return fix_number;
	}
	public void setFix_number(String fix_number) {
		this.fix_number = fix_number;
	}
	public BigDecimal getBalance() {
		return balance;
	}
	public void setBalance(BigDecimal balance) {
		this.balance = balance;
	}
	public Date getExpiration_time() {
		return expiration_time;
	}
	public void setExpiration_time(Date expiration_time) {
		this.expiration_time = expiration_time;
	}
	public String getPol_number() {
		return pol_number;
	}
	public void setPol_number(String pol_number) {
		this.pol_number = pol_number;
	}
	public BigDecimal getPol_balance() {
		return pol_balance;
	}
	public void setPol_balance(BigDecimal pol_balance) {
		this.pol_balance = pol_balance;
	}
	public String getPol_type() {
		return pol_type;
	}
	public void setPol_type(String pol_type) {
		this.pol_type = pol_type;
	}
	public Date getPol_expirationTime() {
		return pol_expirationTime;
	}
	public void setPol_expirationTime(Date pol_expirationTime) {
		this.pol_expirationTime = pol_expirationTime;
	}
	public BigDecimal getCdm_bal() {
		return cdm_bal;
	}
	public void setCdm_bal(BigDecimal cdm_bal) {
		this.cdm_bal = cdm_bal;
	}
	public BigDecimal getFix_bal() {
		return fix_bal;
	}
	public void setFix_bal(BigDecimal fix_bal) {
		this.fix_bal = fix_bal;
	}
	public BigDecimal getPol_bal() {
		return pol_bal;
	}
	public void setPol_bal(BigDecimal pol_bal) {
		this.pol_bal = pol_bal;
	}
	public BigDecimal getFinance_bal() {
		return finance_bal;
	}
	public void setFinance_bal(BigDecimal finance_bal) {
		this.finance_bal = finance_bal;
	}
	public BigDecimal getFund_bal() {
		return fund_bal;
	}
	public void setFund_bal(BigDecimal fund_bal) {
		this.fund_bal = fund_bal;
	}
	public BigDecimal getGov_debt_bal() {
		return gov_debt_bal;
	}
	public void setGov_debt_bal(BigDecimal gov_debt_bal) {
		this.gov_debt_bal = gov_debt_bal;
	}
	public String getOrg_name() {
		return org_name;
	}
	public void setOrg_name(String org_name) {
		this.org_name = org_name;
	}
	public String getResvered1() {
		return resvered1;
	}
	public void setResvered1(String resvered1) {
		this.resvered1 = resvered1;
	}
	public String getResvered2() {
		return resvered2;
	}
	public void setResvered2(String resvered2) {
		this.resvered2 = resvered2;
	}
	public String getResvered3() {
		return resvered3;
	}
	public void setResvered3(String resvered3) {
		this.resvered3 = resvered3;
	}
	public String getCust_id() {
		return cust_id;
	}
	public void setCust_id(String cust_id) {
		this.cust_id = cust_id;
	}
	public String getOrg_id() {
		return org_id;
	}
	public void setOrg_id(String org_id) {
		this.org_id = org_id;
	}
	public String getGrid_id() {
		return grid_id;
	}
	public void setGrid_id(String grid_id) {
		this.grid_id = grid_id;
	}
	public String getGrid_name() {
		return grid_name;
	}
	public void setGrid_name(String grid_name) {
		this.grid_name = grid_name;
	}
	public String getManger_id() {
		return manger_id;
	}
	public void setManger_id(String manger_id) {
		this.manger_id = manger_id;
	}
	public String getCust_cphone() {
		return cust_cphone;
	}
	public void setCust_cphone(String cust_cphone) {
		this.cust_cphone = cust_cphone;
	}
	public String getCust_ctf() {
		return cust_ctf;
	}
	public void setCust_ctf(String cust_ctf) {
		this.cust_ctf = cust_ctf;
	}
	public String getCust_name() {
		return cust_name;
	}
	public void setCust_name(String cust_name) {
		this.cust_name = cust_name;
	}
	public Integer getCust_gender() {
		return cust_gender;
	}
	public void setCust_gender(Integer cust_gender) {
		this.cust_gender = cust_gender;
	}
	public Integer getCust_prop() {
		return cust_prop;
	}
	public void setCust_prop(Integer cust_prop) {
		this.cust_prop = cust_prop;
	}
	public String getOther_prop() {
		return other_prop;
	}
	public void setOther_prop(String other_prop) {
		this.other_prop = other_prop;
	}
	public String getCust_addr() {
		return cust_addr;
	}
	public void setCust_addr(String cust_addr) {
		this.cust_addr = cust_addr;
	}
	public Integer getBt_post() {
		return bt_post;
	}
	public void setBt_post(Integer bt_post) {
		this.bt_post = bt_post;
	}
	public Integer getHas_card() {
		return has_card;
	}
	public void setHas_card(Integer has_card) {
		this.has_card = has_card;
	}
	public Integer getIs_vip() {
		return is_vip;
	}
	public void setIs_vip(Integer is_vip) {
		this.is_vip = is_vip;
	}
	public Date getBirthday() {
		return birthday;
	}
	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}
	public Integer getFm_cond() {
		return fm_cond;
	}
	public void setFm_cond(Integer fm_cond) {
		this.fm_cond = fm_cond;
	}
	public Integer getAnnual() {
		return annual;
	}
	public void setAnnual(Integer annual) {
		this.annual = annual;
	}
	public Integer getMain_bank() {
		return main_bank;
	}
	public void setMain_bank(Integer main_bank) {
		this.main_bank = main_bank;
	}
	public Integer getDuty() {
		return duty;
	}
	public void setDuty(Integer duty) {
		this.duty = duty;
	}
	
	public Integer getJob_des() {
		return job_des;
	}
	public void setJob_des(Integer job_des) {
		this.job_des = job_des;
	}
	public Integer getImportance_degree() {
		return importance_degree;
	}
	public void setImportance_degree(Integer importance_degree) {
		this.importance_degree = importance_degree;
	}
	public String getRelation_time() {
		return relation_time;
	}
	public void setRelation_time(String relation_time) {
		this.relation_time = relation_time;
	}
	public Integer getDeleted() {
		return deleted;
	}
	public void setDeleted(Integer deleted) {
		this.deleted = deleted;
	}
	public Integer getType_way() {
		return type_way;
	}
	public void setType_way(Integer type_way) {
		this.type_way = type_way;
	}
	public List<String> getResvered() {
		return resvered;
	}
	public void setResvered(List<String> resvered) {
		this.resvered = resvered;
	}

	public String getLeader_identity() {
		return leader_identity;
	}
	public void setLeader_identity(String leader_identity) {
		this.leader_identity = leader_identity;
	}

	public String getCust_cphone2() {
		return cust_cphone2;
	}
	public void setCust_cphone2(String cust_cphone2) {
		this.cust_cphone2 = cust_cphone2;
	}
	public String getOffice_id() {
		return office_id;
	}
	public void setOffice_id(String office_id) {
		this.office_id = office_id;
	}
	public String getThebirthday() {
		return thebirthday;
	}
	public void setThebirthday(String thebirthday) {
		this.thebirthday = thebirthday;
	}
	public String getThebirthyear() {
		return thebirthyear;
	}
	public void setThebirthyear(String thebirthyear) {
		this.thebirthyear = thebirthyear;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((cust_ctf == null) ? 0 : cust_ctf.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		Customer other = (Customer) obj;
		if (cust_ctf == null) {
			if (other.cust_ctf != null)
				return false;
		} else if (!cust_ctf.equals(other.cust_ctf))
			return false;
		return true;
	}


}
