package g.proux.model;

import g.proux.enumeration.ElementType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class Map {

    private Integer height;
    private Integer width;
    private List<Element> elements = new ArrayList<>();

    public String writeLine() {
        return ElementType.MAP + " - " + this.width + " - " + this.height;
    }

}
