package com.mokal.journalApp.service;

import com.mokal.journalApp.Repo.JournalEntryRepo;
import com.mokal.journalApp.Repo.UserRepo;
import com.mokal.journalApp.entity.JournalEntry;
import com.mokal.journalApp.entity.User;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepo userRepo;

    public void saveEntry(User journalEntry) {
        userRepo.save(journalEntry);
    }

    public List<User> getAll() {
        return userRepo.findAll();
    }

    public Optional<User> getById(ObjectId id) {
        return userRepo.findById(id);
    }

    public void deleteById(ObjectId id) {
        userRepo.deleteById(id);
    }

    public User findByUserName(String userName){
        return userRepo.findByUserName(userName);
    }

    public Optional<User> updateUser(ObjectId id) {
        return userRepo.findById(id);
    }


}
