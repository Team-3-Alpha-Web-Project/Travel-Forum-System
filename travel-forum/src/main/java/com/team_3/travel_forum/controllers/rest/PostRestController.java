package com.team_3.travel_forum.controllers.rest;

import com.team_3.travel_forum.exceptions.BlockedUserException;
import com.team_3.travel_forum.exceptions.EntityNotFoundException;
import com.team_3.travel_forum.exceptions.UnauthorizedAccessException;
import com.team_3.travel_forum.helpers.PostMapper;
import com.team_3.travel_forum.models.Post;
import com.team_3.travel_forum.models.User;
import com.team_3.travel_forum.models.dtos.PostRequestDTO;
import com.team_3.travel_forum.models.dtos.PostResponseDTO;
import com.team_3.travel_forum.services.PostService;
import com.team_3.travel_forum.services.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/posts")
public class PostRestController {

    //TODO when happy with the Global Exceptions Controllers - delete the try-catch blocks and unused imports

    private final PostService postService;
    private final PostMapper postMapper;
    private final UserService userService;

    @Autowired
    public PostRestController(PostService postService, PostMapper postMapper, UserService userService) {
        this.postService = postService;
        this.postMapper = postMapper;
        this.userService = userService;
    }

    @GetMapping
    public List<PostResponseDTO> get() {
        List<Post> posts = postService.get();
        return postMapper.toDto(posts);
    }

    @GetMapping("/{id}")
    public PostResponseDTO get(@PathVariable int id) {
//        try {
            Post post = postService.get(id);
            return postMapper.toDto(post);
//        } catch (EntityNotFoundException e) {
//            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
//        }
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
    public PostResponseDTO create(@Valid @RequestBody PostRequestDTO postRequestDTO,
                                  Principal principal) {
//        try {
            Post post = postMapper.fromDto(postRequestDTO);
            User currentUser = userService.get(principal.getName());
            postService.create(post, currentUser);
            return postMapper.toDto(post);
//        } catch (EntityNotFoundException e) {
//            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
//        } catch (BlockedUserException e) {
//            throw new ResponseStatusException(HttpStatus.FORBIDDEN, e.getMessage());
//        }
    }

    @PutMapping("/{id}")
    public PostResponseDTO update(@PathVariable int id,
                                  @Valid @RequestBody PostRequestDTO postRequestDTO,
                                  Principal principal) {
//        try {
            Post post = postMapper.fromDto(id, postRequestDTO);
            User currentUser = userService.get(principal.getName());
            postService.update(post, currentUser);
            return postMapper.toDto(post);
//        } catch (EntityNotFoundException e) {
//            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
//        } catch (UnauthorizedAccessException e) {
//            throw new ResponseStatusException(HttpStatus.FORBIDDEN, e.getMessage());
//        }
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable int id,
                       Principal principal) {
//        try {
            User currentUser = userService.get(principal.getName());
            postService.delete(id, currentUser);
//        } catch (EntityNotFoundException e) {
//            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
//        } catch (UnauthorizedAccessException e) {
//            throw new ResponseStatusException(HttpStatus.FORBIDDEN, e.getMessage());
//        }
    }
}