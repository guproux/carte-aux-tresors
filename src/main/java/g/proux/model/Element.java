package g.proux.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public abstract sealed class Element permits Treasure, BlockingElement{

    private Integer x;
    private Integer y;

    public abstract String getType();

    public abstract String writeLine();

}
