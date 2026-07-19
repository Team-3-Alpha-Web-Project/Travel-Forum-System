package com.team_3.travel_forum.servicesTests;

import static com.team_3.travel_forum.Helpers.*;
import com.team_3.travel_forum.exceptions.DuplicateEntityException;
import com.team_3.travel_forum.exceptions.EntityNotFoundException;
import com.team_3.travel_forum.models.Role;
import com.team_3.travel_forum.models.User;
import com.team_3.travel_forum.models.dtos.ChangePasswordDto;
import com.team_3.travel_forum.models.dtos.RegisterUserDto;
import com.team_3.travel_forum.repositories.UserRepository;
import com.team_3.travel_forum.services.UserServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;

@ExtendWith(MockitoExtension.class)
public class UserServiceImplTests {

    @Mock
    UserRepository mockUserRepository;

    @Mock
    PasswordEncoder mockPasswordEncoder;

    @InjectMocks
    UserServiceImpl userService;

    @Test
    public void get_Should_ReturnAllUsers_When_UsersExist() {
        List<User> mockUsers = List.of(createMockUser());

        Mockito.when(mockUserRepository.get())
                .thenReturn(mockUsers);

        List<User> result = userService.get();

        Assertions.assertEquals(mockUsers, result);

        Mockito.verify(mockUserRepository, Mockito.times(1))
                .get();
    }

    @Test
    public void get_Should_ThrowException_When_UserDoesNotExist() {
        Mockito.when(mockUserRepository.get(1))
                .thenThrow(new EntityNotFoundException("User"));

        Assertions.assertThrows(
                EntityNotFoundException.class,
                () -> userService.get(1)
        );

        Mockito.verify(mockUserRepository, Mockito.times(1))
                .get(1);
    }

    @Test
    public void getByUsername_Should_ReturnUser_When_UserExists() {
        User mockUser = createMockUser();

        Mockito.when(mockUserRepository.get("testuser"))
                .thenReturn(mockUser);

        User result = userService.get("testuser");

        Assertions.assertEquals(mockUser, result);

        Mockito.verify(mockUserRepository, Mockito.times(1))
                .get("testuser");
    }

    @Test
    public void getByUsername_Should_ThrowException_When_UserDoesNotExist() {
        String username = "missinguser";

        Mockito.when(mockUserRepository.get(username))
                .thenThrow(new EntityNotFoundException("User", "username", username));

        Assertions.assertThrows(
                EntityNotFoundException.class,
                () -> userService.get(username)
        );

        Mockito.verify(mockUserRepository, Mockito.times(1))
                .get(username);
    }

    @Test
    public void searchByUsername_Should_ReturnUsers_When_UsersExist() {
        String username = "test";
        List<User> mockUsers = List.of(createMockUser());

        Mockito.when(mockUserRepository.searchByUsername(username))
                .thenReturn(mockUsers);

        List<User> result = userService.searchByUsername(username);

        Assertions.assertEquals(mockUsers, result);

        Mockito.verify(mockUserRepository, Mockito.times(1))
                .searchByUsername(username);
    }

    @Test
    public void searchByEmail_Should_ReturnUsers_When_UsersExist() {
        String email = "test@test.com";
        List<User> mockUsers = List.of(createMockUser());

        Mockito.when(mockUserRepository.searchByEmail(email))
                .thenReturn(mockUsers);

        List<User> result = userService.searchByEmail(email);

        Assertions.assertEquals(mockUsers, result);

        Mockito.verify(mockUserRepository, Mockito.times(1))
                .searchByEmail(email);
    }

    @Test
    public void searchByFirstName_Should_ReturnUsers_When_UsersExist() {
        String firstName = "Test";
        List<User> mockUsers = List.of(createMockUser());

        Mockito.when(mockUserRepository.searchByFirstName(firstName))
                .thenReturn(mockUsers);

        List<User> result = userService.searchByFirstName(firstName);

        Assertions.assertEquals(mockUsers, result);

        Mockito.verify(mockUserRepository, Mockito.times(1))
                .searchByFirstName(firstName);
    }

