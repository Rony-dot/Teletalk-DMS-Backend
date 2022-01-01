package com.rony.oracleemployee.services;


import com.rony.oracleemployee.exception.InvalidTokenException;
import com.rony.oracleemployee.model.User;

public interface TokenService {

    void validateToken(String jwtToken) throws InvalidTokenException, InvalidTokenException;

    void generateToken(User userMdoel);
}
