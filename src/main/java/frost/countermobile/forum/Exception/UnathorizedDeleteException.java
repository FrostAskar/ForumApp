package frost.countermobile.forum.Exception;

public class UnathorizedDeleteException extends RuntimeException{
    public UnathorizedDeleteException(String message){
        super(message);
    }
}
