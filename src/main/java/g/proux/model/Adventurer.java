package g.proux.model;

import g.proux.enumeration.ElementType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class Adventurer extends BlockingElement {

    private String name;
    private String orientation;
    private List<String> actions;

    @Override
    public String getType() {
        return ElementType.ADVENTURER;
    }

    @Override
    public String toString() {
        return this.getType() + " (" + this.name + ")";
    }

}
