package com.team_3.travel_forum.servicesTests;

import com.team_3.travel_forum.exceptions.EntityNotFoundException;
import com.team_3.travel_forum.models.User;
import com.team_3.travel_forum.repositories.UserRepository;
import com.team_3.travel_forum.services.SecurityUserDetailsService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import static com.team_3.travel_forum.Helpers.createMockUser;

@ExtendWith(MockitoExtension.class)
public class SecurityUserDetailsServiceTests {

    @Mock
    UserRepository mockUserRepository;

    @InjectMocks
    SecurityUserDetailsService securityUserDetailsService;

    @Test
    public void loadUserByUsername_Should_ThrowException_When_UsernameDoesNotExist() {
        String username = "username";
        Mockito.when(mockUserRepository.get(username))
                .thenThrow(new EntityNotFoundException("User", "username", username));

        Assertions.assertThrows(UsernameNotFoundException.class,
                () -> securityUserDetailsService.loadUserByUsername(username));
        Mockito.verify(mockUserRepository, Mockito.times(1))
                .get(username);
    }

    @Test
    public void loadUserByUsername_Should_ReturnUser_When_UsernameExists() {
        String username = "username";
        User mockUser = createMockUser();
        mockUser.setUsername(username);
        Mockito.when(mockUserRepository.get(username)).thenReturn(mockUser);

        UserDetails userDetails = securityUserDetailsService.loadUserByUsername(username);

        Assertions.assertEquals(username, userDetails.getUsername());
        Mockito.verify(mockUserRepository, Mockito.times(1))
                .get(username);
    }
}
