package g.proux.view;

import g.proux.controller.service.MapService;
import g.proux.exception.OutOfBoundsException;
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
            StringBuilder sb = new StringBuilder();

            for (int j = 0; j < map.getWidth(); j++) {
                Optional<Element> oElement;
                try {
                    oElement = this.mapService.getElementByCoordinates(map, j, i);
                } catch (OutOfBoundsException e) {
                    oElement = Optional.empty();
                }

                String stringToPrint;
                if (oElement.isPresent()) {
                    Element element = oElement.get();
                    stringToPrint = element.toString();
                } else {
                    stringToPrint = ".";
                }

                sb.append(String.format("%-20s", stringToPrint));
            }
            log.info(sb.toString());
        }
        log.info("");
    }

}
