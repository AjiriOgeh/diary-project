package africa.semicolon.diaryProject;

import africa.semicolon.diaryProject.dto.*;
import africa.semicolon.diaryProject.services.DiaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class DiaryController {

    @Autowired
    private DiaryService diaryService;

    @PostMapping("/Register")
    public String register(@RequestBody RegisterRequest registerRequest) {
        try {
            diaryService.register(registerRequest);
            return String.format("%s diary has been successfully created%n", registerRequest.getUsername());
        }
        catch (Exception error) {
            return error.getMessage();
        }
    }

    @PatchMapping("/Login")
    public String login(@RequestBody LoginRequest loginRequest) {
        try {
            diaryService.login(loginRequest);
            return String.format("%s has successfully logged in%n", loginRequest.getUsername());
        }
        catch (Exception error) {
            return error.getMessage();
        }
    }

    @PatchMapping("/Logout")
    public String logout(@RequestBody LogoutRequest logoutRequest) {
        try {
            diaryService.logout(logoutRequest);
            return String.format("%s has been logged out%n", logoutRequest.getUsername());
        }
        catch (Exception error) {
            return error.getMessage();
        }
    }

    @PostMapping("/CreateEntry")
    public String createEntry(@RequestBody CreateEntryRequest createEntryRequest) {
        try {
            diaryService.createEntry(createEntryRequest);
            return String.format("%s has been created in %s%n" +
                    "Entry ID is %s%n", createEntryRequest.getTitle(), createEntryRequest.getUsername(), diaryService.getLastCreatedEntryId());
        }
        catch (Exception error) {
            return error.getMessage();
        }
    }

    @DeleteMapping("/DeleteEntry")
    public String deleteEntry(@RequestBody DeleteEntryRequest deleteEntryRequest) {
        try {
            diaryService.deleteEntry(deleteEntryRequest);
            return String.format("Entry %s in %s has been deleted%n", deleteEntryRequest.getId(), deleteEntryRequest.getUsername());
        }
        catch (Exception error) {
            return error.getMessage();
        }
    }

    @DeleteMapping("/DeleteDiary/{name}")
    public String deleteDiary(@PathVariable("name") String username) {
        try {
            diaryService.deleteDiary(username);
            return String.format("%s has been deleted", username);
        }
        catch (Exception error) {
            return error.getMessage();
        }
    }

    @PatchMapping("/UpdateEntry")
    public String updateEntry(@RequestBody UpdateEntryRequest updateEntryRequest) {
        try {
            diaryService.updateEntry(updateEntryRequest);
            return String.format("Entry %s has been updated%n", updateEntryRequest.getId());
        }
        catch (Exception error) {
            return error.getMessage();
        }
    }

    @GetMapping("/FindAllEntries/{name}")
    public String findAllEntries(@PathVariable("name") String username) {
        try {
            return String.format("%s ", diaryService.findAllEntries(username));
        }
        catch (Exception error) {
            return error.getMessage();
        }
    }
}