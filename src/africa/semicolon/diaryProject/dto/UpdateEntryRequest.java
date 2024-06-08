package africa.semicolon.diaryProject.dto;

import lombok.Data;

@Data
public class UpdateEntryRequest {
    private String updatedTitle;
    private String updatedBody;
    private String id;

}
