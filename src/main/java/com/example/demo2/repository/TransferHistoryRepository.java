package com.example.demo2.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.example.demo2.entity.TransferHistory;

public interface TransferHistoryRepository extends MongoRepository <TransferHistory, String> {

	List<TransferHistory> findBySenderAccountIdOrReceiverAccountIdOrderByTimeStamp(String id, String id2);
}
