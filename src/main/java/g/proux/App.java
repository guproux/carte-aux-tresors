package g.proux;

import g.proux.controller.AdventureController;
import g.proux.controller.service.AdventurerService;
import g.proux.controller.service.MapService;
import g.proux.exception.ElementCreationException;
import g.proux.view.MapView;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

@Slf4j
public class App
{

    public static void main( String[] args ) {
        MapService mapService = new MapService();
        AdventurerService adventurerService = new AdventurerService(mapService);

        MapView view = new MapView(mapService);
        AdventureController controller = new AdventureController(view, mapService, adventurerService);

        String inputFileName = "src/main/resources/input/adventure.txt";
        String outputFileName = "src/main/resources/output/adventure.txt";

        try {
            // Première étape lire le fichier pour créer la carte et ses éléments
            controller.readFileAndCreateMap(inputFileName);

            // Deuxième étape la recherche des trésors par les aventuriers
            controller.searchTreasures();

            // Troisième étape enregistrement de la simulation dans un fichier
            controller.writeMapIntoFile(outputFileName);
        } catch (IOException ex) {
            log.error("Erreur pendant la simulation, un des fichiers n'est pas disponible en lecture ou en écriture. {}", ex.getMessage());
        } catch (ElementCreationException ex) {
            log.error("Erreur pendant la simulation, un élément n'a pas pu être créé. {}", ex.getMessage());
        }
    }

}
