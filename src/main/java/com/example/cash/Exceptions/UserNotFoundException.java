package com.example.cash.Exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND) 
public class UserNotFoundException  extends RuntimeException {

	private static final long serialVersionUID = -7347398488901816728L;

	public UserNotFoundException(long userId){
        super("User " + userId + " does not exist.");
    }
    
}
