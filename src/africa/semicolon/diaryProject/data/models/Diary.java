package africa.semicolon.diaryProject.data.models;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;

@Data
@Document
public class Diary {
    @Id
    private String username;
    private String password;
    private boolean isLocked;

}
