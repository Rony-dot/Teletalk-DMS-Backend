package com.rony.oracleemployee.controller;

import com.rony.oracleemployee.model.Country;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Validated
@RequestMapping("/countries")
public interface CountryController {

    @GetMapping("/all")
    ResponseEntity<List<Country>> getAll();

    @PostMapping("/add")
    ResponseEntity<Void> add(@Valid @RequestBody Country country);

    @GetMapping("/{id}")
    ResponseEntity<Country> getById(@PathVariable Long id);

    @PutMapping("/{id}")
    ResponseEntity<Void> update(@Valid @RequestBody Country country);

    @DeleteMapping("/{id}")
    ResponseEntity<Void> deleteById(@PathVariable long id);


}
