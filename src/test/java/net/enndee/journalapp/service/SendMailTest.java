package net.enndee.journalapp.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class SendMailTest {
    @Autowired
    private  EmailService emailService;

    @Test
    public void sendEmail(){
        emailService.sendEmail("nddar191131@gmail.com","Email Test","Hello there testing java send mail");
    }
}
