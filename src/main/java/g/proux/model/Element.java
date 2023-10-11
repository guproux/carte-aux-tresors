package g.proux.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public abstract class Element {

    private Integer x;
    private Integer y;

    public abstract String getType();

    public abstract String writeLine();

}
