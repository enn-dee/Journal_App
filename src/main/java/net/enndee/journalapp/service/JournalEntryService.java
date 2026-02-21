package net.enndee.journalapp.service;
import net.enndee.journalapp.entity.JournalEntry;
import net.enndee.journalapp.entity.User;
import net.enndee.journalapp.repository.JournalEntryRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class JournalEntryService {
@Autowired
    private JournalEntryRepository journalEntryRepository;
@Autowired
private  UserService userService;

public void saveEntry(JournalEntry journalEntry, String  username){
    User user = userService.findByUsername(username);
    journalEntry.setDate(LocalDateTime.now());
    JournalEntry saved =journalEntryRepository.save(journalEntry);
    user.getJournalEntries().add(saved); // getJournalEntries()-> getter from lombok
    userService.saveUser(user);
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
