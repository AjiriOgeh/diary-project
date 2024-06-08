package africa.semicolon.diaryProject.exceptions;

public class EntryNotFoundException extends RuntimeException{

    public EntryNotFoundException(String message) {
        super(message);
    }
}
