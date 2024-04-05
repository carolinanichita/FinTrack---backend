package com.bytebandits.fintrackbackend.service;

import com.bytebandits.fintrackbackend.dto.AccountDTO;
import com.bytebandits.fintrackbackend.model.Account;
import com.bytebandits.fintrackbackend.model.User;
import com.bytebandits.fintrackbackend.repository.AccountRepository;
import com.bytebandits.fintrackbackend.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class AccountService {
    @Autowired
    private final AccountRepository accountRepository;
    @Autowired
    private final UserRepository userRepository;


    public AccountService(AccountRepository accountRepository, UserRepository userRepository) {
        this.accountRepository = accountRepository;
        this.userRepository = userRepository;
    }

    public Account createAccount(AccountDTO accountDTO) {
        User user = userRepository.findById(accountDTO.userId())
                .orElseThrow(() -> new EntityNotFoundException("User not found"));

        Account account = new Account();
        account.setAccountName(accountDTO.accountName());
        account.setAmount(accountDTO.amount());
        account.setDescription(accountDTO.description());
        account.setUser(user);
        accountRepository.save(account);

        return account;
    }

    public List<Account> getAllAccounts() {
        return accountRepository.findAll();
    }

    public List<Account> getAccountsByUserId(UUID userId) {
        return accountRepository.findByUserId(userId);
    }
}