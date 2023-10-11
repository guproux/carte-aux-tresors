package g.proux.exception;

public class NotAllowedActionException extends Exception {

    private String code;

    public NotAllowedActionException(String message, String code) {
        super(message);
        this.code = code;
    }

}
