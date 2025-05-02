package com.mokal.journalApp.scheduler;

import com.mokal.journalApp.Repo.UserRepoImpl;
import com.mokal.journalApp.cache.AppCache;
import com.mokal.journalApp.entity.JournalEntry;
import com.mokal.journalApp.entity.User;
import com.mokal.journalApp.enums.Sentiment;
import com.mokal.journalApp.service.EmailService;
import com.mokal.journalApp.service.SentimentAnalysisService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class UserScheduler {

    private final EmailService emailService;
    private final UserRepoImpl userRepoImpl;
    private SentimentAnalysisService sentimentAnalysisService;
    private AppCache appCache;


    public UserScheduler(EmailService emailService, UserRepoImpl userRepoImpl, SentimentAnalysisService sentimentAnalysisService, AppCache appCache) {
        this.emailService = emailService;
        this.userRepoImpl = userRepoImpl;
        this.sentimentAnalysisService = sentimentAnalysisService;
        this.appCache = appCache;
    }

//    @Scheduled(cron = "0 0 9 * * SUN")
    public void fetchUserAndSendSaMail() {
        List<User> users = userRepoImpl.getUserForSA();
        for (User user : users) {
            List<JournalEntry> journalEntries = user.getJournalEntries();
            List<Sentiment> sentiments = journalEntries.stream().filter(x -> x.getDate().isAfter(LocalDateTime.now().minus(7, ChronoUnit.DAYS))).map(x -> x.getSentiment()).collect(Collectors.toList());
            Map<Sentiment, Integer> sentimentCounts = new HashMap<>();
            for (Sentiment sentiment : sentiments) {
                if (sentiment != null)
                    sentimentCounts.put(sentiment, sentimentCounts.getOrDefault(sentiment, 0) + 1);
            }
            Sentiment mostFrequentSentiment = null;
            int maxCount = 0;
            for (Map.Entry<Sentiment, Integer> entry : sentimentCounts.entrySet()) {
                if (entry.getValue() > maxCount) {
                    maxCount = entry.getValue();
                    mostFrequentSentiment = entry.getKey();
                }
            }
            if (mostFrequentSentiment != null) {
                emailService.sendEmail(user.getEmail(), "Sentiment for last 7 days", mostFrequentSentiment.toString());
            }
        }
    }

    @Scheduled(cron = "0 0/10 * ? * *")
    public void clearAppCache(){
        appCache.init();
    }
}
