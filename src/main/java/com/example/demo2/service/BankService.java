package com.example.demo2.service;

import java.util.Map;

import org.springframework.stereotype.Service;

import com.example.demo2.entity.BankAccount;

@Service
public interface BankService {

	public Map<String, Object> createBankAccount(BankAccount account) throws Exception;
	
	public Map<String, Object> transferAmount(Map<String, Object> params) throws Exception;
	
	public Map<String, Object> getAccountBalance(String accountId, String customerId) throws Exception;
	
	public Map<String, Object> getAccountHistory(String id) throws Exception;
}
