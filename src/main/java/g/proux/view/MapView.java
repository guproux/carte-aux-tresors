package g.proux.view;

import g.proux.controller.service.MapService;
import g.proux.model.Element;
import g.proux.model.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
public class MapView {

    private final MapService mapService;

    /**
     * Affiche la carte aux trésors.
     *
     * @param map la carte qu'on affiche
     */
    public void printMap(Map map) {
        log.info("Carte aux trésors : ");
        for (int i = 0; i < map.getHeight(); i++) {
            for (int j = 0; j < map.getWidth(); j++) {
                Optional<Element> oElement = this.mapService.getElementByCoordinates(map, j, i);

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
