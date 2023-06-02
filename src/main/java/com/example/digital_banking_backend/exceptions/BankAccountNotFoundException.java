package com.example.digital_banking_backend.exceptions;

public class BankAccountNotFoundException extends Exception{
    public BankAccountNotFoundException(String msg){
        super(msg);
    }
}
