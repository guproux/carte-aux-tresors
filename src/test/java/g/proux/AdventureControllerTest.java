package g.proux;

import g.proux.controller.AdventureController;
import g.proux.controller.service.AdventurerService;
import g.proux.controller.service.MapService;
import g.proux.model.Map;
import g.proux.view.MapView;
import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;

public class AdventureControllerTest {

    private AdventureController adventureController;

    private MapService mapService;
    private AdventurerService adventurerService;

    private MapView mapView;

    @BeforeEach
    void setUp() {
        this.mapService = mock(MapService.class);
        this.adventurerService = mock(AdventurerService.class);
        this.mapView = mock(MapView.class);
        this.adventureController = new AdventureController(this.mapView, this.mapService, this.adventurerService);
    }

    @Test
    @SneakyThrows
    public void testReadFileAndCreateMap() {
        String testFileName = "src/test/resources/map/adventure.txt";

        doNothing().when(this.mapService).initMap(any(Map.class), any());
        doNothing().when(this.mapService).addMountain(any(Map.class), any(), anyInt());
        doNothing().when(this.mapService).addTreasure(any(Map.class), any(), anyInt());
        doNothing().when(this.mapService).addAdventurer(any(Map.class), any(), anyInt());
        doNothing().when(this.mapView).printMap(any(Map.class));

        this.adventureController.readFileAndCreateMap(testFileName);

        verify(this.mapService, times(1)).initMap(any(Map.class), any());
        verify(this.mapService, times(2)).addMountain(any(Map.class), any(), anyInt());
        verify(this.mapService, times(2)).addTreasure(any(Map.class), any(), anyInt());
        verify(this.mapService, times(1)).addAdventurer(any(Map.class), any(), anyInt());
        verify(this.mapView, times(1)).printMap(any(Map.class));
    }

}
