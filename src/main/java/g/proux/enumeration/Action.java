package g.proux.enumeration;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class Action {

    public static final String MOVE = "A";
    public static final String TURN_RIGHT = "D";
    public static final String TURN_LEFT = "G";

}
