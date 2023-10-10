package g.proux.exception;

public class ElementCreationException extends Exception {

    private String code;

    public ElementCreationException(String message, String code) {
        super(message);
        this.code = code;
    }

}
