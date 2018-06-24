package com.hello.jpa;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.hello.domain.Customer;

public interface CustomerRepository extends MongoRepository<Customer, String> {

    public Customer findByFirstName(String firstName);
    public List<Customer> findByLastName(String lastName);
    public Customer findById(String id);

}