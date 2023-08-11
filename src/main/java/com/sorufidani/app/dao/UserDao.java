package com.sorufidani.app.dao;

import com.sorufidani.app.model.User;

import java.security.NoSuchAlgorithmException;
import java.sql.*;

public interface UserDao extends DAO<User> {

    boolean getMail(String mail) throws SQLException;

    int getIdWithHash(String hash);

    void updatePassword(int userId,String password) throws NoSuchAlgorithmException;

    String getSecretKey(int userId);


}
