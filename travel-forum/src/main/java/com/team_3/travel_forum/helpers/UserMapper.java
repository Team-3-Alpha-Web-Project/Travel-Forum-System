package com.team_3.travel_forum.helpers;

import com.team_3.travel_forum.models.User;
import com.team_3.travel_forum.models.dtos.UpdateUserDto;
import com.team_3.travel_forum.models.dtos.UserResponseDto;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UserMapper {

    public UserResponseDto toDto(User user) {
        UserResponseDto dto = new UserResponseDto();

        dto.setId(user.getId());
        dto.setFirstName(user.getFirstName());
        dto.setLastName(user.getLastName());
        dto.setEmail(user.getEmail());
        dto.setUsername(user.getUsername());
        dto.setRole(user.getRole().name());
        dto.setBlocked(user.isBlocked());
        dto.setProfilePhotoUrl(user.getProfilePhotoUrl());

        return dto;
    }

    public List<UserResponseDto> toDto(List<User> users) {
        return users.stream()
                .map(this::toDto)
                .toList();
    }

    public User fromDto(UpdateUserDto dto) {
        User user = new User();

        user.setFirstName(dto.getFirstName());
        user.setLastName(dto.getLastName());
        user.setEmail(dto.getEmail());
        user.setProfilePhotoUrl(dto.getProfilePhotoUrl());

        return user;
    }
}
