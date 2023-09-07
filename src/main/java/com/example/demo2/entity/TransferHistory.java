package com.example.demo2.entity;

import java.time.LocalDateTime;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;

public class TransferHistory {
	
	@Id
	private String id;
	
	@DBRef
	private BankAccount senderAccount;
	
	@DBRef
	private BankAccount receiverAccount;
	
	private Double timeStamp = new Double(System.nanoTime() / 100000);
	private Long amount;
	
	
	public TransferHistory (BankAccount senderAccount, BankAccount receiverAccount, Long amount) {
		this.senderAccount = senderAccount;
		this.receiverAccount = receiverAccount;
		this.amount = amount;
	}
	
	public TransferHistory() {
		// TODO Auto-generated constructor stub
	}

	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	
	public BankAccount getSenderAccount() {
		return senderAccount;
	}
	public void setSenderAccount(BankAccount senderAccount) {
		this.senderAccount = senderAccount;
	}
	public BankAccount getReceiverAccount() {
		return receiverAccount;
	}
	public void setReceiverAccount(BankAccount receiverAccount) {
		this.receiverAccount = receiverAccount;
	}
	
	public Double getTimeStamp() {
		return timeStamp;
	}

	public void setTimeStamp(Double timeStamp) {
		this.timeStamp = timeStamp;
	}

	public Long getAmount() {
		return amount;
	}
	public void setAmount(Long amount) {
		this.amount = amount;
	}

	
}
