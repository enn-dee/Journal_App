package net.enndee.journalapp.controller;

import net.enndee.journalapp.entity.JournalEntry;
import net.enndee.journalapp.entity.User;
import net.enndee.journalapp.service.JournalEntryService;
import net.enndee.journalapp.service.UserService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("journal")
public class JournalEntryController {

    @Autowired
    private JournalEntryService journalEntryService;
    @Autowired
    private UserService userService;

    @GetMapping
    public ResponseEntity<?> getAllGeneralEntriesOfUser() {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();

        User user = userService.findByUsername(username);

        List<JournalEntry> userJournalEntries = user.getJournalEntries(); //getJournalEntries() -> getter from lombok

        if (userJournalEntries != null && !userJournalEntries.isEmpty()) {
            return ResponseEntity.status(HttpStatus.OK).body(userJournalEntries);
        }
        return ResponseEntity.status(HttpStatus.OK).body("No entries yet");
    }

    @PostMapping()
    public ResponseEntity<JournalEntry> createEntry(@RequestBody JournalEntry myEntry) {
        try {
//            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
//            String username = auth.getName();
            journalEntryService.saveEntry(myEntry);
            return new ResponseEntity<>(myEntry, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }


    }

    @GetMapping("id/{myId}")
    public ResponseEntity<JournalEntry> JournalById(@PathVariable ObjectId myId) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        User user = userService.findByUsername(username);
        List<JournalEntry> collect = user.getJournalEntries().stream().filter(x -> x.getId().equals(myId)).toList();

        if (!collect.isEmpty()) {
            Optional<JournalEntry> journalEntry = journalEntryService.getEntry(myId);
            if (journalEntry.isPresent()) {
                return journalEntry.map(entry -> new ResponseEntity<>(entry, HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
            }
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("id/{myId}")
    public ResponseEntity<?> deleteJournalById(@PathVariable ObjectId myId) {
        try {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            String username = auth.getName();

            journalEntryService.deleteById(myId, username);
//            journalEntryService.deleteEntry(myId);
            return new ResponseEntity<>("Deleted", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("something went wrong", HttpStatus.BAD_REQUEST);
        }

    }

    @PutMapping("id/{myId}")
    public ResponseEntity<?> updateJournalById(@PathVariable ObjectId myId, @RequestBody JournalEntry newEntry) {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        User user = userService.findByUsername(username);
        List<JournalEntry> collect = user.getJournalEntries().stream().filter(x -> x.getId().equals(myId)).toList();

        if (!collect.isEmpty()) {
            Optional<JournalEntry> oldJournalEntry = journalEntryService.getEntry(myId);
            if (oldJournalEntry.isPresent()) {
                JournalEntry oldJournal = oldJournalEntry.get();
                oldJournal.setTitle(!newEntry.getTitle()
                        .isEmpty() ? newEntry.getTitle() : oldJournal.getTitle());

                oldJournal.setContent(newEntry.getContent() != null && !newEntry.getContent()
                        .isEmpty() ? newEntry.getContent() : oldJournal.getContent());
                journalEntryService.saveEntry(oldJournal);
                return  new ResponseEntity<>(oldJournalEntry,HttpStatus.OK);
            }
        }


//        if (oldJournal != null) {
//oldJournal.setTitle(newEntry.getTitle() != null && !newEntry.getTitle().isEmpty() ? newEntry.getTitle() : oldJournal.getTitle());
//            oldJournal.setContent(newEntry.getContent() != null && !newEntry.getContent().isEmpty() ? newEntry.getContent() : oldJournal.getContent());
///
//            return new ResponseEntity<>(oldJournal, HttpStatus.OK);
//        }
        return new ResponseEntity<>("Not found", HttpStatus.NOT_FOUND);
    }

}
