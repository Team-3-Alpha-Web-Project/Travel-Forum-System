package com.team_3.travel_forum.services;

import com.team_3.travel_forum.models.User;

import java.util.List;

public interface UserService {

    List<User> get();

    User get(int id);

    User get(String username);

    List<User> searchByUsername(String username);

    List<User> searchByEmail(String email);

    List<User> searchByFirstName(String firstName);

    void create(User user);

    void update(User user);

    void delete(int id);

    void block(int id);

    void unblock(int id);

    void promoteToAdmin(int id);
}
