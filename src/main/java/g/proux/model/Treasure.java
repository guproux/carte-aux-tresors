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

}
