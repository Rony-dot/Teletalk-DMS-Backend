package com.rony.oracleemployee.controller;


import com.rony.oracleemployee.dtos.request.UserInfoDto;
import com.rony.oracleemployee.model.User;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Validated
@RequestMapping("/users")
public interface UserController {

    @PostMapping("/add")
    ResponseEntity<Void> add(@Valid @RequestBody UserInfoDto userInfoDto);

    @GetMapping("/{id}")
    ResponseEntity<User> getById(@PathVariable long id);

    @GetMapping("/all")
    ResponseEntity<List<User>> getAll();

    @DeleteMapping("/{id}")
    ResponseEntity<Void> deleteById(@PathVariable long id);

    @PutMapping("/{id}")
    ResponseEntity<Void> update(@Valid @RequestBody User user, @PathVariable long id);
}
