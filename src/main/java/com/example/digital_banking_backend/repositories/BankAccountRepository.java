package com.example.digital_banking_backend.repositories;

import com.example.digital_banking_backend.entities.BankAccount;
import com.example.digital_banking_backend.entities.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BankAccountRepository extends JpaRepository<BankAccount,String> {
}
