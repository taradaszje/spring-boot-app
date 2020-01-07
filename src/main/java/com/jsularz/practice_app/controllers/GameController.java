package com.jsularz.practice_app.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class GameController {

    @GetMapping(value = "/game", produces = "application/javascript")
    public String getGame(){
        return "game/index";
    }
}
