package com.sorufidani.app.config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    private static final String DATABASE_URL = "jdbc:mysql://localhost:3306/app"; // Veritabanı URL'si
    private static final String USERNAME = "root"; // Veritabanı kullanıcı adı
    private static final String PASSWORD = ""; // Veritabanı şifresi

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DATABASE_URL, USERNAME, PASSWORD);
    }
}
