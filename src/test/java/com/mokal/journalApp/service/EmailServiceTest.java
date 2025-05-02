package com.mokal.journalApp.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class EmailServiceTest {

    @Autowired
    private EmailService emailService;

    @Test
    public void testSendEmail() {
        try {
            emailService.sendEmail(
                    "tadagaj794@hedotu.com",
                    "TestingMail",
                    "Hi Abhyas kar"
            );
            System.out.println("Email sent successfully.");
        }
        catch (Exception e ){
            System.out.println("Error");
        }
    }
}
