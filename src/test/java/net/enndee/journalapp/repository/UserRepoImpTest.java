package net.enndee.journalapp.repository;

import net.enndee.journalapp.entity.User;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class UserRepoImpTest {

    @Autowired
    private UserRepoImp userRepoImp;


    @Test
    public void testGetUser(){

        List<User> users=userRepoImp.getUsersForSA();
        System.out.println(users);
    }
}
