package com.sorufidani.app.service;

import com.sorufidani.app.dao.EserDao;
import com.sorufidani.app.model.Eser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class EserService {

    private final EserDao eserDao;

    @Autowired
    public EserService(EserDao eserDao) {
        this.eserDao = eserDao;
    }


    public List<Eser> getEserler(int user_age, int page) {
        try {
            int offset = 0;
            int limit = 50;

            if (page > 1) {
                offset = limit * (page - 1);
                limit *= page;
            }

            return eserDao.getEserler(user_age, offset, limit);
        } catch (Exception e) {
            e.printStackTrace(); // Hata izini
            return new ArrayList<>(); // Hata durumunda boş liste döndür
        }
    }
}
