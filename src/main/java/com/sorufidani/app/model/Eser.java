package com.sorufidani.app.model;

import lombok.Data;

@Data
public class Eser {
    private int user_id;
    private String name;
    private String created_at;
    private String cover;


    public Eser(int user_id, String name, String created_at, String cover) {
        this.user_id = user_id;
        this.name = name;
        this.created_at = created_at;
        this.cover = cover;
    }
}
