package com.team_3.travel_forum.controllers.rest;

import com.team_3.travel_forum.helpers.UserMapper;
import com.team_3.travel_forum.models.User;
import com.team_3.travel_forum.models.dtos.ChangePasswordDto;
import com.team_3.travel_forum.models.dtos.UpdateUserDto;
import com.team_3.travel_forum.models.dtos.UserResponseDto;
import com.team_3.travel_forum.services.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserRestController {

    private final UserService userService;
    private final UserMapper userMapper;

    @Autowired
    public UserRestController(UserService userService, UserMapper userMapper) {
        this.userService = userService;
        this.userMapper = userMapper;
    }

    @GetMapping
    public List<UserResponseDto> get() {
        return userMapper.toDto(userService.get());
    }

    @GetMapping("/me")
    public UserResponseDto getCurrentUser(Principal principal) {
        User user = userService.get(principal.getName());

        return userMapper.toDto(user);
    }

    @PutMapping("/me")
    public void updateCurrentUser(@Valid @RequestBody UpdateUserDto dto, Principal principal) {
        User currentUser = userService.get(principal.getName());
        User user = userMapper.fromDto(dto);

        userService.update(currentUser.getId(), user);
    }

    @PatchMapping("/me/password")
    public void changePassword(@Valid @RequestBody ChangePasswordDto dto,
                               Principal principal) {
        userService.changePassword(principal.getName(), dto);
    }

    @GetMapping("/{id}")
    public UserResponseDto get(@PathVariable int id) {
        User user = userService.get(id);

        return userMapper.toDto(user);
    }

    @GetMapping("/username/{username}")
    public UserResponseDto get(@PathVariable String username) {
        User user = userService.get(username);

        return userMapper.toDto(user);
    }

    @GetMapping("/search/username")
    public List<UserResponseDto> searchByUsername(@RequestParam String username) {
        return userMapper.toDto(userService.searchByUsername(username));
    }

    @GetMapping("/search/email")
    public List<UserResponseDto> searchByEmail(@RequestParam String email) {
        return userMapper.toDto(userService.searchByEmail(email));
    }

    @GetMapping("/search/first-name")
    public List<UserResponseDto> searchByFirstName(@RequestParam String firstName) {
        return userMapper.toDto(userService.searchByFirstName(firstName));
    }

    @PutMapping("/{id}")
    public void update(@PathVariable int id, @Valid @RequestBody UpdateUserDto dto) {
        User user = userMapper.fromDto(dto);

        userService.update(id, user);
    }

    @PatchMapping("/{id}/block")
    public void block(@PathVariable int id) {
        userService.block(id);
    }

    @PatchMapping("/{id}/unblock")
    public void unblock(@PathVariable int id) {
        userService.unblock(id);
    }

    @PatchMapping("/{id}/promote")
    public void promoteToAdmin(@PathVariable int id) {
        userService.promoteToAdmin(id);
    }
}