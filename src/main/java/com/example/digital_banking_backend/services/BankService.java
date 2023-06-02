package com.example.digital_banking_backend.services;

import com.example.digital_banking_backend.entities.BankAccount;
import com.example.digital_banking_backend.entities.CurrentAccount;
import com.example.digital_banking_backend.entities.SavingAccount;
import com.example.digital_banking_backend.repositories.BankAccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class BankService {
    @Autowired
    private BankAccountRepository bankAccountRepository;
    public void consulter(){
        BankAccount bankAccount = bankAccountRepository.findById("08d9a511-0ecd-4baa-9019-afe955d9cee1").orElse(null);
        if(bankAccount!=null) {
            System.out.println("**********************");
            System.out.println(bankAccount.getId());
            System.out.println(bankAccount.getBalance());
            System.out.println(bankAccount.getCustomer().getName());
            System.out.println(bankAccount.getClass().getSimpleName());
            if (bankAccount instanceof CurrentAccount) {
                System.out.println("OverDraft : " + ((CurrentAccount) bankAccount).getOverDraft());
            } else {
                System.out.println("interet : " + ((SavingAccount) bankAccount).getInterestRate());
            }
            System.out.println("/*************Operations************/");
            bankAccount.getAccountOperations().forEach(op -> {
                System.out.println(op.getType() + " " + op.getAmount() + " " + op.getOperationDate());
            });
        }
    }
}
