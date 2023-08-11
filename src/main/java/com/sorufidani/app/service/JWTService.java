package com.sorufidani.app.service;

import com.sorufidani.app.config.GenerateSHA256InSpringBoot;
import com.sorufidani.app.dao.TokenDao;
import com.sorufidani.app.dao.TokenDaoImpl;
import com.sorufidani.app.dao.UserDaoImpl;
import com.sorufidani.app.model.LoginRequest;
import com.sorufidani.app.model.Token;
import com.sorufidani.app.model.User;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Service;
import io.jsonwebtoken.security.Keys;
import org.springframework.context.MessageSource;

import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.*;

import io.jsonwebtoken.Claims;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class JWTService {

    private static final Key SECRET_KEY = Keys.secretKeyFor(SignatureAlgorithm.HS256);
    private static final long EXPIRATION_TIME = 86400000; // 1 day in milliseconds
    private static final String[] ALLOWED_TYPE = {"app", "web"};
    private final UserService userService;
    private final MessageSource messageSource;

    public JWTService(UserService userService, MessageSource messageSource) {
        this.userService = userService;
        this.messageSource = messageSource;
    }

    public static class ErrorMessages {
        public static final String INVALID_AUTH_HEADER = "error.auth.invalid";
        public static final String TOKEN_VALIDATION_FAILED = "error.token.validation.failed";
    }

    public String generateJwtToken(String mail, String password, String type, HttpServletRequest request) {

        try {
            LoginRequest loginRequest = new LoginRequest(mail, password, type);
            String headers = getHeaderInfo(request);
            if (!isValidLoginType(type)) {
                return generateErrorResponse("Invalid login type");
            }
            String encodedPassword = GenerateSHA256InSpringBoot.toHexString(GenerateSHA256InSpringBoot.getSHA(password));
            User user = UserDaoImpl.getUserByEmailAndPassword(mail, encodedPassword);
            if (user != null) {
                return generateTokenResponse(user, headers, type);
            } else {
                return generateErrorResponse("Invalid credentials");
            }
        } catch (NoSuchAlgorithmException ex) {
            return "error: Algorithm not available";
        } catch (SQLException ex) {
            return "error: Database error";
        }
    }

    public int decodeJwtAndGetUserId(String authorizationHeader, int id) {
        int userId = 0;

        if (!isValidAuthorizationHeader(authorizationHeader) || id < 1) {
                return Integer.parseInt(messageSource.getMessage(ErrorMessages.INVALID_AUTH_HEADER, null, Locale.getDefault()));
        }

        String token = extractTokenFromHeader(authorizationHeader);
        String secretKey = userService.getSecretKey(id);

        if (secretKey == null || secretKey.isEmpty()) {
            throw new RuntimeException("Secret key not found for user");
        }

        try {
            try {
                Claims claims = parseToken(token, secretKey);
                userId = Integer.parseInt(claims.getSubject());
            } catch (Exception e) {
                throw new RuntimeException("Token validation failed");
            }
        } catch (Exception e) {
            return Integer.parseInt(messageSource.getMessage(ErrorMessages.TOKEN_VALIDATION_FAILED, null, Locale.getDefault()));
        }
        return userId;
    }

    private String getHeaderInfo(HttpServletRequest request) {
        Enumeration<String> headerNames = request.getHeaderNames();
        StringBuilder response = new StringBuilder();
        while (headerNames.hasMoreElements()) {
            String headerName = headerNames.nextElement();
            String headerValue = request.getHeader(headerName);
            response.append(headerName).append(": ").append(headerValue).append("\n");
        }
        return response.toString();
    }

    private boolean isValidLoginType(String type) {
        return Arrays.asList(ALLOWED_TYPE).contains(type);
    }

    private String generateTokenResponse(User user, String headers, String type) throws SQLException {
        TokenDao tokenDao = new TokenDaoImpl();
        int lastLoginId = tokenDao.getUserTokenWithMail(user.getMail(), type);

        if (lastLoginId != 0) {//daha önce giriş yapmış
            tokenDao.updateTokenStatus(lastLoginId);
        }

        Token token = createToken(user.getId(), headers, type);
        int generatedTokenId = tokenDao.insert(token);

        Map<String, Object> jsonResponse = new HashMap<>();
        jsonResponse.put("errorCode", 0);
        jsonResponse.put("id", generatedTokenId);
        jsonResponse.put("token", generateAuthToken(user.getId(), type));

        return generateJsonResponse(jsonResponse);
    }

    private Token createToken(int userId, String headers, String type) {
        String secretKeyBase64 = Base64.getEncoder().encodeToString(SECRET_KEY.getEncoded());
        return new Token(secretKeyBase64, userId, type, headers);
    }

    private String generateAuthToken(int userId, String type) {
        return Jwts.builder()
                .setSubject(String.valueOf(userId))
                .setAudience(type)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(SECRET_KEY, SignatureAlgorithm.HS256)
                .compact();
    }

    private String generateErrorResponse(String errorReason) {
        Map<String, Object> jsonResponse = new HashMap<>();
        jsonResponse.put("errorCode", 1);
        jsonResponse.put("errorReason", errorReason);

        return generateJsonResponse(jsonResponse);
    }

    private String generateJsonResponse(Map<String, Object> jsonResponse) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.writeValueAsString(jsonResponse);
        } catch (Exception e) {
            return "error";
        }
    }

    private boolean isValidAuthorizationHeader(String authorizationHeader) {
        return authorizationHeader != null && authorizationHeader.startsWith("Bearer ");
    }

    private String extractTokenFromHeader(String authorizationHeader) {
        return authorizationHeader.substring(7);
    }

    private Claims parseToken(String token, String secretKey) {
        return Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
}