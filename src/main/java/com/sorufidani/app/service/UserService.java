package com.sorufidani.app.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sorufidani.app.config.GenerateSHA256InSpringBoot;
import com.sorufidani.app.dao.UserDao;
import com.sorufidani.app.dao.UserDaoImpl;
import com.sorufidani.app.model.CreateUserRequest;
import com.sorufidani.app.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;


import com.fasterxml.jackson.core.JsonProcessingException;


@Service
public class UserService {

    private final EmailSenderService senderService;
    private final UserDao userDao;

    private static final String EMAIL_PATTERN = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
    private static final Pattern pattern = Pattern.compile(EMAIL_PATTERN);

    @Autowired
    public UserService(EmailSenderService senderService, UserDao userDao) {
        this.senderService = senderService;
        this.userDao = userDao;
    }

    public String forgotPassword(String mail) {
        Map<String, Object> jsonResponse = new HashMap<>();
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            if (isValidEmail(mail)) {
                if (userDao.getMail(mail)) {
                    senderService.sendEmail(mail, "Test", "İçerik");
                }
                jsonResponse.put("errorCode", 0);
            } else {
                jsonResponse.put("errorCode", 1);
                jsonResponse.put("errorReason", "incorrect");
            }
            return objectMapper.writeValueAsString(jsonResponse);
        } catch (JsonProcessingException ex) {
            ex.printStackTrace();
            throw new RuntimeException("JSON dönüştürme hatası: " + ex.getMessage());
        } catch (SQLException ex) {
            ex.printStackTrace();
            throw new RuntimeException("Veritabanı hatası: " + ex.getMessage());
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new RuntimeException("Bilinmeyen bir hata oluştu: " + ex.getMessage());
        }
    }

    public String resetPassword(String hash, String password) throws NoSuchAlgorithmException, JsonProcessingException {
        Map<String, Object> jsonResponse = new HashMap<>();
        ObjectMapper objectMapper = new ObjectMapper();
        int userId = userDao.getIdWithHash(hash);
        if (userId != 0) {
            userDao.updatePassword(userId, hash);
            jsonResponse.put("errorCode", 0);
        } else {
            //todo: log tutalım burda
            jsonResponse.put("errorCode", 0);
        }
        return objectMapper.writeValueAsString(jsonResponse);
    }

    public String createUser(CreateUserRequest createUserRequest) throws JsonProcessingException {
        Map<String, Object> jsonResponse = new HashMap<>();
        ObjectMapper objectMapper = new ObjectMapper();
        String mail = createUserRequest.getMail();
        if (isValidEmail(mail)) {
            try {
                UserDao userDao = new UserDaoImpl();
                String encodedPassword = GenerateSHA256InSpringBoot.toHexString(GenerateSHA256InSpringBoot.getSHA(createUserRequest.getPassword()));
                User user = new User(createUserRequest.getMail(), createUserRequest.getFirstName(), encodedPassword, 1, createUserRequest.getLastName(), 0, "", "", createUserRequest.getBirthDate(), sha256Hash(mail + "secretsecret"));
                userDao.insert(user);
                senderService.sendEmail(mail, "Hoşgeldiniz", "Aktivasyon linki");
                jsonResponse.put("errorCode", 0);
            } catch (NoSuchAlgorithmException | SQLException e) {
                jsonResponse.put("errorCode", 1);
                jsonResponse.put("errorReason", "duplicate");
            }
        } else {
            jsonResponse.put("errorCode", 1);
            jsonResponse.put("errorReason", "mail");
        }
        return objectMapper.writeValueAsString(jsonResponse);
    }

    public String getSecretKey(int user_id) {
        return userDao.getSecretKey(user_id);
    }

    public static boolean isValidEmail(String mail) {
        Matcher matcher = pattern.matcher(mail);
        return matcher.matches();
    }

    public static String sha256Hash(String input) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        byte[] hashBytes = md.digest(input.getBytes(StandardCharsets.UTF_8));
        StringBuilder sb = new StringBuilder();
        for (byte b : hashBytes) {
            sb.append(String.format("%02x", b & 0xff));
        }
        return sb.toString();
    }

    public User get(int id) throws SQLException {
        return userDao.get(id);
    }


    public String updateUser(User userRequest, int id) {
        try {
            String encodedPassword = GenerateSHA256InSpringBoot.toHexString(GenerateSHA256InSpringBoot.getSHA(userRequest.getPassword()));

            User user = userDao.get(id);

            user.setId(id);
            user.setName(userRequest.getName());
            user.setSurname(userRequest.getSurname());
            user.setMail(userRequest.getMail());
            user.setPassword(encodedPassword);
            user.setBirthday(userRequest.getBirthday());

            return String.valueOf(userDao.update(user));

        } catch (SQLException e) {
            return String.valueOf(e);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }
}