package com.team_3.travel_forum.models.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class PostRequestDTO {

    @NotBlank(message = "Title is required.")
    @Size(min = 16, max = 64, message = "Title must be between 16 and 64 characters.")
    private String title;

    @NotBlank(message = "Content is required.")
    @Size(min = 32, max = 8192, message = "Content must be between 32 and 8192 characters.")
    private String content;

    public PostRequestDTO() {
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}