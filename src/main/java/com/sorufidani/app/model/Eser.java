package com.sorufidani.app.model;

import lombok.Data;

@Data
public class Eser {
    private int publisher_id;
    private String name;
    private String created_at;
    private String cover;


    public Eser(int publisher_id, String name, String created_at, String cover) {
        this.publisher_id = publisher_id;
        this.name = name;
        this.created_at = created_at;
        this.cover = cover;
    }
}
