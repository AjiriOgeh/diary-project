package africa.semicolon.diaryProject.services;

import africa.semicolon.diaryProject.data.models.Entry;
import africa.semicolon.diaryProject.dto.*;

import java.util.List;

public interface EntryService {
    void createEntry(CreateEntryRequest createEntryRequest);
    void updateEntry(UpdateEntryRequest updateEntryRequest) ;
    void deleteEntry(DeleteEntryRequest deleteEntryRequest); // username
    Entry findByEntryId(String id); // username
    long count();
    List<Entry> findAllEntries(); // delete all entries
    List<Entry> findByUsername(String username);
    String getLastCreatedEntryId(); // utils fold
}
