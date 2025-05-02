package com.mokal.journalApp.service;

import com.mokal.journalApp.Repo.JournalEntryRepo;
import com.mokal.journalApp.entity.JournalEntry;
import com.mokal.journalApp.entity.User;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class JournalEntryService {

    @Autowired
    private JournalEntryRepo journalEntryRepo;
    @Autowired
    private UserService userService;

    public JournalEntryService(JournalEntryRepo journalEntryRepo, UserService userService) {
        this.journalEntryRepo = journalEntryRepo;
        this.userService = userService;
    }


    @Transactional
    public void saveEntry(JournalEntry journalEntry, String userName) {
        User user = userService.findByUserName(userName);
        journalEntry.setDate(LocalDateTime.now());
        JournalEntry saved = journalEntryRepo.save(journalEntry);
        user.getJournalEntries().add(saved);
        userService.saveUser(user);
    }
    public void saveEntry(JournalEntry journalEntry) {
        journalEntryRepo.save(journalEntry);
    }

    public List<JournalEntry> getAllEntries() {
        return journalEntryRepo.findAll();
    }

    public Optional<JournalEntry> getEntryById(ObjectId id) {
        return journalEntryRepo.findById(id);
    }

    @Transactional
    public Boolean deleteEntryById(ObjectId id, String userName) {
        boolean removed = false;
        try {
            User user = userService.findByUserName(userName);
            removed = user.getJournalEntries().removeIf(x -> x.getId().equals(id));
            if (removed){
                userService.saveUser(user);
                journalEntryRepo.deleteById(id);
            }
        }catch (Exception e){
            System.out.println(e);
            throw new RuntimeException("An error occcured while deleting the entry.",e);
        }
        return removed;



    }

//    public List<JournalEntry> findByUserName(String userName){
//        return
//    }
}











//    public Optional<JournalEntry> updateEntry(ObjectId id) {
//
//        userService.saveEntry(id);
//
//        return journalEntryRepo.findById(id);
//}