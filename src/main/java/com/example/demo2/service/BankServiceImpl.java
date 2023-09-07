package com.example.demo2.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;

import com.example.demo2.entity.BankAccount;
import com.example.demo2.entity.Customer;
import com.example.demo2.entity.TransferHistory;
import com.example.demo2.repository.BankAccountRepository;
import com.example.demo2.repository.CustomerRepository;
import com.example.demo2.repository.TransferHistoryRepository;

public class BankServiceImpl implements BankService{

	@Autowired
	private BankAccountRepository bankAccountRepository;
	
	@Autowired
	private CustomerRepository customerRepository;
	
	@Autowired
	private TransferHistoryRepository transferHistoryRepository;
	
	@Override
	public Map<String, Object> createBankAccount(BankAccount account) throws Exception  {  // saving new account for customer
		HashMap<String,Object> response = new HashMap<String, Object>();
		String customerId = account.getCustomerId();
		Optional<Customer> customer = customerRepository.findById(customerId);
		if(customer != null) {
			bankAccountRepository.save(account);		// saving account with inital deposit in database
			response.put("success", true);
			response.put("account", account);
		}else {
			response.put("success", false);
			response.put("errorMsg", "Customer hasn't registered yet");
		}
		
		return response;
	}
	
	public Map<String, Object> transferAmount(Map<String, Object> params) throws Exception {
		HashMap<String,Object> response = new HashMap<String, Object>();
		String senderAccountId = (String) params.get("senderAccountId");
		String receiverAccountId = (String) params.get("receiverAccountId");
		Long amountToTransfer = (Long) params.get("amount");
		BankAccount senderAccount = null;
		BankAccount receiverAccount = null;
		List<String> accountIds = new ArrayList<String> ();
		if(senderAccountId != null && receiverAccountId != null && !senderAccountId.equals(receiverAccountId)) { // checking if senderAccountId is not same as reciverAccountId
			accountIds.add(senderAccountId);
			accountIds.add(receiverAccountId);
			List<BankAccount> bankAccounts =   bankAccountRepository.findByIdIn(accountIds);
			for (BankAccount account : bankAccounts) {
				if(account.getId().equals(senderAccountId)) {
					senderAccount = account;
				}
				else if(account.getId().equals(receiverAccountId)) {
					receiverAccount = account;
				}
			}
			
			if(senderAccount != null && receiverAccount != null) {
				// check if senderAccount has enough balance for transaction
				if(senderAccount.getBalance() >= amountToTransfer) {
					// perform transaction
					senderAccount.setBalance(senderAccount.getBalance() - amountToTransfer);
					receiverAccount.setBalance(receiverAccount.getBalance() + amountToTransfer);
					bankAccountRepository.save(senderAccount);
					bankAccountRepository.save(receiverAccount);
					TransferHistory transactionHistory = new TransferHistory(senderAccount, receiverAccount, amountToTransfer);
					transferHistoryRepository.save(transactionHistory);
					response.put("success", true);
					response.put("transactionHistory", transactionHistory);
				}else {
					// not enough balance
					response.put("success", false);
					response.put("errorMsg", "You do not have enough credit in account");
				}
				
			}
		}else {
			response.put("success", false);
			response.put("errorMsg", "Transaction could not go through");
		}
		
		return response;
	}
	
	@Override
	public Map<String, Object> getAccountBalance(String accountId, String customerId) throws Exception {
		HashMap<String,Object> response = new HashMap<String, Object>();
		Optional<BankAccount> bankAccount =  bankAccountRepository.findById(accountId); // making sure account exists
		if(bankAccount != null) {
			if(bankAccount.get().getCustomerId().equals(customerId)) {   // Checking if the customerId in bank account matches with user who request to see balance
				response.put("balance", bankAccount.get().getBalance()); // returning balance
				response.put("success", true);				
			}else {
				response.put("success", false);
				response.put("errorMsg", "This account belongs to someone else");
			}
		}else {
			response.put("success", false);
		}
		return response;
	}
	
	@Override 
	public Map<String, Object> getAccountHistory(String id) throws Exception {
		HashMap<String,Object> response = new HashMap<String, Object>();
		List <TransferHistory> transferHistory = transferHistoryRepository.findBySenderAccountIdOrReceiverAccountIdOrderByTimeStamp(id, id); // fetching history order by transaction time
		if(transferHistory != null) {
			response.put("success", true);
			response.put("history", transferHistory);
		}else {
			response.put("success", false);
		}
		return response;
	}
}
