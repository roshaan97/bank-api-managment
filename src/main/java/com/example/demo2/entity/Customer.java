package com.example.demo2.entity;

import org.springframework.data.annotation.Id;

public class Customer {

	@Id
	private String id;
	private String name;
	
	public Customer (String name) {
		this.name = name;
	}
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	
}
