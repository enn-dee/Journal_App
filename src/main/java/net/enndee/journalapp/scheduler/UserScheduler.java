package net.enndee.journalapp.scheduler;

import net.enndee.journalapp.entity.JournalEntry;
import net.enndee.journalapp.entity.User;
import net.enndee.journalapp.enums.Sentiment;
import net.enndee.journalapp.repository.UserRepoImp;
import net.enndee.journalapp.service.EmailService;
import net.enndee.journalapp.service.SentimentAnalysisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@Component
public class UserScheduler {
    @Autowired
    private SentimentAnalysisService sentimentAnalysisService;

    @Autowired
    private UserRepoImp userRepoImp;

    @Autowired
    private EmailService emailService;

//    @Scheduled(cron = "0 * * ? * *")
    @Scheduled (cron = "0 0 9 * * Sun")
    public void fetchUsersAndSendMail() {

        List<User> users = userRepoImp.getUsersForSA();

        for (User user : users) {
            List<JournalEntry> journalEntries = user.getJournalEntries();

            List<Sentiment> sentiments = journalEntries.stream().filter(x -> x.getDate()
                            .isAfter(LocalDateTime.now().minusDays(7)))
                    .map(x -> x.getSentiment()).collect(Collectors.toList());

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
                emailService.sendEmail(user.getEmail(), "Sentiment for last 7 days ", mostFrequentSentiment.toString());
            }
        }
    }
}

