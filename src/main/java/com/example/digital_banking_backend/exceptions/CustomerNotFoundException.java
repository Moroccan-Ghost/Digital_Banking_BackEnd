package com.example.digital_banking_backend.exceptions;

public class CustomerNotFoundException extends Exception{
    public CustomerNotFoundException(String msg){
        super(msg);
    }
}
