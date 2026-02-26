package org.nadezhdaPoliaeva.service;

import org.junit.jupiter.api.Test;
import org.nadezhdaPoliaeva.model.AppUser;
import org.nadezhdaPoliaeva.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@SpringBootTest(classes = {
        CustomUserDetailsService.class,
        CustomUserDetailsServiceTestConfigurationTest.Config.class
})
class CustomUserDetailsServiceTestConfigurationTest {

    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    @Autowired
    private UserRepository userRepository; // это будет MOCK из Config

    @TestConfiguration
    static class Config {
        @Bean
        UserRepository userRepository() {
            return mock(UserRepository.class);
        }
    }

    @Test
    void loadUserByUsername_worksWithTestConfigurationBean() {
        AppUser user = new AppUser();
        user.setUsername("nadya");
        user.setPassword("123");
        user.setRole("USER");

        when(userRepository.findByUsername("nadya")).thenReturn(Optional.of(user));

        var details = customUserDetailsService.loadUserByUsername("nadya");

        assertEquals("nadya", details.getUsername());
        verify(userRepository).findByUsername("nadya");
        verifyNoMoreInteractions(userRepository);
    }
}