    @Test
    public void changePassword_Should_UpdatePassword_When_RequestIsValid() {
        User mockUser = createMockUser();
        ChangePasswordDto dto = createChangePasswordDto();

        Mockito.when(mockUserRepository.get(Mockito.anyString()))
                .thenReturn(mockUser);

        Mockito.when(mockPasswordEncoder.matches(dto.getCurrentPassword(), mockUser.getPassword()))
                .thenReturn(true);

        Mockito.when(mockPasswordEncoder.encode(dto.getNewPassword()))
                .thenReturn("encodedNewPassword");

        userService.changePassword("testuser", dto);

        Assertions.assertEquals("encodedNewPassword", mockUser.getPassword());

        Mockito.verify(mockUserRepository, Mockito.times(1))
                .update(mockUser);
    }

    @Test
    public void changePassword_Should_ThrowException_When_CurrentPasswordIsWrong() {
        User mockUser = createMockUser();
        ChangePasswordDto dto = createChangePasswordDto();

        Mockito.when(mockUserRepository.get(Mockito.anyString()))
                .thenReturn(mockUser);

        Mockito.when(mockPasswordEncoder.matches(dto.getCurrentPassword(), mockUser.getPassword()))
                .thenReturn(false);

        Assertions.assertThrows(
                IllegalArgumentException.class,
                () -> userService.changePassword("testuser", dto)
        );

        Mockito.verify(mockPasswordEncoder, Mockito.never())
                .encode(Mockito.anyString());

        Mockito.verify(mockUserRepository, Mockito.never())
                .update(Mockito.any(User.class));
    }

    @Test
    public void changePassword_Should_ThrowException_When_NewPasswordsDoNotMatch() {
        User mockUser = createMockUser();
        ChangePasswordDto dto = createChangePasswordDto();
        dto.setConfirmPassword("DifferentPassword123!");

        Mockito.when(mockUserRepository.get(Mockito.anyString()))
                .thenReturn(mockUser);

        Mockito.when(mockPasswordEncoder.matches(dto.getCurrentPassword(), mockUser.getPassword()))
                .thenReturn(true);

        Assertions.assertThrows(
                IllegalArgumentException.class,
                () -> userService.changePassword("testuser", dto)
        );

        Mockito.verify(mockPasswordEncoder, Mockito.never())
                .encode(Mockito.anyString());

        Mockito.verify(mockUserRepository, Mockito.never())
                .update(Mockito.any(User.class));
    }

    @Test
    public void register_Should_CallRepository_When_UsernameAndEmailAreUnique() {
        RegisterUserDto dto = createRegisterUserDto();

        Mockito.when(mockUserRepository.existsByUsername(dto.getUsername()))
                .thenReturn(false);

        Mockito.when(mockUserRepository.existsByEmail(dto.getEmail()))
                .thenReturn(false);

        Mockito.when(mockPasswordEncoder.encode(dto.getPassword()))
                .thenReturn("encodedPassword");

        userService.register(dto);

        Mockito.verify(mockUserRepository, Mockito.times(1))
                .create(Mockito.any(User.class));
    }

    @Test
    public void register_Should_ThrowException_When_UsernameAlreadyExists() {
        RegisterUserDto dto = createRegisterUserDto();

        Mockito.when(mockUserRepository.existsByUsername(dto.getUsername()))
                .thenReturn(true);

        Assertions.assertThrows(
                DuplicateEntityException.class,
                () -> userService.register(dto)
        );

        Mockito.verify(mockUserRepository, Mockito.never())
                .create(Mockito.any(User.class));
    }

    @Test
    public void register_Should_ThrowException_When_EmailAlreadyExists() {
        RegisterUserDto dto = createRegisterUserDto();

        Mockito.when(mockUserRepository.existsByUsername(dto.getUsername()))
                .thenReturn(false);

        Mockito.when(mockUserRepository.existsByEmail(dto.getEmail()))
                .thenReturn(true);

        Assertions.assertThrows(
                DuplicateEntityException.class,
                () -> userService.register(dto)
        );

        Mockito.verify(mockUserRepository, Mockito.never())
                .create(Mockito.any(User.class));
    }

