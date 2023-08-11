package com.sorufidani.app.dao;

import com.sorufidani.app.model.Question;

import java.sql.SQLException;
import java.util.List;

public class QuestionDaoImpl implements QuestionDao {
    @Override
    public Question get(int id) throws SQLException {
        return null;
    }

    @Override
    public List<Question> getAll() throws SQLException {
        return null;
    }

    @Override
    public int save(Question question) throws SQLException {
        return 0;
    }

    @Override
    public int insert(Question question) throws SQLException {
        return 0;
    }

    @Override
    public int update(Question question) throws SQLException {
        return 0;
    }

    @Override
    public int delete(Question question) throws SQLException {
        return 0;
    }
}
