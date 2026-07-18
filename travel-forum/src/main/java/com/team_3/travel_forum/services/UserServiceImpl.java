package com.team_3.travel_forum.services;

import com.team_3.travel_forum.exceptions.DuplicateEntityException;
import com.team_3.travel_forum.models.Role;
import com.team_3.travel_forum.models.User;
import com.team_3.travel_forum.models.dtos.ChangePasswordDto;
import com.team_3.travel_forum.models.dtos.RegisterUserDto;
import com.team_3.travel_forum.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(UserRepository userRepository
            , PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public List<User> get() {
        return userRepository.get();
    }

    @Override
    public User get(int id) {
        return userRepository.get(id);
    }

    @Override
    public User get(String username) {
        return userRepository.get(username);
    }

    @Override
    public List<User> searchByUsername(String username) {
        return userRepository.searchByUsername(username);
    }

    @Override
    public List<User> searchByEmail(String email) {
        return userRepository.searchByEmail(email);
    }

    @Override
    public List<User> searchByFirstName(String firstName) {
        return userRepository.searchByFirstName(firstName);
    }

    @Override
    public void update(int id, User user) {
        User userToUpdate = userRepository.get(id);

        if (!userToUpdate.getEmail().equalsIgnoreCase(user.getEmail()) && userRepository.existsByEmail(user.getEmail())) {
            throw new DuplicateEntityException("User", "email", user.getEmail());
        }

        userToUpdate.setFirstName(user.getFirstName());
        userToUpdate.setLastName(user.getLastName());
        userToUpdate.setEmail(user.getEmail());
        userToUpdate.setProfilePhotoUrl(user.getProfilePhotoUrl());

        userRepository.update(userToUpdate);
    }

    @Override
    public void delete(int id) {
        userRepository.delete(id);
    }

    @Override
    public void block(int id) {
        User user = userRepository.get(id);

        user.setBlocked(true);

        userRepository.update(user);
    }

    @Override
    public void unblock(int id) {
        User user = userRepository.get(id);

        user.setBlocked(false);

        userRepository.update(user);
    }

    @Override
    public void promoteToAdmin(int id) {
        User user = userRepository.get(id);

        user.setRole(Role.ROLE_ADMIN);

        userRepository.update(user);
    }

    @Override
    public void register(RegisterUserDto dto) {
        if (userRepository.existsByUsername(dto.getUsername())) {
            throw new DuplicateEntityException("User", "username", dto.getUsername());
        }

        if (userRepository.existsByEmail(dto.getEmail())) {
            throw new DuplicateEntityException("User", "email", dto.getEmail());
        }

        User user = new User();

        user.setFirstName(dto.getFirstName());
        user.setLastName(dto.getLastName());
        user.setEmail(dto.getEmail());
        user.setUsername(dto.getUsername());
        user.setPassword(passwordEncoder.encode(dto.getPassword()));
        user.setRole(Role.ROLE_USER);
        user.setBlocked(false);
        user.setProfilePhotoUrl(dto.getProfilePhotoUrl());

        userRepository.create(user);
    }

    @Override
    public long countAllUsers() {
        return userRepository.countAllUsers();
    }

    @Override
    public void changePassword(String username, ChangePasswordDto dto) {
        User user = userRepository.get(username);

        if (!passwordEncoder.matches(dto.getCurrentPassword(), user.getPassword())) {
            throw new IllegalArgumentException("Current password is incorrect.");
        }

        if (!dto.getNewPassword().equals(dto.getConfirmPassword())) {
            throw new IllegalArgumentException("New passwords do not match.");
        }

        user.setPassword(passwordEncoder.encode(dto.getNewPassword()));

        userRepository.update(user);
    }
}
