package com.team_3.travel_forum.controllers.rest;

import com.team_3.travel_forum.exceptions.BlockedUserException;
import com.team_3.travel_forum.exceptions.EntityNotFoundException;
import com.team_3.travel_forum.exceptions.UnauthorizedAccessException;
import com.team_3.travel_forum.helpers.CommentMapper;
import com.team_3.travel_forum.models.Comment;
import com.team_3.travel_forum.models.User;
import com.team_3.travel_forum.models.dtos.CommentRequestDto;
import com.team_3.travel_forum.models.dtos.CommentResponseDto;
import com.team_3.travel_forum.services.CommentService;
import com.team_3.travel_forum.services.PostService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api/comments")
public class CommentRestController {

    private final CommentService commentService;
    private final PostService postService;
    private final CommentMapper commentMapper;


    @Autowired
    public CommentRestController(CommentService commentService,
                                 PostService postService,
                                 CommentMapper commentMapper) {
        this.commentService = commentService;
        this.postService = postService;
        this.commentMapper = commentMapper;
    }

    @GetMapping("/{id}")
    public CommentResponseDto get(@PathVariable int id) {
        try {
            Comment comment = commentService.get(id);
            return commentMapper.toDto(comment);
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,e.getMessage());
        }
    }

    @GetMapping("/posts/{postId}/comments")
    public List<CommentResponseDto> getByPost(
            @PathVariable int postId,
            @AuthenticationPrincipal User currentUser) {

        List<Comment> comments = commentService.getByPost(postId, currentUser);

        return commentMapper.toDto(comments);
    }

    @GetMapping("/users/{userId}/comments")
    public List<CommentResponseDto> getByUser(
            @PathVariable int userId,
            @AuthenticationPrincipal User currentUser) {

        List<Comment> comments = commentService.getByUser(userId, currentUser);

        return commentMapper.toDto(comments);
    }

    @PostMapping("/posts/{postId}/comments")
    @ResponseStatus(HttpStatus.CREATED)
    public CommentResponseDto createComment(@PathVariable int postId,
                              @Valid @RequestBody CommentRequestDto commentRequestDto,
                              @AuthenticationPrincipal User currentUser) {
        try {
            Comment comment = commentMapper.fromDto(commentRequestDto);
            comment.setPost(postService.get(postId));
            comment.setUser(currentUser);

            commentService.create(comment, currentUser);
            return commentMapper.toDto(comment);
        } catch (BlockedUserException e) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, e.getMessage());
        }
    }

    @PutMapping("/comments/{commentId}")
    public CommentResponseDto updateComment(
            @PathVariable int commentId,
            @Valid @RequestBody CommentRequestDto commentRequestDto,
            @AuthenticationPrincipal User currentUser) {

        try {
            Comment comment = commentMapper.fromDto(commentId, commentRequestDto);
            commentService.update(comment, currentUser);
            return commentMapper.toDto(comment);
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        } catch (UnauthorizedAccessException e) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, e.getMessage());
        }
    }

    @DeleteMapping("/comments/{commentId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteComment(
            @PathVariable int commentId,
            @AuthenticationPrincipal User currentUser) {
        try {
            commentService.delete(commentId, currentUser);
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        } catch (UnauthorizedAccessException e) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, e.getMessage());
        }
    }

}
