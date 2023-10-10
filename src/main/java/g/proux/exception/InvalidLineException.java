package g.proux.exception;

public class InvalidLineException extends Exception {

    private String code;

    public InvalidLineException(String message, String code) {
        super(message);
        this.code = code;
    }

}
