package com.sorufidani.app.model;

import lombok.Data;

@Data
public class User {
    private int id;
    private int role_id;
    private String name;
    private String surname;
    private String mail;
    private String password;
    private String country_code;
    private String phone;
    private String birthday;
    private String hash;

    public User(String mail, String name, String password, int role_id, String surname, int id, String country_code, String phone, String birthday, String hash) {
        this.id = id;
        this.role_id = role_id;
        this.name = name;
        this.surname = surname;
        this.mail = mail;
        this.password = password;
        this.country_code = country_code;
        this.phone = phone;
        this.birthday = birthday;
        this.hash = hash;
    }

}
