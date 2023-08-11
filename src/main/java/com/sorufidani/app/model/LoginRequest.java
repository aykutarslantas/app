package com.sorufidani.app.model;

import lombok.Data;

@Data
public class LoginRequest {
    private String mail;
    private String password;
    private String type;

    public LoginRequest(String mail, String password, String type) {
        this.mail = mail;
        this.password = password;
        this.type = type;
    }
}