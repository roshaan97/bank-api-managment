package com.example.demo2;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.example.demo2.controller.AppController;
import com.example.demo2.entity.BankAccount;
import com.example.demo2.repository.BankAccountRepository;
import com.example.demo2.service.BankService;

class GetBalanceTest {

	private AppController appController;
	private BankService bankService;
    private BankAccountRepository bankAccountRepository;
    
    @BeforeEach
    public void setUp() {
        bankService = mock(BankService.class);
        bankAccountRepository = mock(BankAccountRepository.class);
        appController = new AppController(bankService);
     
    }
    
    @Test
    public void testGetAccountBalance_Success() throws Exception {
      
        String accountId = "1";
        String customerId = "2";
        BankAccount bankAccount = new BankAccount();
        bankAccount.setId(accountId);
        bankAccount.setCustomerId(customerId);
        bankAccount.setBalance(100L);
        
        when(bankAccountRepository.findById(accountId)).thenReturn(Optional.of(bankAccount));
       
        when(bankService.getAccountBalance(accountId, customerId)).thenReturn(getSuccessResponse(bankAccount.getBalance()));

        ResponseEntity<Object> response = appController.getAccountBalance(accountId, customerId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        Map<String, Object> responseBody = (Map<String, Object>) response.getBody();
        assertTrue((boolean) responseBody.get("success"));
        assertEquals(100.0, responseBody.get("balance"));
    }

    @Test
    public void testGetAccountBalance_AccountNotFound() throws Exception {
        
        String accountId = "1";
        String customerId = "2";

        when(bankAccountRepository.findById(accountId)).thenReturn(Optional.empty());
  
        ResponseEntity<Object> response = appController.getAccountBalance(accountId, customerId);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        Map<String, Object> responseBody = (Map<String, Object>) response.getBody();
        assertFalse((boolean) responseBody.get("success"));
    }

    @Test
    public void testGetAccountBalance_WrongCustomerId() throws Exception {
        // Arrange
        String accountId = "1";
        String customerId = "2";
        String wrongCustomerId = "3";

        // Create a bank account with a different customerId
        BankAccount bankAccount = new BankAccount();
        bankAccount.setId(accountId);
        bankAccount.setCustomerId(wrongCustomerId);
        bankAccount.setBalance(100L);

        // Mock the behavior of the bankAccountRepository
        when(bankAccountRepository.findById(accountId)).thenReturn(Optional.of(bankAccount));

        // Act
        ResponseEntity<Object> response = appController.getAccountBalance(accountId, customerId);

        // Assert
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        Map<String, Object> responseBody = (Map<String, Object>) response.getBody();
        assertFalse((boolean) responseBody.get("success"));
    }

    private Map<String, Object> getSuccessResponse(Long balance) {
        Map<String, Object> response = new HashMap<>();
        response.put("balance", balance);
        response.put("success", true);
        return response;
    }
	

}
