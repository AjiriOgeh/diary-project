package africa.semicolon.diaryProject.data.repositories;

import africa.semicolon.diaryProject.data.models.Entry;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EntryRepository extends MongoRepository<Entry, String> {

    List<Entry> findByUsername(String username);
}
