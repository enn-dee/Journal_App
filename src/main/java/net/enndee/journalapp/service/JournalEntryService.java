package net.enndee.journalapp.service;

import net.enndee.journalapp.entity.JournalEntry;
import net.enndee.journalapp.repository.JournalEntryRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

//@Component
@Service
public class JournalEntryService {
@Autowired
    private JournalEntryRepository journalEntryRepository;

public void saveEntry(JournalEntry journalEntry){
    journalEntryRepository.save(journalEntry);
}

public List<JournalEntry> getEntries(){
    return journalEntryRepository.findAll();
}
public Optional<JournalEntry> getEntry(ObjectId id){
    return journalEntryRepository.findById(id);
}

public void deleteEntry(ObjectId id){
     journalEntryRepository.deleteById(id);
}
}
