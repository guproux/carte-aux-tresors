package g.proux.exception;

import lombok.Getter;

@Getter
public class NotAllowedActionException extends Exception {

    private final String code;

    public NotAllowedActionException(String message, String code) {
        super(message);
        this.code = code;
    }

}
