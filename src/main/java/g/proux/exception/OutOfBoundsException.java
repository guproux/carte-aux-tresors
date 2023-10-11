package g.proux.exception;

public class OutOfBoundsException extends Exception {

    private String code;

    public OutOfBoundsException(String message, String code) {
        super(message);
        this.code = code;
    }

}
