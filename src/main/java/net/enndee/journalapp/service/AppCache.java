package net.enndee.journalapp.service;

import jakarta.annotation.PostConstruct;
import net.enndee.journalapp.entity.ConfigJournalEntity;
import net.enndee.journalapp.repository.ConfigJournal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class AppCache {

    @Autowired
    private ConfigJournal configJournal;

    public Map<String, String> APP_CACHE = new HashMap<>();

    @PostConstruct
    public void init() {
        List<ConfigJournalEntity> all = configJournal.findAll();
        for (ConfigJournalEntity configJournalEntity : all) {
            APP_CACHE.put(
                    configJournalEntity.getKey(),
                    configJournalEntity.getValue()
            );
        }
    }
}