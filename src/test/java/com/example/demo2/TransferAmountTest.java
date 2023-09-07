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
import com.example.demo2.entity.TransferHistory;
import com.example.demo2.service.BankService;

class TransferAmountTest {

	private BankService bankService;
	private AppController appController;
	
	@BeforeEach
	public void setUp() {
		bankService = mock(BankService.class);
		appController = new AppController(bankService);

	}
	
	@Test
	public void testTransfer_Success() throws Exception {

		Map<String, Object> params = new HashMap<>();
		params.put("senderAccountId", "1");
		params.put("receiverAccountId", "2");
		params.put("amount", 100L); // Use Long for the amount

		when(bankService.transferAmount(params)).thenReturn(getSuccessResponse());

		ResponseEntity<Object> response = appController.transfer(params);

		assertEquals(HttpStatus.OK, response.getStatusCode());
		Map<String, Object> responseBody = (Map<String, Object>) response.getBody();
		assertTrue((boolean) responseBody.get("success"));
	}

	@Test
	public void testTransfer_Failure() throws Exception {

		Map<String, Object> params = new HashMap<>();
		params.put("senderAccountId", "sender123");
		params.put("receiverAccountId", "sender123"); // Same sender and receiver for failure scenario
		params.put("amount", 100L);

		when(bankService.transferAmount(params)).thenReturn(getErrorResponse());

		ResponseEntity<Object> response = appController.transfer(params);

		assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
		Map<String, Object> responseBody = (Map<String, Object>) response.getBody();
		assertFalse((boolean) responseBody.get("success"));

	}

	 private Map<String, Object> getSuccessResponse() {
	        Map<String, Object> response = new HashMap<>();
	        response.put("success", true);
	        response.put("transactionHistory", new TransferHistory()); // You can populate this with relevant data
	        return response;
	    }

	    private Map<String, Object> getErrorResponse() {
	        Map<String, Object> response = new HashMap<>();
	        response.put("success", false);
	        response.put("errorMsg", "Some error message");
	        return response;
	    }
}
