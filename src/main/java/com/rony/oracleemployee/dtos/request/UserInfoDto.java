package com.rony.oracleemployee.dtos.request;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.List;

@Data
public class UserInfoDto {

    private long id;

    @NotBlank(message = "name cannot be null")
    @Size(min = 3, max = 35, message
            = "Name must be between 3 and 30 characters")
    private String name;
    private int age;

    @NotBlank(message = "username is required")
    private String username;
    private List<String> roles;

    @Email(message = "email invalid")
    @NotBlank(message = "email cannot be empty")
    private String email;

    @NotBlank(message = "password is required")
    private String password;
    private String homeTown;
    private String countryCode;
    private String mobile;

    @NotBlank(message = "salutation is required")
    private String salutation;

    @NotBlank(message = "gender is required")
    private String gender;
    private String dateOfBirth;
    private String jwtToken;

    public UserInfoDto() {
    }

}
