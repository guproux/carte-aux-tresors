package g.proux.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Getter
@Setter
@NoArgsConstructor
public class Map {

    private Integer height;
    private Integer width;
    private List<Element> elements = new ArrayList<>();

    public Optional<Element> getElement(int height, int width) {
        return this.elements.stream().filter(e -> e.getX().equals(width) && e.getY().equals(height)).findFirst();
    }

}
