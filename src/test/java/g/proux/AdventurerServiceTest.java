package g.proux;

import g.proux.controller.service.AdventurerService;
import g.proux.controller.service.MapService;
import g.proux.enumeration.Action;
import g.proux.enumeration.Orientation;
import g.proux.model.Adventurer;
import g.proux.model.Map;
import g.proux.model.Mountain;
import g.proux.model.Treasure;
import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.*;

public class AdventurerServiceTest {

    private AdventurerService adventurerService;

    private MapService mapService;

    @BeforeEach
    void setUp() {
        this.mapService = mock(MapService.class);
        this.adventurerService = new AdventurerService(mapService);
    }

    @Test
    public void testNominalDoActions() {
        List<String> actions = new ArrayList<>();
        actions.add(Action.MOVE);
        actions.add(Action.MOVE);
        actions.add(Action.TURN_RIGHT);
        actions.add(Action.MOVE);
        actions.add(Action.MOVE);
        actions.add(Action.TURN_LEFT);
        actions.add(Action.TURN_LEFT);
        actions.add(Action.MOVE);

        Adventurer adventurer = new Adventurer();
        adventurer.setName("Test");
        adventurer.setX(0);
        adventurer.setY(0);
        adventurer.setOrientation(Orientation.EAST);
        adventurer.setActions(actions);
        adventurer.setLoot(0);

        Mountain mountain = new Mountain();
        mountain.setX(0);
        mountain.setY(1);

        Treasure treasure = new Treasure();
        treasure.setX(2);
        treasure.setY(1);
        treasure.setValue(2);

        Map map = new Map();
        map.setWidth(3);
        map.setHeight(3);
        map.getElements().add(adventurer);
        map.getElements().add(mountain);
        map.getElements().add(treasure);


        when(this.mapService.getElementByCoordinates(map, 1, 0)).thenReturn(Optional.empty());
        when(this.mapService.getElementByCoordinates(map, 2, 0)).thenReturn(Optional.empty());
        when(this.mapService.getElementByCoordinates(map, 2, 1)).thenReturn(Optional.of(treasure));
        when(this.mapService.getElementByCoordinates(map, 2, 2)).thenReturn(Optional.empty());

        for (String action : actions) {
            this.adventurerService.doAction(adventurer, map);
        }

        assertThat(adventurer.getX()).isEqualTo(treasure.getX());
        assertThat(adventurer.getY()).isEqualTo(treasure.getY());
        assertThat(adventurer.getLoot()).isEqualTo(2);

        assertThat(treasure.getValue()).isZero();

        reset(this.mapService);
    }


}
