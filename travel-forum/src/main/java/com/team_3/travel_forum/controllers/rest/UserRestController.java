package com.team_3.travel_forum.controllers.rest;

import com.team_3.travel_forum.models.User;
import com.team_3.travel_forum.models.dtos.ChangePasswordDto;
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

    @Autowired
    public UserRestController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public List<User> get() {
        return userService.get();
    }

    @GetMapping("/me")
    public User getCurrentUser(Principal principal) {
        return userService.get(principal.getName());
    }

    @PutMapping("/me")
    public void updateCurrentUser(@RequestBody User user, Principal principal) {
        User currentUser = userService.get(principal.getName());

        userService.update(currentUser.getId(), user);
    }

    @PatchMapping("/me/password")
    public void changePassword(@Valid @RequestBody ChangePasswordDto dto,
                               Principal principal) {
        userService.changePassword(principal.getName(), dto);
    }

    @GetMapping("/{id}")
    public User get(@PathVariable int id) {
        return userService.get(id);
    }

    @GetMapping("/username/{username}")
    public User get(@PathVariable String username) {
        return userService.get(username);
    }

    @GetMapping("/search/username")
    public List<User> searchByUsername(@RequestParam String username) {
        return userService.searchByUsername(username);
    }

    @GetMapping("/search/email")
    public List<User> searchByEmail(@RequestParam String email) {
        return userService.searchByEmail(email);
    }

    @GetMapping("/search/first-name")
    public List<User> searchByFirstName(@RequestParam String firstName) {
        return userService.searchByFirstName(firstName);
    }

    @PutMapping("/{id}")
    public void update(@PathVariable int id, @RequestBody User user) {
        userService.update(id, user);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable int id) {
        userService.delete(id);
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