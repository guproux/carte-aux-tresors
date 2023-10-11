package g.proux;

import g.proux.controller.AdventureController;
import g.proux.controller.service.MapService;
import g.proux.exception.ElementCreationException;
import g.proux.model.Map;
import g.proux.view.MapView;
import lombok.RequiredArgsConstructor;

import java.io.File;
import java.io.IOException;

public class App
{

    public static void main( String[] args ) throws IOException, ElementCreationException {
        MapService mapService = new MapService();

        MapView view = new MapView(mapService);
        AdventureController controller = new AdventureController(view, mapService);

        String fileName = "src/main/resources/input/adventure.txt";

        // Première étape lire le fichier pour créer la carte et ses éléments
        controller.readFileAndCreateMap(fileName);

        // Deuxième étape la recherche des trésors par les aventuriers
        controller.searchTreasures();
    }

}
