package com.team_3.travel_forum.helpers;

import com.team_3.travel_forum.models.Comment;
import com.team_3.travel_forum.models.dtos.CommentRequestDto;
import com.team_3.travel_forum.models.dtos.CommentResponseDto;
import com.team_3.travel_forum.services.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class CommentMapper {

    private final CommentService commentService;

    @Autowired
    public CommentMapper(CommentService commentService) {
        this.commentService = commentService;
    }

    public Comment fromDto(CommentRequestDto dto) {
        Comment comment = new Comment();
        comment.setContent(dto.getContent());
        return comment;
    }

    public Comment fromDto(int id, CommentRequestDto dto) {
        Comment comment = commentService.get(id);
        comment.setContent(dto.getContent());
        return comment;
    }

    public CommentResponseDto toDto(Comment comment) {
        CommentResponseDto dto = new CommentResponseDto();
        dto.setId(comment.getId());
        dto.setContent(comment.getContent());
        dto.setPostId(comment.getPost().getId());
        dto.setUserId(comment.getUser().getId());
        dto.setUsername(comment.getUser().getUsername());
        dto.setCreatedAt(comment.getCreatedAt());

        return dto;
    }

    public List<CommentResponseDto> toDto(List<Comment> comments) {
        List<CommentResponseDto> commentsDto = new ArrayList<>();

        for (Comment comment : comments) {
            commentsDto.add(toDto(comment));
        }

        return commentsDto;
    }
}
