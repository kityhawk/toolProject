package com.kityhawk.lvds.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;

import com.kityhawk.lvds.model.Customer;

@Service
public interface CustomerDao {
	
	public Customer getCustByCtf(String cust_ctf);
	
	public Customer getCustByPhone(String cust_cphone);
	
	public List<Customer> getCustListByOrgId(String org_id);
	
	/**
	 * 检查身份证与电话号码的插入
	 */
	public int addCustomerByPhoneCtf(Customer customer) throws Exception;
	
	/**
	 * 检查电话号码的插入
	 */
	public int addCustomerByPhone(Customer customer) throws Exception;
	
	/**
	 * 检查身份证的插入
	 */
	public int addCustomerByCtf(Customer customer) throws Exception;
	
	/**
	 * 将客户加入某标签
	 */
	public int addCustomerToLabel(@Param("labelTableName") String labelTableName, @Param("cust_id") String cust_id) throws Exception;
	
}
