package com.team_3.travel_forum.controllers.rest;

import com.team_3.travel_forum.exceptions.BlockedUserException;
import com.team_3.travel_forum.exceptions.EntityNotFoundException;
import com.team_3.travel_forum.models.User;
import com.team_3.travel_forum.services.LikeService;
import com.team_3.travel_forum.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

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
        try {
            User currentUser = userService.get(principal.getName());

            likeService.addLike(postId, currentUser);
        } catch (BlockedUserException e) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, e.getMessage());
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @DeleteMapping("/posts/{postId}/likes")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void removeLike(@PathVariable int postId,
                           Principal principal) {
        try {
            User currentUser = userService.get(principal.getName());

            likeService.removeLike(postId, currentUser);
        } catch (BlockedUserException e) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, e.getMessage());
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @GetMapping("/posts/{postId}/likes/count")
    public long countByPost(@PathVariable int postId) {
        try {
            return likeService.countByPost(postId);
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }
}
