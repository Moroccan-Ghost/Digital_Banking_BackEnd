package com.example.digital_banking_backend.exceptions;

public class BalanceNotSufficientException extends Exception{
    public BalanceNotSufficientException(String msg){
        super(msg);
    }
}
