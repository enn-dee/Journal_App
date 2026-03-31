package net.enndee.journalapp.service;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@Slf4j
public class SendMailTest {
    @Autowired
    private  EmailService emailService;

    @Test
    public void sendEmail(){
        emailService.sendEmail("nddar191131@gmail.com","Email Test","Hello there testing java send mail");
    }
}
