package com.team_3.travel_forum.controllers;

import com.team_3.travel_forum.exceptions.BlockedUserException;
import com.team_3.travel_forum.exceptions.UnauthorizedAccessException;
import com.team_3.travel_forum.helpers.PostMapper;
import com.team_3.travel_forum.models.Post;
import com.team_3.travel_forum.models.User;
import com.team_3.travel_forum.models.dtos.PostRequestDTO;
import com.team_3.travel_forum.models.dtos.PostResponseDTO;
import com.team_3.travel_forum.services.PostService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api/posts")
public class PostRestController {

    private final PostService postService;
    private final PostMapper postMapper;

    public PostRestController(PostService postService, PostMapper postMapper) {
        this.postService = postService;
        this.postMapper = postMapper;
    }

    @GetMapping
    public List<PostResponseDTO> get() {
        List<Post> posts = postService.get();
        return postMapper.toDto(posts);
    }

    @GetMapping("/{id}")
    public PostResponseDTO get(@PathVariable int id) {
        try {
            Post post = postService.get(id);
            return postMapper.toDto(post);
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @GetMapping("/user/{userId}")
    public List<PostResponseDTO> getByUser(@PathVariable int userId) {
        List<Post> posts = postService.getByUser(userId);
        return postMapper.toDto(posts);
    }

    @GetMapping("/search")
    public List<PostResponseDTO> search(@RequestParam(required = false) String keyword,
                                        @RequestParam(required = false) String sortBy) {
        List<Post> posts = postService.search(keyword, sortBy);
        return postMapper.toDto(posts);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void create(@Valid @RequestBody PostRequestDTO postRequestDTO,
                       @AuthenticationPrincipal User currentUser) {
        try {
            Post post = postMapper.fromDto(postRequestDTO);
            postService.create(post, currentUser);
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        } catch (BlockedUserException e) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public void update(@PathVariable int id,
                       @Valid @RequestBody PostRequestDTO postRequestDTO,
                       @AuthenticationPrincipal User currentUser) {
        try {
            Post post = postMapper.fromDto(id, postRequestDTO);
            postService.update(post, currentUser);
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        } catch (UnauthorizedAccessException e) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable int id,
                       @AuthenticationPrincipal User currentUser) {
        try {
            postService.delete(id, currentUser);
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        } catch (UnauthorizedAccessException e) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, e.getMessage());
        }
    }
}