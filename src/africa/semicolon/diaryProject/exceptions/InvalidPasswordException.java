package africa.semicolon.diaryProject.exceptions;

public class InvalidPasswordException extends RuntimeException{

    public InvalidPasswordException(String message){
        super(message);
    }
}
