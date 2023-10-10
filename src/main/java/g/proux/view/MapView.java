package g.proux.view;

import g.proux.model.Element;
import g.proux.model.Map;
import lombok.extern.slf4j.Slf4j;

import java.util.Optional;

@Slf4j
public class MapView {

    /**
     * Affiche la carte aux trésors.
     *
     * @param map la carte qu'on affiche
     */
    public void printMap(Map map) {
        log.info("Carte aux trésors : ");
        for (int i = 0; i < map.getHeight(); i++) {
            for (int j = 0; j < map.getWidth(); j++) {
                Optional<Element> oElement = map.getElement(i, j);

                String stringToPrint;
                if (oElement.isPresent()) {
                    Element element = oElement.get();
                    stringToPrint = element.toString();
                } else {
                    stringToPrint = ".";
                }

                System.out.printf("%-20s", stringToPrint);
            }
            System.out.println();
        }
    }

}
