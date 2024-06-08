package africa.semicolon.diaryProject.services;

import africa.semicolon.diaryProject.data.models.Diary;
import africa.semicolon.diaryProject.data.models.Entry;
import africa.semicolon.diaryProject.data.repositories.DiaryRepository;
import africa.semicolon.diaryProject.dto.*;

import africa.semicolon.diaryProject.exceptions.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class DiaryServiceImplementation implements DiaryService{

    @Autowired
    private DiaryRepository diaryRepository;

    @Autowired
    private EntryService entryService;


    @Override
    public void register(RegisterRequest registerRequest) {
        validateUsername(registerRequest);
        validatePassword(registerRequest);
        Diary diary = new Diary();
        diary.setUsername(registerRequest.getUsername());
        diary.setPassword(registerRequest.getPassword());
        diary.setLocked(false);
        diaryRepository.save(diary);
    }
    private void validateUsername(RegisterRequest registerRequest) {
        if (isUsernameNull(registerRequest)) throw new UsernameException("Username cannot be null. Please enter a username\n");
        if (IsUsernameExisting(registerRequest)) throw new UsernameException(String.format("%s is unavailable. Please select a different one%n", registerRequest.getUsername()));
        if (isUsernameEmpty(registerRequest)) throw new UsernameException("Username field cannot be empty. Please enter a username\n");
        if (isUsernameWithSpaceCharacter(registerRequest)) throw new IllegalArgumentException("Username cannot contain characters. Please enter a valid username\n");;
    }

    private void validatePassword(RegisterRequest registerRequest) {
        if(isPasswordEmpty(registerRequest)) throw new InvalidPasswordException("Password field cannot be empty. Please enter a valid password");
    }

    private boolean IsUsernameExisting(RegisterRequest registerRequest) {
        return diaryRepository.existsById(registerRequest.getUsername());
    }
    private boolean isUsernameEmpty(RegisterRequest registerRequest) {
        return registerRequest.getUsername().isEmpty();
    }
    
    private boolean isUsernameNull(RegisterRequest registerRequest) {
        return registerRequest.getUsername() == null;
    }

    private boolean isUsernameWithSpaceCharacter(RegisterRequest registerRequest) {
        return registerRequest.getUsername().contains(" ");
    }
    private boolean isPasswordEmpty(RegisterRequest registerRequest) {
        return registerRequest.getPassword().isEmpty();
    }

    @Override
    public void login(LoginRequest loginRequest) {
        Optional<Diary> diary = diaryRepository.findById(loginRequest.getUsername());
        if(diary.isEmpty()) throw new DiaryNotFoundException(("Invalid Login Details. Please Try Again"));
        if (!diary.get().getPassword().equals(loginRequest.getPassword())) throw new InvalidPasswordException(("Invalid Login Details. Please Try Again"));
        diary.get().setLocked(false);
        diaryRepository.save(diary.get());
    }

    @Override
    public void logout(LogoutRequest logoutRequest) {
        Optional<Diary> diary = diaryRepository.findById(logoutRequest.getUsername());
        if (diary.isEmpty()) throw new DiaryNotFoundException(String.format("%s does not exist", logoutRequest.getUsername()));
        diary.get().setLocked(true);
        diaryRepository.save(diary.get());
    }

    @Override
    public void createEntry(CreateEntryRequest createEntryRequest) {
        Optional<Diary> diary = diaryRepository.findById(createEntryRequest.getUsername());
        if (diary.isEmpty()) throw new DiaryNotFoundException(String.format("%s does not exist. Please Try Again", createEntryRequest.getUsername()));
        if(diary.get().isLocked()) throw new DiaryLockStateException(String.format("%s is locked. Please Unlock Your Diary", createEntryRequest.getUsername()));
        entryService.createEntry(createEntryRequest);
    }

    @Override
    public void updateEntry(UpdateEntryRequest updateEntryRequest) {
        entryService.updateEntry(updateEntryRequest);
    }

    @Override
    public void deleteEntry(DeleteEntryRequest deleteEntryRequest) {
        Diary diary = findByUsername(deleteEntryRequest.getUsername());
        if(diary.isLocked()) throw new DiaryLockStateException(String.format("%s is locked. Please Unlock Your Diary", deleteEntryRequest.getUsername()));
        if (!diary.getPassword().equals(deleteEntryRequest.getPassword())) throw new InvalidPasswordException("Invalid Password. Please Try Again");
        entryService.deleteEntry(deleteEntryRequest);
    }

    @Override
    public List<Entry> findAllEntries(String username) {
        Optional<Diary> diary  = diaryRepository.findById(username);
        if (diary.isEmpty()) throw new DiaryNotFoundException("Diary not found. Please Try Again");
        if(diary.get().isLocked()) throw new DiaryLockStateException(String.format("%s is locked. Please Unlock Your Diary", username));
        if (entryService.findByUsername(username).isEmpty()) throw new EntryNotFoundException("No Entries found. Please create an Entry");
        return entryService.findByUsername(username);
    }

    @Override
    public void deleteDiary(String username) {
        Optional<Diary> diary  = diaryRepository.findById(username);
        if (diary.isEmpty()) throw new DiaryNotFoundException("Diary not found. Please Try Again");
        if(diary.get().isLocked()) throw new DiaryLockStateException(String.format("%s is locked. Please Unlock Your Diary", username));
        diaryRepository.delete(diary.get());
    }

    @Override
    public Diary findByUsername(String username) {
        Optional<Diary> diary = diaryRepository.findById(username);
        if (diary.isEmpty()) throw new DiaryNotFoundException("Diary not found. Please Try Again");
        if(diary.get().isLocked()) throw new DiaryLockStateException(String.format("%s is locked. Please Unlock Your Diary", username));
        return diary.get();
    }

    @Override
    public List<Diary> findAllDiaries() {
        return diaryRepository.findAll();
    }

    @Override
    public long count() {
        return diaryRepository.count();
    }

    @Override
    public String getLastCreatedEntryId() {
        return entryService.getLastCreatedEntryId();
    }

}

