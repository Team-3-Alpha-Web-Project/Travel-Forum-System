package com.team_3.travel_forum.controllers;

import com.team_3.travel_forum.models.Post;
import com.team_3.travel_forum.models.User;
import com.team_3.travel_forum.services.PostService;
import com.team_3.travel_forum.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/posts")
public class PostRestController {

    private final PostService postService;
    private final UserService userService;

    @Autowired
    public PostRestController(PostService postService, UserService userService) {
        this.postService = postService;
        this.userService = userService;
    }

    @GetMapping
    public List<Post> get() {
        return postService.get();
    }

    @GetMapping("/{id}")
    public Post get(@PathVariable int id) {
        return postService.get(id);
    }

    @GetMapping("/user/{userId}")
    public List<Post> getByUser(@PathVariable int userId) {
        return postService.getByUser(userId);
    }

    @GetMapping("/search")
    public List<Post> search(@RequestParam(required = false) String keyword,
                             @RequestParam(required = false) String sortBy) {
        return postService.search(keyword, sortBy);
    }

    @PostMapping
    public void create(@RequestBody Post post, Authentication authentication) {
        User currentUser = getCurrentUser(authentication);
        postService.create(post, currentUser);
    }

    @PutMapping("/{id}")
    public void update(@PathVariable int id, @RequestBody Post post, Authentication authentication) {
        post.setId(id);
        User currentUser = getCurrentUser(authentication);
        postService.update(post, currentUser);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable int id, Authentication authentication) {
        User currentUser = getCurrentUser(authentication);
        postService.delete(id, currentUser);
    }

    private User getCurrentUser(Authentication authentication) {
        return userService.get(authentication.getName());
    }
}