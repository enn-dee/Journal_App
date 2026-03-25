package net.enndee.journalapp.repository;

import net.enndee.journalapp.entity.ConfigJournalEntity;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ConfigJournal  extends MongoRepository<ConfigJournalEntity, ObjectId> {
}
