package com.example.backend.controller;

import com.example.backend.dao.CustomerDao;
import com.example.backend.ds.Customer;
import com.example.backend.ds.Customers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class CustomerController {

    @Autowired
    private CustomerDao customerDao;

    @GetMapping("/customers")
    public Customers listCustomer(){
        return new Customers(customerDao.findAll().spliterator());
    }

    @PostMapping(value = "/customers",consumes = MediaType.APPLICATION_JSON_VALUE)
    public Customer createCustomer(@RequestBody Customer customer){
        return customerDao.save(customer);
    }

    @DeleteMapping("/customers/{id}")
    public ResponseEntity deleteCustomer(@PathVariable int id){
        if (customerDao.existsById(id)){
            customerDao.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        else{
            return ResponseEntity.notFound().build();
        }

    }


}
