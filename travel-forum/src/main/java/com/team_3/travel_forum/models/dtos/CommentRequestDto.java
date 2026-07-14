package com.team_3.travel_forum.models.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class CommentRequestDto {

    @NotBlank(message = "Comment cannot be empty.")
    @Size(min = 1, max = 4096, message = "Comment must be between 1 and 4096 characters.")
    private String content;

    public CommentRequestDto() {}

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
