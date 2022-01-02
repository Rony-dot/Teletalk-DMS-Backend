package com.rony.oracleemployee.controller;

import com.rony.oracleemployee.model.Customer;
import com.rony.oracleemployee.repository.CustomerRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/customers")
@AllArgsConstructor
public class CustomerController {
    private CustomerRepository customerRepository;

    @GetMapping("")
    public ResponseEntity<List<Customer>> getAll(){
        return  ResponseEntity.ok(customerRepository.findAll());
    }
}
