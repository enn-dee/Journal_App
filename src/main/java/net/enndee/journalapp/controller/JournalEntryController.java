package net.enndee.journalapp.controller;
import net.enndee.journalapp.entity.JournalEntry;
import net.enndee.journalapp.service.JournalEntryService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("journal")
public class JournalEntryController {

    @Autowired
    private JournalEntryService journalEntryService;

    @GetMapping
    public ResponseEntity<?> getAllGeneralEntriesOfUser() {
        List<JournalEntry> journalEntries = journalEntryService.getEntries();
        if(journalEntries != null && !journalEntries.isEmpty()){
        return ResponseEntity.status(HttpStatus.OK).body(journalEntries);
        }
        return  ResponseEntity.status(HttpStatus.OK).body("No entries yet");
    }

    @PostMapping
    public ResponseEntity<JournalEntry> createEntry(@RequestBody JournalEntry myEntry) {
        try{
            myEntry.setDate(LocalDateTime.now());
            journalEntryService.saveEntry(myEntry);
            return new ResponseEntity<>(myEntry, HttpStatus.CREATED);
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }


    }

    @GetMapping("id/{myId}")
    public ResponseEntity<JournalEntry> JournalById(@PathVariable ObjectId myId) {
        Optional<JournalEntry> journalEntry =  journalEntryService.getEntry(myId);
        return journalEntry.map(entry -> new ResponseEntity<>(entry, HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));

    }
@DeleteMapping("id/{myId}")
    public ResponseEntity<?> deleteJournalById(@PathVariable ObjectId myId){
        try{
            journalEntryService.deleteEntry(myId);
            return new ResponseEntity<>("Deleted",HttpStatus.OK);
        }catch(Exception e){
            return new ResponseEntity<>("something went wrong",HttpStatus.BAD_REQUEST);
        }

}

@PutMapping("id/{myId}")
    public ResponseEntity<?> updateJournalById(@PathVariable ObjectId myId, @RequestBody JournalEntry newEntry) {
        JournalEntry oldJournal = journalEntryService.getEntry(myId).orElse(null);
        if(oldJournal !=null){
            oldJournal.setTitle(newEntry.getTitle() !=null && !newEntry.getTitle().isEmpty() ? newEntry.getTitle() : oldJournal.getTitle());
            oldJournal.setContent(newEntry.getContent() !=null && !newEntry.getContent().isEmpty() ? newEntry.getContent() : oldJournal.getContent());
    journalEntryService.saveEntry(oldJournal);
            return  new ResponseEntity<>(oldJournal, HttpStatus.OK);
        }
    return new ResponseEntity<>("Not found", HttpStatus.NOT_FOUND);
}

}
