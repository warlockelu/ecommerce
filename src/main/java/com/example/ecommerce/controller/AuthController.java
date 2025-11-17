package com.example.ecommerce.controller;

import com.example.ecommerce.config.JwtAuthFilter;
import com.example.ecommerce.model.Account;
import com.example.ecommerce.service.AccountService;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AccountService accountService;

    public AuthController(AccountService accountService) {
        this.accountService = accountService;
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(
            @RequestParam("username") String username,
            @RequestParam("password") String password
    ) {
        try {
            Account account = accountService.register(username, password);
            return ResponseEntity.ok("Register Success: " + account.getUsername());
        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body("Register Failed: " + e.getMessage());
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(
            @RequestParam("username") String username,
            @RequestParam("password") String password
    ) {
        try {
            Account account = accountService.login(username, password);
            if (account != null) {
                String token = Jwts.builder()
                        .setSubject(account.getUsername())
                        .claim("role", "ROLE_USER")
                        .setIssuedAt(new Date())
                        .setExpiration(new Date(System.currentTimeMillis() + 24 * 60 * 60 * 1000)) // 1 day
                        .signWith(Keys.hmacShaKeyFor(JwtAuthFilter.SECRET_KEY.getBytes(StandardCharsets.UTF_8)))
                        .compact();

                return ResponseEntity.ok(Map.of("token", token));
            } else {
                return ResponseEntity
                        .status(HttpStatus.UNAUTHORIZED)
                        .body("Login Failed: username or password incorrect");
            }
        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body("Login Failed: " + e.getMessage());
        }
    }
}
