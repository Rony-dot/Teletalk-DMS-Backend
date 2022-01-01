package com.rony.oracleemployee.controller.controllersimpl;

import com.rony.oracleemployee.controller.HomeController;
import com.rony.oracleemployee.dtos.request.UserInfoDto;
import com.rony.oracleemployee.dtos.request.UserLoginDto;
import com.rony.oracleemployee.services.UserService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class HomeControllerImpl implements HomeController {

    @Autowired
    private UserService userService;

    @Override
    public ResponseEntity<UserInfoDto> login(UserLoginDto userLoginDto) {
        System.out.println("username "+userLoginDto.getUsername());
        System.out.println("password "+userLoginDto.getPassword());

        return ResponseEntity.ok(userService.loginUser(userLoginDto));
    }

    @Override
    public ResponseEntity<Void> register(UserInfoDto userInfoDto) {
        userService.addUser(userInfoDto);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/demo")
    ResponseEntity<Void> demoPage(){
        return ResponseEntity.ok().build();
    }
}
