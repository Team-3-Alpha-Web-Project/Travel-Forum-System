package com.team_3.travel_forum;

import com.team_3.travel_forum.models.*;
import com.team_3.travel_forum.models.dtos.ChangePasswordDto;
import com.team_3.travel_forum.models.dtos.RegisterUserDto;

public class Helpers {

    public static User createMockUser() {
        User user = new User();
        user.setId(1);
        user.setFirstName("Test");
        user.setLastName("User");
        user.setEmail("test@test.com");
        user.setUsername("testuser");
        user.setPassword("encodedOldPassword");
        user.setRole(Role.ROLE_USER);
        user.setBlocked(false);
        return user;
    }

    public static User createOtherUser() {
        User user = new User();
        user.setId(2);
        user.setFirstName("Other");
        user.setLastName("User");
        user.setEmail("other@test.com");
        user.setUsername("otheruser");
        user.setRole(Role.ROLE_USER);
        user.setBlocked(false);
        return user;
    }

    public static User createMockAdmin() {
        User user = new User();
        user.setId(3);
        user.setFirstName("Admin");
        user.setLastName("User");
        user.setEmail("admin@test.com");
        user.setUsername("adminuser");
        user.setRole(Role.ROLE_ADMIN);
        user.setBlocked(false);
        return user;
    }

    public static Post createMockPost() {
        return createMockPost(createMockUser());
    }

    public static Post createMockPost(User user) {
        Post post = new Post();
        post.setId(1);
        post.setTitle("Test post title");
        post.setContent("Test post content");
        post.setUser(user);
        return post;
    }

    public static Comment createMockComment(User user) {
        Comment comment = new Comment();
        comment.setId(1);
        comment.setContent("Test comment");
        comment.setUser(user);

        Post post = new Post();
        post.setId(1);
        comment.setPost(post);

        return comment;
    }

    public static ChangePasswordDto createChangePasswordDto() {
        ChangePasswordDto dto = new ChangePasswordDto();
        dto.setCurrentPassword("OldPassword123!");
        dto.setNewPassword("NewPassword123!");
        dto.setConfirmPassword("NewPassword123!");
        return dto;
    }

    public static RegisterUserDto createRegisterUserDto() {
        RegisterUserDto dto = new RegisterUserDto();
        dto.setFirstName("Test");
        dto.setLastName("User");
        dto.setEmail("newuser@test.com");
        dto.setUsername("newuser");
        dto.setPassword("Password123!");
        dto.setProfilePhotoUrl("photo-url");
        return dto;
    }

    public static User createUpdatedUser() {
        User user = new User();
        user.setFirstName("Updated");
        user.setLastName("User");
        user.setEmail("updated@test.com");
        user.setProfilePhotoUrl("updated-photo-url");
        return user;
    }
}