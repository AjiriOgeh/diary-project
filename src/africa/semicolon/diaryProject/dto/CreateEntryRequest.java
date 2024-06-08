package africa.semicolon.diaryProject.dto;


import lombok.Data;

@Data
public class CreateEntryRequest {
    private String title;
    private String body;
    private String username;


}
