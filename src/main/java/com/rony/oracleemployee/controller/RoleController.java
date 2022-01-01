package com.rony.oracleemployee.controller;


import com.rony.oracleemployee.model.Role;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Validated
@RequestMapping("/roles")
public interface RoleController {

    @PostMapping("/add")
    ResponseEntity<Void> add(@Valid @RequestBody Role role);

    @GetMapping("/{id}")
    ResponseEntity<Role> getById(@PathVariable long id);

    @GetMapping("/all")
    ResponseEntity<List<Role>> getAll();

    @DeleteMapping("/{id}")
    ResponseEntity<Void> deleteById(@PathVariable long id);

    @PutMapping("/{id}")
    ResponseEntity<Void> update(@Valid @RequestBody Role role, @PathVariable long id);
}
