package com.sorufidani.app.model;

import lombok.Data;

@Data
public class CreateUserRequest {
    private String firstName;
    private String lastName;
    private String mail;
    private String password;
    private String birthDate;

    public CreateUserRequest(String firstName, String lastName, String mail, String password, String birthDate) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.mail = mail;
        this.password = password;
        this.birthDate = birthDate;
    }
}