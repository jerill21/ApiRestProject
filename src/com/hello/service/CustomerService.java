package com.hello.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hello.domain.Customer;
import com.hello.jpa.CustomerRepository;

/*
 * Sample service to demonstrate what the API would use to get things done
 */
@Service
public class CustomerService {

    private static final Logger log = LoggerFactory.getLogger(CustomerService.class);

    @Autowired
    private CustomerRepository customerRepository;

    public CustomerService() {
    }

    public Customer createCustomer(Customer customer) {
        return customerRepository.save(customer);
    }

    public Customer getCustomerByFirstName(String firstName) {
        return customerRepository.findByFirstName(firstName);
    }
    
    public Customer getCustomerById(String id) {
    	return customerRepository.findById(id);
    }

    public void updateCustomer(Customer customer) {
    	customerRepository.save(customer);
    }

    public void deleteCustomer(String id) {
    	customerRepository.delete(id);
    }

//    //http://goo.gl/7fxvVf
//    public Page<Hotel> getAllHotels(Integer page, Integer size) {
//        Page pageOfHotels = hotelRepository.findAll(new PageRequest(page, size));
//        // example of adding to the /metrics
//        if (size > 50) {
//            counterService.increment("Khoubyari.HotelService.getAll.largePayload");
//        }
//        return pageOfHotels;
//    }
}
