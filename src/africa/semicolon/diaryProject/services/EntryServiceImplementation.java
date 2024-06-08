package africa.semicolon.diaryProject.services;

import africa.semicolon.diaryProject.data.models.Entry;
import africa.semicolon.diaryProject.data.repositories.EntryRepository;
import africa.semicolon.diaryProject.dto.*;
import africa.semicolon.diaryProject.exceptions.DiaryNotFoundException;
import africa.semicolon.diaryProject.exceptions.EntryNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EntryServiceImplementation implements EntryService {

    @Autowired
    private EntryRepository entryRepository;

    @Override
    public void createEntry(CreateEntryRequest createEntryRequest) {
        Entry entry = new Entry();
        entry.setTitle(createEntryRequest.getTitle());
        entry.setBody(createEntryRequest.getBody());
        entry.setUsername(createEntryRequest.getUsername());
        entryRepository.save(entry);
    }

    @Override
    public void updateEntry(UpdateEntryRequest updateEntryRequest) {
        Optional<Entry> entry = entryRepository.findById(updateEntryRequest.getId());
        if (entry.isEmpty()) throw new EntryNotFoundException(String.format("Entry %s does not exist. Please Try Again ", updateEntryRequest.getId()));
        entry.get().setTitle(updateEntryRequest.getUpdatedTitle());
        entry.get().setBody(updateEntryRequest.getUpdatedBody());
        entryRepository.save(entry.get());
    }

    @Override
    public void deleteEntry(DeleteEntryRequest deleteEntryRequest) {
        Optional<Entry> entry = entryRepository.findById(deleteEntryRequest.getId()); // make sure the usernames match
        if (entry.isEmpty()) throw new EntryNotFoundException(String.format("Entry %s does not exist. Please Try Again ", deleteEntryRequest.getId()));
        if (!entry.get().getUsername().equalsIgnoreCase(deleteEntryRequest.getUsername())) throw new DiaryNotFoundException("diary not found");
        entryRepository.delete(entry.get());
    }

    @Override
    public Entry findByEntryId(String id) {
        Optional<Entry> entry = entryRepository.findById(id);
        if (entry.isEmpty()) throw new EntryNotFoundException(String.format("Entry %s does not exist. Please Try Again ", id));
        return entry.get();
    }

    @Override
    public long count() {
        return entryRepository.count();
    }

    @Override
    public List<Entry> findAllEntries() {
        return entryRepository.findAll();
    }

    @Override
    public List<Entry> findByUsername(String username) {
        if (entryRepository.findByUsername(username).isEmpty()) throw new EntryNotFoundException("No Entries found. Please create an Entry");
        return entryRepository.findByUsername(username);
    }


    @Override
    public String getLastCreatedEntryId() {
        int lastCreatedEntryIndex = entryRepository.findAll().size() - 1;
        return entryRepository.findAll().get(lastCreatedEntryIndex).getId();
    }
}
