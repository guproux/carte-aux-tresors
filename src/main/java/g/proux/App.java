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
        MapView view = new MapView();
        MapService mapService = new MapService();

        AdventureController controller = new AdventureController(view, mapService);

        String fileName = "src/main/resources/input/adventure.txt";

        Map map = controller.readFileAndCreateMap(fileName);
    }

}
