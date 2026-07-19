package com.team_3.travel_forum.services;

import com.team_3.travel_forum.models.User;
import com.team_3.travel_forum.models.dtos.ChangePasswordDto;
import com.team_3.travel_forum.models.dtos.RegisterUserDto;
import java.util.List;

public interface UserService {

    List<User> get();

    User get(int id);

    User get(String username);

    List<User> searchByUsername(String username);

    List<User> searchByEmail(String email);

    List<User> searchByFirstName(String firstName);

    void update(int id, User user);

    void block(int id);

    void unblock(int id);

    void promoteToAdmin(int id);

    void register(RegisterUserDto dto);

    long countAllUsers();

    void changePassword(String username, ChangePasswordDto dto);
}
