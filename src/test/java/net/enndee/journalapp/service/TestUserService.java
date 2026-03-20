package net.enndee.journalapp.service;


import net.enndee.journalapp.entity.User;
import net.enndee.journalapp.repository.UserRepository;
import org.junit.jupiter.api.Test;

import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

import static org.mockito.Mockito.*;

@SpringBootTest
public class TestUserService {
    // using Mockito
    UserRepository userRepository = mock(UserRepository.class);


    @Test
    public void testFindByUsername(){
        when(userRepository.findByUsername("Ahmad")).thenReturn(new User("nadeem-ahmad", "12345"));
        net.enndee.journalapp.entity.User user = userRepository.findByUsername("Ahmad");
        User newUser  = new User("nadeem-ahmad","12345");
        assertEquals(newUser, user);
        verify(userRepository).findByUsername("Ahmad");

    }

}
