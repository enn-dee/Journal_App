package net.enndee.journalapp.service;


import net.enndee.journalapp.entity.User;
import net.enndee.journalapp.repository.UserRepository;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

import static org.mockito.Mockito.*;

@SpringBootTest
@Disabled
public class TestUserService {
    // using Mockito
    UserRepository userRepository = mock(UserRepository.class);


    @Test
    public void testFindByUsername() {

        User mockUser = User.builder()
                .username("nadeem-ahmad")
                .password("12345")
                .build();

        when(userRepository.findByUsername("Ahmad")).thenReturn(mockUser);

        User user = userRepository.findByUsername("Ahmad");
        User newUser = User.builder()
                .username("nadeem-ahmad")
                .password("12345")
                .build();

        assertEquals(newUser, user);

        verify(userRepository).findByUsername("Ahmad");
    }

}
