package net.enndee.journalapp.service;

import net.enndee.journalapp.entity.JournalEntry;
import net.enndee.journalapp.entity.User;
import net.enndee.journalapp.repository.JournalEntryRepository;
import net.enndee.journalapp.repository.UserRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class JournalEntryService {
    @Autowired
    private JournalEntryRepository journalEntryRepository;
    @Autowired
    private UserService userService;
    @Autowired
    private UserRepository userRepository;

    @Transactional
    public void saveEntry(JournalEntry journalEntry) {
        try {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            String username = auth.getName();
            User user = userService.findByUsername(username);
            journalEntry.setDate(LocalDateTime.now());
            JournalEntry saved = journalEntryRepository.save(journalEntry);
            user.getJournalEntries().add(saved); // getJournalEntries()-> getter from lombok
            userService.saveUser(user);
        } catch (Exception e) {
            System.out.println(e);
            throw new RuntimeException("An error occurred while saving the entry. : " + e);
        }
    }

    public List<JournalEntry> getEntries() {
        return journalEntryRepository.findAll();
    }

    public Optional<JournalEntry> getEntry(ObjectId id) {
        return journalEntryRepository.findById(id);
    }

    public void deleteEntry(ObjectId id) {
        journalEntryRepository.deleteById(id);
    }

    public void deleteById(ObjectId id, String username) {
        User user = userRepository.findByUsername(username);
        boolean removed = user.getJournalEntries().removeIf(x -> x.getId().equals(id));
        if (removed) {
            userService.saveNewUser(user);
            journalEntryRepository.deleteById(id);
        }
    }
}
