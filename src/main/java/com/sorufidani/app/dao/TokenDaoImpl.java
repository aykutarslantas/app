package com.sorufidani.app.dao;

import com.sorufidani.app.config.DatabaseConnection;
import com.sorufidani.app.model.Token;

import java.sql.*;
import java.util.List;

public class TokenDaoImpl implements TokenDao {

    private int userId;
    private String loginType;

    @Override
    public Token get(int id) throws SQLException {

        Token token = null;
        String query = "SELECT * FROM tokens WHERE id = ?";

        try (
                Connection connection = DatabaseConnection.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(query)
        ) {
            preparedStatement.setInt(1, id);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    int userId = resultSet.getInt("id");
                    String secret_key = resultSet.getString("secret_key");
                    String login_type = resultSet.getString("login_type");
                    String headers = resultSet.getString("headers");
                    token = new Token(secret_key, userId, login_type, headers);
                }
            }
        }

        return token;
    }

    @Override
    public List<Token> getAll() throws SQLException {
        return null;
    }

    @Override
    public int save(Token token) throws SQLException {
        return 0;
    }

    @Override
    public int insert(Token token) throws SQLException {
        String query = "INSERT INTO tokens (secret_key, user_id, login_type, headers) VALUES (?,?,?,?)";
        try (
                Connection connection = DatabaseConnection.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)
        ) {
            preparedStatement.setString(1, String.valueOf(token.getSecret_key()));
            preparedStatement.setInt(2, token.getUser_id());
            preparedStatement.setString(3, token.getLogin_type());
            preparedStatement.setString(4, token.getHeaders());

            int rowsAffected = preparedStatement.executeUpdate();

            if (rowsAffected > 0) {
                try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        return generatedKeys.getInt(1);
                    }
                }
            }
        }
        return 0;
    }

    @Override
    public int update(Token token) throws SQLException {
        return 0;
    }

    @Override
    public int delete(Token token) throws SQLException {
        return 0;
    }


    public int getUserTokenWithMail(String mail, String login_type) {
        loginType = login_type;

        String query = "SELECT t.id FROM tokens t, users u WHERE u.mail = ? AND t.login_type = ? AND t.user_id = u.id AND t.is_active = 1";
        try (
                Connection connection = DatabaseConnection.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(query)
        ) {
            preparedStatement.setString(1, mail);
            preparedStatement.setString(2, login_type);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getInt("id");
                } else {
                    return 0;
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void updateTokenStatus(int lastLoginId) {
        String query = "UPDATE tokens SET is_active = 0 WHERE id = ?";
        try (
                Connection connection = DatabaseConnection.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(query)
        ) {
            preparedStatement.setInt(1, lastLoginId);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public int getUserLoginId(int id) {

        String query = "SELECT id FROM tokens t WHERE id = ? AND is_active = 1";
        try (
                Connection connection = DatabaseConnection.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(query)
        ) {
            preparedStatement.setInt(1, id);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getInt("id");
                } else {
                    return 0;
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
