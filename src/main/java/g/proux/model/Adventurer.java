package g.proux.model;

import g.proux.enumeration.ElementType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public final class Adventurer extends BlockingElement {

    private String name;
    private String orientation;
    private List<String> actions;
    private Integer loot;

    @Override
    public String getType() {
        return ElementType.ADVENTURER;
    }

    @Override
    public String writeLine() {
        return getType() + " - " + this.getName() + " - " + this.getX() + " - " + this.getY() + " - " + this.getOrientation() + " - " + this.getLoot();
    }

    @Override
    public String toString() {
        return this.getType() + " (" + this.name + ")";
    }

}
