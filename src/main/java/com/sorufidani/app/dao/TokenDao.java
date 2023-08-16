package com.sorufidani.app.dao;

import com.sorufidani.app.model.Token;

public interface TokenDao extends DAO<Token> {
    int getUserTokenWithMail(String mail, String login_type);

    void updateTokenStatus(int lastLoginId);

    int getUserLoginId(int id);
}
