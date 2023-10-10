package g.proux.model;

import g.proux.enumeration.ElementType;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class Mountain extends BlockingElement {

    @Override
    public String getType() {
        return ElementType.MOUNTAIN;
    }

}
