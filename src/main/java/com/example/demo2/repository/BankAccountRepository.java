package com.example.demo2.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.example.demo2.entity.BankAccount;

public interface BankAccountRepository extends MongoRepository <BankAccount, String> {

	List<BankAccount> findByIdIn(List<String> ids);
}
