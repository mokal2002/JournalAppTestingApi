//package com.mokal.journalApp.service;
//
//
//import com.mokal.journalApp.Repo.UserRepo;
//import com.mokal.journalApp.entity.User;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//
//import static org.junit.jupiter.api.Assertions.assertNotNull;
//import static org.junit.jupiter.api.Assertions.assertTrue;
//
//@SpringBootTest
//public class UserServiceTest {
//    @Autowired
//    private UserRepo userRepo;
//
//    @Test
//    public void testFindByUserName(){
//        assertNotNull(userRepo.findByUserName("Ram"));
//        User user =userRepo.findByUserName("Ram");
//        assertTrue(!user.getJournalEntries().isEmpty());
//    }
//}
