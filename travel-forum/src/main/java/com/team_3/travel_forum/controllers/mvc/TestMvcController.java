package com.team_3.travel_forum.controllers.mvc;

import com.team_3.travel_forum.exceptions.BlockedUserException;
import com.team_3.travel_forum.exceptions.EntityNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;


//todo remove class when HTML views are ready
@Controller
@RequestMapping("/test")
public class TestMvcController {
    @GetMapping("/404")
    public String notFound() {
        throw new EntityNotFoundException("Testing 404");
    }

    @GetMapping("/403")
    public String forbidden() {
        throw new BlockedUserException("Testing 403");
    }

    @GetMapping("/500")
    public String error() {
        throw new RuntimeException("Testing 500");
    }

}
