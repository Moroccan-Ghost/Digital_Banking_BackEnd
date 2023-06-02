package com.example.digital_banking_backend;

import com.example.digital_banking_backend.dtos.BankAccountDTO;
import com.example.digital_banking_backend.dtos.CurrentBankAccountDTO;
import com.example.digital_banking_backend.dtos.CustomerDTO;
import com.example.digital_banking_backend.dtos.SavingBankAccountDTO;
import com.example.digital_banking_backend.entities.*;
import com.example.digital_banking_backend.enums.AccountStatus;
import com.example.digital_banking_backend.enums.OperationType;
import com.example.digital_banking_backend.exceptions.BalanceNotSufficientException;
import com.example.digital_banking_backend.exceptions.BankAccountNotFoundException;
import com.example.digital_banking_backend.exceptions.CustomerNotFoundException;
import com.example.digital_banking_backend.repositories.AccountOperationRepository;
import com.example.digital_banking_backend.repositories.BankAccountRepository;
import com.example.digital_banking_backend.repositories.CustomerRepository;
import com.example.digital_banking_backend.services.BankAccountService;
import com.example.digital_banking_backend.services.BankService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Stream;

@SpringBootApplication
public class DigitalBankingBackEndApplication {

    public static void main(String[] args) {
        SpringApplication.run(DigitalBankingBackEndApplication.class, args);
    }
    @Bean
    CommandLineRunner commandLineRunner(BankAccountService bankAccountService){
        return arg -> {
            Stream.of("Aiman","Taha","Hadi").forEach(name->{
                CustomerDTO customer = new CustomerDTO();
                customer.setName(name);
                customer.setEmail(name+"@gmail.com");
                bankAccountService.saveCustomer(customer);
            });

            bankAccountService.listCustomers().forEach(customer->{
                try {
                    bankAccountService.saveCurrentBankAccount(Math.random()*10000,9000, customer.getId());
                    bankAccountService.saveSavingBankAccount(Math.random()*10000,5.5, customer.getId());

                } catch (CustomerNotFoundException e) {
                    e.printStackTrace();
                }
            });
            List<BankAccountDTO> bankAccounts =  bankAccountService.bankAccountList();
            for(BankAccountDTO bankAccount : bankAccounts){
                for (int i = 0; i < 10; i++) {
                    String accountId;
                    if(bankAccount instanceof SavingBankAccountDTO){
                        accountId = ((SavingBankAccountDTO) bankAccount).getId();
                    }else{
                        accountId = ((CurrentBankAccountDTO) bankAccount).getId();
                    }
                    bankAccountService.credit(accountId,10000+Math.random()*120000,"Credit");
                    bankAccountService.debit(accountId, 1000+Math.random()*9000,"Debit");
                }
            }
        };
    }

    //@Bean
    CommandLineRunner start(CustomerRepository customerRepository, BankAccountRepository bankAccountRepository, AccountOperationRepository accountOperationRepository){
        return arg -> {
            Stream.of("Hassan","Yassin","karam").forEach(name->{
                Customer customer = new Customer();
                customer.setName(name);
                customer.setEmail(name+"@gmail.com");
                customerRepository.save(customer);
            });

            customerRepository.findAll().forEach(cust->{
                CurrentAccount currentAccount = new CurrentAccount();
                currentAccount.setId(UUID.randomUUID().toString());
                currentAccount.setBalance(Math.random()*80000);
                currentAccount.setCreatedAt(new Date());
                currentAccount.setStatus(AccountStatus.CREATED);
                currentAccount.setCustomer(cust);
                currentAccount.setOverDraft(9000);
                currentAccount.setCurrency("DH");
                bankAccountRepository.save(currentAccount);

                SavingAccount savingAccount = new SavingAccount();
                savingAccount.setId(UUID.randomUUID().toString());
                savingAccount.setBalance(Math.random()*80000);
                savingAccount.setCreatedAt(new Date());
                savingAccount.setStatus(AccountStatus.CREATED);
                savingAccount.setCustomer(cust);
                savingAccount.setInterestRate(5.5);
                savingAccount.setCurrency("DH");
                bankAccountRepository.save(savingAccount);
            });

            bankAccountRepository.findAll().forEach(acc->{
                for (int i=0;i<10;i++){
                    AccountOperation accountOperation = new AccountOperation();
                    accountOperation.setOperationDate(new Date());
                    accountOperation.setAmount(Math.random()*12000);
                    accountOperation.setType(Math.random() > 0.5 ? OperationType.DEBIT : OperationType.CREDIT);
                    accountOperation.setBankAccount(acc);
                    accountOperationRepository.save(accountOperation);
                }
            });

        };
    }

}
