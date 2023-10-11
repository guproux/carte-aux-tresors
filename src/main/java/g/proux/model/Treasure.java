package g.proux.model;

import g.proux.enumeration.ElementType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class Treasure extends Element {

    private Integer value;

    @Override
    public String getType() {
        return ElementType.TREASURE;
    }

    @Override
    public String writeLine() {
        return getType() + " - " + this.getX() + " - " + this.getY() + " - " + this.getValue();
    }

    @Override
    public String toString() {
        return this.getType() + " (" + this.value + ")";
    }

}
