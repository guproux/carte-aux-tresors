package g.proux.exception;

import lombok.Getter;

@Getter
public class ElementCreationException extends Exception {

    private final String code;

    public ElementCreationException(String message, String code) {
        super(message);
        this.code = code;
    }

}
