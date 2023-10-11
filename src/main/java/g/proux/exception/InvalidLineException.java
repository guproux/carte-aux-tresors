package g.proux.exception;

import lombok.Getter;

@Getter
public class InvalidLineException extends Exception {

    private final String code;

    public InvalidLineException(String message, String code) {
        super(message);
        this.code = code;
    }

}
