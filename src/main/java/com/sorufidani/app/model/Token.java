package com.sorufidani.app.model;

import lombok.Data;

@Data
public class Token {
    private String secret_key;
    private Integer user_id;
    private String login_type;
    private String headers;

    public Token(String secret_key, int user_id, String login_type, String headers) {
        this.secret_key = secret_key;
        this.user_id = user_id;
        this.login_type = login_type;
        this.headers = headers;
    }

}
