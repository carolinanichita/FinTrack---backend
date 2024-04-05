package com.bytebandits.fintrackbackend.controller;


import com.bytebandits.fintrackbackend.dto.AccountDTO;
import com.bytebandits.fintrackbackend.dto.AccountResponseDTO;
import com.bytebandits.fintrackbackend.model.Account;
import com.bytebandits.fintrackbackend.service.AccountService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/accounts")
public class AccountController {
    private final AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @PostMapping("/create")
    public ResponseEntity<String> createAccount(@RequestBody AccountDTO accountDTO) {
        Account account = accountService.createAccount(accountDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(account.getId().toString());
    }

    @GetMapping("/{userId}")
    public ResponseEntity<List<AccountResponseDTO>> getAccountNamesByUserId(@PathVariable UUID userId) {
        List<Account> accountsList = accountService.getAccountsByUserId(userId);
        List<AccountResponseDTO> accountResponse = new ArrayList<>();

        for (Account account : accountsList) {
            AccountResponseDTO response = new AccountResponseDTO(account.getId(), account.getAccountName());
            accountResponse.add(response);
        }
        return ResponseEntity.ok(accountResponse);
    }
}