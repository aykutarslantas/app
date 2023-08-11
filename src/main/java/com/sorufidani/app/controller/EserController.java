package com.sorufidani.app.controller;

import com.sorufidani.app.model.User;
import com.sorufidani.app.service.EserService;
import com.sorufidani.app.service.JWTService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class EserController {

    private final JWTService jwtService;
    private final EserService eserService;

    @Autowired
    public EserController(JWTService jwtService, EserService eserService) {
        this.jwtService = jwtService;
        this.eserService = eserService;
    }


    @GetMapping(value = "/eserler", produces = "application/json")
    public String getEserler(@RequestHeader("Authorization") String authorizationHeader, @RequestHeader int id, @RequestBody User userRequest) {
        int userId = jwtService.decodeJwtAndGetUserId(authorizationHeader, id);
        // todo: burda yaşa göre eserleri getireceğiz
        return "";
    }

    @GetMapping(value = "eser", produces = "application/json")
    public String getEser() {
        return "";
    }

    @PostMapping(value = "/eser-update",produces = "application/json")
    public String updateEser(){
        return "";
    }


    @PostMapping(value = "/eser-create",produces = "application/json")
    public String createEser(){
        return "";
    }
}
