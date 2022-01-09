package com.rony.oracleemployee.controller;

import com.rony.oracleemployee.model.Customer;
import com.rony.oracleemployee.repository.CustomerRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/customers")
@AllArgsConstructor
public class CustomerController {
    private CustomerRepository customerRepository;

    @GetMapping("")
    public ResponseEntity<List<Customer>> getAll(){
        return  ResponseEntity.ok(customerRepository.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Customer> getById(@PathVariable long id){
        Optional<Customer> customer = customerRepository.findById(id);
        return customer.map(ResponseEntity::ok).orElse(null);
    }

    @PutMapping("/{id}")
    ResponseEntity<Customer> update(@Valid @RequestBody Customer customer, @PathVariable long id){
        return ResponseEntity.ok(customerRepository.save(customer));
    }
}
