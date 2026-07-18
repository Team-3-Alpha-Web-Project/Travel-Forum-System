package com.team_3.travel_forum.controllers.rest;

import com.team_3.travel_forum.models.User;
import com.team_3.travel_forum.services.LikeService;
import com.team_3.travel_forum.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("/api")
public class LikeRestController {

    private final LikeService likeService;
    private final UserService userService;

    @Autowired
    public LikeRestController(LikeService likeService,
                              UserService userService) {
        this.likeService = likeService;
        this.userService = userService;
    }

    @PostMapping("/posts/{postId}/likes")
    @ResponseStatus(HttpStatus.CREATED)
    public void addLike(@PathVariable int postId,
                        Principal principal) {

        User currentUser = userService.get(principal.getName());

        likeService.addLike(postId, currentUser);
    }

    @DeleteMapping("/posts/{postId}/likes")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void removeLike(@PathVariable int postId,
                           Principal principal) {

        User currentUser = userService.get(principal.getName());

        likeService.removeLike(postId, currentUser);
    }

    @GetMapping("/posts/{postId}/likes/count")
    public long countByPost(@PathVariable int postId) {
        return likeService.countByPost(postId);
    }
}
