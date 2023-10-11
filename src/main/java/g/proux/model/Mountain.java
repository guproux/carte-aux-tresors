package g.proux.model;

import g.proux.enumeration.ElementType;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class Mountain extends BlockingElement {

    @Override
    public String getType() {
        return ElementType.MOUNTAIN;
    }

    @Override
    public String writeLine() {
        return getType() + " - " + this.getX() + " - " + this.getY();
    }

    @Override
    public String toString() {
        return this.getType();
    }

}
