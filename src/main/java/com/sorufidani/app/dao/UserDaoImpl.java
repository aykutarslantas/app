package com.sorufidani.app.dao;

import com.sorufidani.app.config.DatabaseConnection;
import com.sorufidani.app.config.GenerateSHA256InSpringBoot;
import com.sorufidani.app.model.User;
import org.springframework.stereotype.Repository;

import java.security.NoSuchAlgorithmException;
import java.sql.*;
import java.util.List;

@Repository
public class UserDaoImpl implements UserDao {
    @Override
    public User get(int id) throws SQLException {
        User user = null;
        String query = "SELECT * FROM users WHERE id = ?";

        try (Connection connection = DatabaseConnection.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, id);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    int userId = resultSet.getInt("id");
                    String mail = resultSet.getString("mail");
                    String name = resultSet.getString("name");
                    String surname = resultSet.getString("surname");
                    String password = "";
                    int role_id = resultSet.getInt("role_id");
                    String country_code = resultSet.getString("country_code");
                    String phone = resultSet.getString("phone");
                    String birthday = resultSet.getString("birthday");

                    user = new User(mail, name, password, role_id, surname, userId, country_code, phone, birthday, "");
                }
            }
        }

        return user;
    }

    @Override
    public List<User> getAll() throws SQLException {
        return null;
    }

    @Override
    public int save(User user) throws SQLException {
        return 0;
    }

    @Override
    public int insert(User user) throws SQLException {
        String query = "INSERT INTO users (role_id, name, surname, mail, password, country_code, phone, birthday,hash) " +
                "VALUES (?,?,?,?,?,?,?,?,?)";
        try (Connection connection = DatabaseConnection.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setInt(1, user.getRole_id());
            preparedStatement.setString(2, user.getName());
            preparedStatement.setString(3, user.getSurname());
            preparedStatement.setString(4, user.getMail());
            preparedStatement.setString(5, user.getPassword());
            preparedStatement.setString(6, user.getCountry_code());
            preparedStatement.setString(7, user.getPhone());
            preparedStatement.setString(8, user.getBirthday());
            preparedStatement.setString(9, user.getHash());

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
    public int update(User user) throws SQLException {
        String query = "UPDATE users SET name = ?, surname = ?, password = ?, birthday = ? WHERE id = ?";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            //todo: burda gelen değerler arzu edilen formattamı bakmak lazım
            preparedStatement.setString(1, user.getName());
            preparedStatement.setString(2, user.getSurname());
            preparedStatement.setString(3, user.getPassword());
            preparedStatement.setString(4, user.getBirthday());
            preparedStatement.setInt(5, user.getId());

            return preparedStatement.executeUpdate();
        }
    }

    @Override
    public int delete(User user) throws SQLException {
        return 0;
    }

    @Override
    public boolean getMail(String mail) throws SQLException {
        String query = "SELECT id FROM users WHERE mail = ?";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, mail);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return true;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public int getIdWithHash(String hash) {
        String query = "SELECT * FROM users WHERE hash = ?";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, hash);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getInt("id");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    @Override
    public void updatePassword(int userId, String password) throws NoSuchAlgorithmException {
        String encodedPassword = GenerateSHA256InSpringBoot.toHexString(GenerateSHA256InSpringBoot.getSHA(password));

        String query = "UPDATE users SET password = ? WHERE id = ?";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, encodedPassword);
            preparedStatement.setInt(2, userId);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static User getUserByEmailAndPassword(String mail, String password) throws SQLException {
        User user = null;

        String query = "SELECT * FROM users WHERE mail = ? AND password = ?";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, mail);
            preparedStatement.setString(2, password);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    int userId = resultSet.getInt("id");
                    String name = resultSet.getString("name");
                    String surname = resultSet.getString("surname");
                    int role_id = resultSet.getInt("role_id");
                    String country_code = resultSet.getString("country_code");
                    String phone = resultSet.getString("phone");
                    String birthday = resultSet.getString("birthday");

                    user = new User(mail, name, password, role_id, surname, userId, country_code, phone, birthday, "");

                }
            }
        }

        return user;
    }

    public String getSecretKey(int id) {
        String secretKey = "";
        String query = "SELECT secret_key FROM tokens WHERE id = ?";
        try (Connection connection = DatabaseConnection.getConnection();

             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setInt(1, id);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    secretKey = resultSet.getString("secret_key");
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return secretKey;
    }
}
