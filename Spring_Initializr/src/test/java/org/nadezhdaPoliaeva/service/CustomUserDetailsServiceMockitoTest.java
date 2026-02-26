package org.nadezhdaPoliaeva.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.nadezhdaPoliaeva.model.AppUser;
import org.nadezhdaPoliaeva.repository.UserRepository;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CustomUserDetailsServiceMockitoTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private CustomUserDetailsService customUserDetailsService;

    @Test
    void loadUserByUsername_whenUserExists_returnsUserDetails() {
        AppUser user = new AppUser();
        user.setUsername("nadya");
        user.setPassword("123");
        user.setRole("USER");

        when(userRepository.findByUsername("nadya")).thenReturn(Optional.of(user));

        var details = customUserDetailsService.loadUserByUsername("nadya");

        assertEquals("nadya", details.getUsername());
        verify(userRepository).findByUsername("nadya");
    }

    @Test
    void loadUserByUsername_whenUserNotFound_throwsException() {
        when(userRepository.findByUsername("ghost")).thenReturn(Optional.empty());

        assertThrows(UsernameNotFoundException.class,
                () -> customUserDetailsService.loadUserByUsername("ghost"));

        verify(userRepository).findByUsername("ghost");
    }
}