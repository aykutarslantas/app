package com.sorufidani.app.dao;

import com.sorufidani.app.model.Eser;
import org.springframework.stereotype.Repository;

import java.util.List;


public interface EserDao extends DAO<Eser> {

    List<Eser> getEserler(int user_age, int offset, int limit);

}
