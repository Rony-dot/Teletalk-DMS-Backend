package com.rony.oracleemployee.controller;


import com.rony.oracleemployee.dtos.request.UserInfoDto;
import com.rony.oracleemployee.dtos.request.UserLoginDto;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;

@Validated
public interface HomeController {

    @PostMapping("/register")
    ResponseEntity<Void> register(@Valid @RequestBody UserInfoDto userInfoDto);

    @PostMapping("/login")
    ResponseEntity<UserInfoDto> login(@Valid @RequestBody UserLoginDto userLoginDto);


}
