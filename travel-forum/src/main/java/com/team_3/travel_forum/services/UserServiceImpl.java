package com.team_3.travel_forum.services;

import com.team_3.travel_forum.models.Role;
import com.team_3.travel_forum.models.User;
import com.team_3.travel_forum.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
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
    public void create(User user) {
        userRepository.create(user);
    }

    @Override
    public void update(User user) {
        userRepository.update(user);
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
}
