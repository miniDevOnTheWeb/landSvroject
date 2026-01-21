package com.LandSV.landSV.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "verification_codes")
public class VerificationCode {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "code")
    private String code;

    @Column(name = "email")
    private String email;

    @Column(name = "expiration")
    private LocalDateTime expiration;

    public VerificationCode () {}

    public VerificationCode (String code, String email) {
        this.code = code;
        this.email = email;
        this.expiration = LocalDateTime.now().plusMinutes(10);
    }

    public Long getId() {
        return id;
    }
    public String getEmail() {
        return email;
    }
    public LocalDateTime getExpiration() {
        return expiration;
    }
    public String getCode() {
        return code;
    }
}
