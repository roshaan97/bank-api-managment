package com.example.demo2.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.example.demo2.entity.Customer;

public interface CustomerRepository extends MongoRepository <Customer,String>{


}
