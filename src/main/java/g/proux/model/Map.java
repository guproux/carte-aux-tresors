package g.proux.model;

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

}
