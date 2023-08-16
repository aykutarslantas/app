package com.sorufidani.app.dao;

import com.sorufidani.app.config.DatabaseConnection;
import com.sorufidani.app.model.Eser;
import com.sorufidani.app.model.User;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Repository
public class EserDaoImpl implements EserDao {
    @Override
    public Eser get(int id) throws SQLException {
        Eser eser = new Eser(id,"","","");

        String query = "SELECT * FROM eserler WHERE id = ?";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setInt(1, id);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    eser.setName(resultSet.getString("name"));
                    eser.setCover(resultSet.getString("cover"));
                    eser.setCreated_at(resultSet.getString("name"));
                    eser.setPublisher_id(resultSet.getInt("publisher_id"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Hata izini
            throw new RuntimeException(e);
        }

        return eser;
    }

    @Override
    public List<Eser> getAll() throws SQLException {
        return null;
    }

    @Override
    public int save(Eser eser) throws SQLException {
        return 0;
    }

    @Override
    public int insert(Eser eser) throws SQLException {
        return 0;
    }

    @Override
    public int update(Eser eser) throws SQLException {
        return 0;
    }

    @Override
    public int delete(Eser eser) throws SQLException {
        return 0;
    }

    @Override
    public List<Eser> getEserler(int user_age, int offset, int limit) {
        List<Eser> eserList = new ArrayList<>();

        String query = "SELECT ac.title as category_title, a.title as attribute_title, e.*, CONCAT(u.name,' ',u.surname) as username " +
                "FROM attribute_categories ac " +
                "LEFT JOIN attributes a ON a.category_id = ac.id " +
                "LEFT JOIN eserler e ON e.id = a.eser_id " +
                "LEFT JOIN users u ON u.id = e.publisher_id " +
                "WHERE e.min_age < ? GROUP BY e.id LIMIT ? , ?";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setInt(1, user_age);
            preparedStatement.setInt(2, offset);
            preparedStatement.setInt(3, limit);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    int publisher_id = resultSet.getInt("publisher_id");
                    String name = resultSet.getString("name");
                    String created_at = resultSet.getString("created_at");
                    String cover = resultSet.getString("cover");

                    Eser eser = new Eser(publisher_id, name, created_at, cover);
                    eserList.add(eser);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Hata izini
            throw new RuntimeException(e);
        }

        return eserList;
    }
}
