package com.mokal.journalApp.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;


@Document(collection = "config_journal_app")
@Data //This is Lombok its uses getter setter etc
@NoArgsConstructor
public class ConfigJournalAppEntity {
    private String key;

    private String value;
}

