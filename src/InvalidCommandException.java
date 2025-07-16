import java.util.InputMismatchException;

public class InvalidCommandException extends InputMismatchException {
    public InvalidCommandException() {
        System.out.println("Invalid command for movement");
    }
}
