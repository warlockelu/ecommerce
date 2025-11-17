package com.example.ecommerce.service;

import org.springframework.stereotype.Service;
import org.springframework.security.crypto.password.PasswordEncoder;
import com.example.ecommerce.repository.AccountRepository;
import com.example.ecommerce.model.Account;

@Service
public class AccountService {
    private final AccountRepository accountRepo;
    private final PasswordEncoder passwordEncoder;

    public AccountService(AccountRepository accountRepo, PasswordEncoder passwordEncoder) {
        this.accountRepo = accountRepo;
        this.passwordEncoder = passwordEncoder;
    }

    public Account register(String username, String rawPassword) {
        String encoded = passwordEncoder.encode(rawPassword);
        Account account = new Account(username, encoded);
        return accountRepo.save(account);
    }

    public Account login(String username, String rawPassword) {
        return accountRepo.findByUsername(username)
                .filter(acc -> passwordEncoder.matches(rawPassword, acc.getPassword()))
                .orElse(null);
    }
}
