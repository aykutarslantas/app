package com.sorufidani.app.controller;

import com.sorufidani.app.model.User;
import com.sorufidani.app.service.EserService;
import com.sorufidani.app.service.JWTService;
import com.sorufidani.app.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.Period;
import java.util.Objects;

@CrossOrigin(origins = "http://localhost:3000")

@RestController
@RequestMapping("/api")
public class EserController {

    private final JWTService jwtService;
    private final EserService eserService;
    private final UserService userService;

    @Autowired
    public EserController(JWTService jwtService, EserService eserService, UserService userService) {
        this.jwtService = jwtService;
        this.eserService = eserService;
        this.userService = userService;
    }


    @GetMapping(value = "/eserler", produces = "application/json")
    public Object getEserler(@RequestHeader("Authorization") String authorizationHeader, @RequestParam int page) {
        try {
            int userId = jwtService.decodeJwtAndGetUserId(authorizationHeader);
            User user = userService.get(userId);

            int userAge = calculateUserAge(user.getBirthday());
            return eserService.getEserler(userAge, page);

        } catch (Exception e) {
            e.printStackTrace();
            return e;
        }
    }

    @GetMapping(value = "eser/{id}", produces = "application/json")
    public Object getEser(@PathVariable("id") int eserId, @RequestHeader("Authorization") String authorizationHeader) {
        //todo: burda yoksa
        try {
            int userId = jwtService.decodeJwtAndGetUserId(authorizationHeader);
            User user = userService.get(userId);
            return eserService.get(eserId);

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @PostMapping(value = "/eser-update", produces = "application/json")
    public String updateEser() {
        return "";
    }


    @PostMapping(value = "/eser-create", produces = "application/json")
    public String createEser() {
        return "";
    }

    private int calculateUserAge(String birthdayString) {
        if (Objects.equals(birthdayString, "")) {
            return 18;
        }

        String[] dateParts = birthdayString.split("-");
        int year = Integer.parseInt(dateParts[0]);
        int month = Integer.parseInt(dateParts[1]);
        int day = Integer.parseInt(dateParts[2]);

        LocalDate birthDate = LocalDate.of(year, month, day);
        LocalDate currentDate = LocalDate.now();
        Period age = Period.between(birthDate, currentDate);
        return age.getYears();
    }
}