    @Test
    public void update_Should_CallRepository_When_EmailIsNotTaken() {
        User existingUser = createMockUser();
        User updatedUser = createUpdatedUser();

        Mockito.when(mockUserRepository.get(Mockito.anyInt()))
                .thenReturn(existingUser);

        Mockito.when(mockUserRepository.existsByEmail(updatedUser.getEmail()))
                .thenReturn(false);

        userService.update(1, updatedUser);

        Assertions.assertEquals(updatedUser.getFirstName(), existingUser.getFirstName());
        Assertions.assertEquals(updatedUser.getLastName(), existingUser.getLastName());
        Assertions.assertEquals(updatedUser.getEmail(), existingUser.getEmail());
        Assertions.assertEquals(updatedUser.getProfilePhotoUrl(), existingUser.getProfilePhotoUrl());

        Mockito.verify(mockUserRepository, Mockito.times(1))
                .update(existingUser);
    }

    @Test
    public void update_Should_ThrowException_When_UsernameIsProvided() {
        User updatedUser = createUpdatedUser();
        updatedUser.setUsername("newUsername");

        Assertions.assertThrows(
                IllegalArgumentException.class,
                () -> userService.update(1, updatedUser)
        );

        Mockito.verify(mockUserRepository, Mockito.never())
                .get(Mockito.anyInt());

        Mockito.verify(mockUserRepository, Mockito.never())
                .update(Mockito.any(User.class));
    }

    @Test
    public void update_Should_ThrowException_When_EmailIsAlreadyTaken() {
        User existingUser = createMockUser();
        User updatedUser = createUpdatedUser();

        Mockito.when(mockUserRepository.get(Mockito.anyInt()))
                .thenReturn(existingUser);

        Mockito.when(mockUserRepository.existsByEmail(updatedUser.getEmail()))
                .thenReturn(true);

        Assertions.assertThrows(
                DuplicateEntityException.class,
                () -> userService.update(1, updatedUser)
        );

        Mockito.verify(mockUserRepository, Mockito.never())
                .update(Mockito.any(User.class));
    }

    @Test
    public void block_Should_SetBlockedTrue() {
        User mockUser = createMockUser();

        Mockito.when(mockUserRepository.get(Mockito.anyInt()))
                .thenReturn(mockUser);

        userService.block(1);

        Assertions.assertTrue(mockUser.isBlocked());

        Mockito.verify(mockUserRepository, Mockito.times(1))
                .update(mockUser);
    }

    @Test
    public void unblock_Should_SetBlockedFalse() {
        User mockUser = createMockUser();
        mockUser.setBlocked(true);

        Mockito.when(mockUserRepository.get(Mockito.anyInt()))
                .thenReturn(mockUser);

        userService.unblock(1);

        Assertions.assertFalse(mockUser.isBlocked());

        Mockito.verify(mockUserRepository, Mockito.times(1))
                .update(mockUser);
    }

    @Test
    public void promoteToAdmin_Should_SetRoleAdmin() {
        User mockUser = createMockUser();

        Mockito.when(mockUserRepository.get(Mockito.anyInt()))
                .thenReturn(mockUser);

        userService.promoteToAdmin(1);

        Assertions.assertEquals(Role.ROLE_ADMIN, mockUser.getRole());

        Mockito.verify(mockUserRepository, Mockito.times(1))
                .update(mockUser);
    }

    @Test
    public void countAllUsers_Should_ReturnCount_When_UsersExist() {
        long expectedCount = 30L;
        Mockito.when(mockUserRepository.countAllUsers()).thenReturn(expectedCount);

        long actualCount = userService.countAllUsers();

        Assertions.assertEquals(expectedCount, actualCount);
        Mockito.verify(mockUserRepository,
                Mockito.times(1))
                .countAllUsers();
    }

    @Test
    public void countAllUsers_Should_Return0_When_NoUsersExist() {
        long expectedCount = 0L;
        Mockito.when(mockUserRepository.countAllUsers()).thenReturn(expectedCount);

        long actualCount = userService.countAllUsers();

        Assertions.assertEquals(expectedCount, actualCount);
        Mockito.verify(mockUserRepository,
                Mockito.times(1))
                .countAllUsers();
    }
}
