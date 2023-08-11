package com.sorufidani.app.model;

import lombok.Data;

@Data
public class Question {
    private String img;
    private String text;

    private Question(String img, String text){
        this.img = img;
        this.text = text;
    }
}
