package com.example.demo2;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.example.demo2.controller.AppController;
import com.example.demo2.entity.BankAccount;
import com.example.demo2.service.BankService;

class CreateAccountTest {

	private BankService bankService;
	private AppController appController;
	@BeforeEach
	public void setUp() {
		bankService = mock(BankService.class);
		appController = new AppController(bankService);

	}

	@Test
	public void testNewAccount_Success() throws Exception {

		BankAccount account = new BankAccount();
		account.setCustomerId("1");
		when(bankService.createBankAccount(account)).thenReturn(getSuccessResponse());

		ResponseEntity<Object> response = appController.newAccount(account);
		assertEquals(HttpStatus.OK, response.getStatusCode());

	}

	@Test
	public void testNewAccount_Failure() throws Exception {
		
		BankAccount account = new BankAccount();
		account.setCustomerId("2");
		when(bankService.createBankAccount(account)).thenReturn(getErrorResponse());
		
		ResponseEntity<Object> response = appController.newAccount(account);
		assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
	}

	private Map<String, Object> getSuccessResponse() {
        Map<String, Object> response = new HashMap<>();
        response.put("success", true);
        response.put("account", new BankAccount()); // You can populate this with relevant data
        return response;
    }

    private Map<String, Object> getErrorResponse() {
        Map<String, Object> response = new HashMap<>();
        response.put("success", false);
        response.put("error", "Some error message");
        return response;
    }
}
