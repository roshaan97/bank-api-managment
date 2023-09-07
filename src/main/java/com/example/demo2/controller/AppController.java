package com.example.demo2.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.demo2.entity.BankAccount;
import com.example.demo2.repository.BankAccountRepository;
import com.example.demo2.repository.CustomerRepository;
import com.example.demo2.repository.TransferHistoryRepository;
import com.example.demo2.service.BankService;


@RequestMapping("/app")
@org.springframework.web.bind.annotation.RestController
public class AppController {

	@Autowired
	private BankService bankService;
	
	public AppController(BankService bankService2) {
		// TODO Auto-generated constructor stub
	}

	@RequestMapping(value = "/new-account", method = RequestMethod.POST)
	public @ResponseBody ResponseEntity<Object> newAccount(@RequestBody BankAccount account) { // For creation of new bank Account. It will receive  customer Id and initial deposit in request body
		Map<String,Object> result = new HashMap<String, Object> ();
		try {
			result =  bankService.createBankAccount(account);			// calling service method to save new account in the database
			return new ResponseEntity<>(result, HttpStatus.OK);
		}catch (Exception ex) {
			result.put("success", false);
			result.put("error", ex.getMessage());
			return new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);	// sending bad request if encounter any issue during saving
		}
	}
	
	@RequestMapping(value = "/transfer", method = RequestMethod.POST)
	public @ResponseBody ResponseEntity<Object> transfer(@RequestBody Map<String, Object> params) { // For transfer of money. It will receive senderAccountId, receiver AccountId and amount to transfer
		Map<String,Object> result = new HashMap<String, Object> ();
		try {
			result =  bankService.transferAmount(params);			// calling service method to transfer amount 
			return new ResponseEntity<>(result, HttpStatus.OK);
		}catch (Exception ex) {
			result.put("success", false);
			result.put("error", ex.getMessage());
			return new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);	// sending bad request if encounter any issue during transfer
		}
	}
	
	@RequestMapping(value = "/account/balance/{accountId}/{customerId}", method = RequestMethod.GET)
	public @ResponseBody ResponseEntity<Object> getAccountBalance(@PathVariable("accountId") String accountId, @PathVariable("customerId") String customerId ) { // For fetching balance,
		Map<String,Object> result = new HashMap<String, Object> ();
		try {
			 result = bankService.getAccountBalance(accountId, customerId);
			return new ResponseEntity<>(result, HttpStatus.OK);
		}catch (Exception ex) {
			result.put("success", false);
			result.put("error", ex.getMessage());
			return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);	// sending bad request if encounter any issue during transfer
		}
	}
	
	@RequestMapping(value = "/account/history/{accountId}", method = RequestMethod.GET)
	public @ResponseBody ResponseEntity<Object> getAccountHistory(@PathVariable("accountId") String id) {
		Map<String,Object> result = new HashMap<String, Object> ();
		try {
			 result = bankService.getAccountHistory(id);
			return new ResponseEntity<>(result, HttpStatus.OK);
		}catch (Exception ex) {
			result.put("success", false);
			result.put("error", ex.getMessage());
			return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);	// sending bad request if encounter any issue during transfer
		}
	}
	
}
