package com.team_3.travel_forum.controllers.mvc;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;

@Controller
public class PageController {

    @GetMapping("/")
    public String root() {
        return "redirect:/home";
    }

    @GetMapping("/home")
    public String home(Model model, Principal principal) {
        model.addAttribute(
                "username",
                principal == null ? null : principal.getName()
        );
        return "home";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }
}