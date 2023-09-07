package com.example.demo2;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.example.demo2.controller.AppController;
import com.example.demo2.entity.TransferHistory;
import com.example.demo2.repository.TransferHistoryRepository;
import com.example.demo2.service.BankService;

class TransactionHistoryTest {

	private AppController appController;
	private BankService bankService;
    private TransferHistoryRepository transferHistoryRepository;

	@BeforeEach
    public void setUp() {
        bankService = mock(BankService.class);
        transferHistoryRepository = mock(TransferHistoryRepository.class);
        appController = new AppController(bankService);
    }

	 @Test
	    public void testGetAccountHistory_Success() throws Exception {
	        // Arrange
	        String accountId = "1";
	        List<TransferHistory> historyList = new ArrayList<>();

	        when(transferHistoryRepository.findBySenderAccountIdOrReceiverAccountIdOrderByTimeStamp(accountId, accountId))
	            .thenReturn(historyList);

	        when(bankService.getAccountHistory(accountId)).thenReturn(getSuccessResponse(historyList));

	        ResponseEntity<Object> response = appController.getAccountHistory(accountId);

	        assertEquals(HttpStatus.OK, response.getStatusCode());
	        Map<String, Object> responseBody = (Map<String, Object>) response.getBody();
	        assertTrue((boolean) responseBody.get("success"));
	       
	    }

	    @Test
	    public void testGetAccountHistory_NoHistory() throws Exception {
	        String accountId = "1";

	        when(transferHistoryRepository.findBySenderAccountIdOrReceiverAccountIdOrderByTimeStamp(accountId, accountId))
	            .thenReturn(null);

	        ResponseEntity<Object> response = appController.getAccountHistory(accountId);

	        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
	        Map<String, Object> responseBody = (Map<String, Object>) response.getBody();
	        assertFalse((boolean) responseBody.get("success"));
	    }

	    private Map<String, Object> getSuccessResponse(List<TransferHistory> historyList) {
	        Map<String, Object> response = new HashMap<>();
	        response.put("success", true);
	        response.put("history", historyList);
	        return response;
	    }
	

}
