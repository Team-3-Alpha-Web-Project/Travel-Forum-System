package com.team_3.travel_forum.controllers.mvc;

import com.team_3.travel_forum.services.PostService;
import com.team_3.travel_forum.services.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class HomeMvcController {

    private final PostService postService;
    private final UserService userService;

    @Autowired
    public HomeMvcController(PostService postService, UserService userService) {
        this.postService = postService;
        this.userService = userService;
    }

    @ModelAttribute("isAuthenticated")
    public boolean populateIsAuthenticated(HttpSession session) {
        return session.getAttribute("currentUser") != null;
    }

    @GetMapping
    public String showHomePage(Model model) {
        model.addAttribute("userCount", userService.countAllUsers());
        model.addAttribute("postCount", postService.countAllPosts());
        //TODO move topCommentedPosts and recentPosts to the Post MVC Controller
        model.addAttribute("topCommentedPosts", postService.getTop10MostCommented());
        model.addAttribute("recentPosts", postService.getTop10Recent());

        return "home-page";
    }

    @GetMapping("/home")
    public String redirectHome() {
        return "redirect:/";
    }

    //TODO login should move to the Auth mvc controller with register
        @GetMapping("/login")
    public String login() {
        return "login";
    }
}
