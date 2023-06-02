package com.example.digital_banking_backend.dtos;

import com.example.digital_banking_backend.enums.AccountStatus;
import lombok.Data;

import java.util.Date;

@Data
public  class SavingBankAccountDTO extends BankAccountDTO{

    private String id;
    private double balance;
    private Date createdAt;
    private AccountStatus status;
    private String currency;
    private CustomerDTO customerDTO;
    private double interestRate;
}
