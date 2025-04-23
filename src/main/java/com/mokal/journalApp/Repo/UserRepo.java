package com.mokal.journalApp.Repo;

import com.mokal.journalApp.entity.JournalEntry;
import com.mokal.journalApp.entity.User;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepo extends MongoRepository<User, ObjectId> {

    User findByUserName(String username);
}
