package com.team_3.travel_forum.repositories;

import com.team_3.travel_forum.models.User;

import java.util.List;

public interface UserRepository {

    List<User> get();

    User get(int id);

    User get(String username);

    List<User> searchByUsername(String username);

    List<User> searchByEmail(String email);

    List<User> searchByFirstName(String firstName);

    boolean existsByUsername(String username);

    boolean existsByEmail(String email);

    void create(User user);

    void update(User user);

    void delete(int id);

    long countAllUsers();
}
