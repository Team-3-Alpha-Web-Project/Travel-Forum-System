package com.team_3.travel_forum.controllers.rest;

import com.team_3.travel_forum.models.Comment;
import com.team_3.travel_forum.models.User;
import com.team_3.travel_forum.models.dtos.CommentRequestDto;
import com.team_3.travel_forum.models.dtos.CommentResponseDto;
import com.team_3.travel_forum.services.CommentService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class CommentRestController {

    private final CommentService commentService;

    public CommentRestController(CommentService commentService) {
        this.commentService = commentService;
    }

    @GetMapping("/posts/{postId}/comments")
    public List<CommentResponseDto> getCommentsByPost(
            @PathVariable int postId,
            @AuthenticationPrincipal User currentUser) {

        List<Comment> comments = commentService.getCommentsByPost(postId, currentUser);
        List<CommentResponseDto> commentsDto = comments.stream().map(this::convertToDto).collect(Collectors.toList());
        return commentsDto;
    }

    @GetMapping("/users/{userId}/comments")
    public List<CommentResponseDto> getCommentsByUser(
            @PathVariable int userId,
            @AuthenticationPrincipal User currentUser) {

        List<Comment> comments = commentService.getCommentsByUser(userId, currentUser);
        List<CommentResponseDto> commentsDto= comments.stream().map(this::convertToDto).collect(Collectors.toList());
        return commentsDto;
    }

    @PostMapping("/posts/{postId}/comments")
    @ResponseStatus(HttpStatus.CREATED)
    public void createComment(@PathVariable int postId,
                              @Valid @RequestBody CommentRequestDto commentRequestDto,
                              @AuthenticationPrincipal User currentUser) {

        commentService.create(commentRequestDto.getContent(), postId, currentUser);
    }

    @PutMapping("/comments/{commentId}")
    public void updateComment(
            @PathVariable int commentId,
            @Valid @RequestBody CommentRequestDto commentRequestDto,
            @AuthenticationPrincipal User currentUser) {

        commentService.update(commentId, commentRequestDto.getContent(), currentUser);
    }

    @DeleteMapping("/comments/{commentId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteComment(
            @PathVariable int commentId,
            @AuthenticationPrincipal User currentUser) {

        commentService.delete(commentId, currentUser);
    }

    private CommentResponseDto convertToDto(Comment comment) {
        CommentResponseDto commentResponseDto = new CommentResponseDto();
        commentResponseDto.setId(comment.getId());
        commentResponseDto.setContent(comment.getContent());
        commentResponseDto.setCreatedAt(comment.getCreatedAt());
        commentResponseDto.setPostId(comment.getPost().getId());
        commentResponseDto.setUserId(comment.getUser().getId());
        commentResponseDto.setUsername(comment.getUser().getUsername());

        return commentResponseDto;
    }

}
