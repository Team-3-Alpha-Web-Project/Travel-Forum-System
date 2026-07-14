package com.team_3.travel_forum.controllers.rest;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeRestController {

    @GetMapping("/api")
    public String home() {
        return "Home controller.";
    }
}
