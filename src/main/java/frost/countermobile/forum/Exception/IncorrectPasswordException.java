package frost.countermobile.forum.Exception;

public class IncorrectPasswordException extends RuntimeException{

    int statusCode;

    public IncorrectPasswordException(String message, int statusCode) {
        super(message);
        this.statusCode = statusCode;
    }

    public int getStatusCode() {
        return statusCode;
    }
}
