package com.rony.oracleemployee.dtos.request;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
public class UserLoginDto {

    @NotBlank(message = "name cannot be null")
    @Size(min = 3, max = 35, message
            = "Name must be between 3 and 30 characters")
    private String username;
    private String password;
}
