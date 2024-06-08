package africa.semicolon.diaryProject.services;

import africa.semicolon.diaryProject.data.models.Diary;
import africa.semicolon.diaryProject.data.models.Entry;
import africa.semicolon.diaryProject.dto.*;


import java.util.List;

public interface DiaryService {
    void register(RegisterRequest registerRequest);
    void logout(LogoutRequest logoutRequest);
    void login(LoginRequest loginRequest);
    void createEntry(CreateEntryRequest createEntryRequest);
    void updateEntry(UpdateEntryRequest updateEntryRequest);
    void deleteEntry(DeleteEntryRequest deleteEntryRequest);
    List<Entry> findAllEntries(String username); // for the diary
    void deleteDiary(String username);
    Diary findByUsername(String username);
    List<Diary> findAllDiaries();
    long count();
    String getLastCreatedEntryId();
}
