package africa.semicolon.diaryProject.dto;


import lombok.Data;

@Data
public class DeleteEntryRequest {
    private String id;
    private String password;
    private String username;

}

