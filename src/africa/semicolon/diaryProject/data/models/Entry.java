package africa.semicolon.diaryProject.data.models;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Data
@Document
public class Entry {
    @Id
    private String id;
    private String title;
    private String body;
    private LocalDateTime dateCreated = LocalDateTime.now();
    private String username;

    @Override
    public String toString(){
            return String.format("Entry ID: %s%nEntry Title: %s%nEntry Body: %s%nEntry Date Created: %s%n", id , title, body, dateCreated) ;
    }

}
