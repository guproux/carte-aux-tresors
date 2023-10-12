package g.proux;

import g.proux.controller.service.MapService;
import g.proux.enumeration.Action;
import g.proux.enumeration.ElementType;
import g.proux.enumeration.Orientation;
import g.proux.exception.ElementCreationException;
import g.proux.model.*;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

public class MapServiceTest {

    private MapService mapService;

    @BeforeEach
    void setUp() {
        this.mapService = new MapService();
    }

    @Test
    @SneakyThrows
    public void testNominalInitMap() {
        Map map = new Map();
        List<String> lineElements = new ArrayList<>();
        lineElements.add(ElementType.MAP);
        lineElements.add("3");
        lineElements.add("4");

        this.mapService.initMap(map, lineElements);

        assertThat(map.getWidth()).isEqualTo(3);
        assertThat(map.getHeight()).isEqualTo(4);
    }

    @Test
    public void testInvalidLineErrorInitMap() {
        Map map = new Map();
        List<String> lineElements = new ArrayList<>();
        lineElements.add(ElementType.MAP);
        lineElements.add("3");

        Throwable thrown = catchThrowable(() ->
                this.mapService.initMap(map, lineElements)
        );

        assertThat(thrown).isInstanceOf(ElementCreationException.class);
        assertThat(((ElementCreationException) thrown).getCode()).isEqualTo("MAP_CREATION_ERROR");
    }

    @Test
    @SneakyThrows
    public void testNominalAddMountain() {
        Map map = new Map();
        map.setWidth(3);
        map.setHeight(4);

        List<String> lineElements = new ArrayList<>();
        lineElements.add(ElementType.MOUNTAIN);
        lineElements.add("1");
        lineElements.add("1");

        this.mapService.addMountain(map, lineElements, 0);

        assertThat(map.getElements()).hasSize(1);

        Element element = map.getElements().get(0);
        assertThat(element).hasSameClassAs(new Mountain());
        assertThat(element.getType()).isEqualTo(ElementType.MOUNTAIN);

        Mountain mountain = (Mountain) element;
        assertThat(mountain.getX()).isEqualTo(1);
        assertThat(mountain.getY()).isEqualTo(1);
    }

    @Test
    public void testInvalidLineErrorAddMountain() {
        Map map = new Map();
        map.setWidth(3);
        map.setHeight(4);

        List<String> lineElements = new ArrayList<>();
        lineElements.add(ElementType.MOUNTAIN);
        lineElements.add("4");
        lineElements.add("4");

        Throwable thrown = catchThrowable(() ->
                this.mapService.addMountain(map, lineElements, 0)
        );

        assertThat(thrown).isInstanceOf(ElementCreationException.class);
        assertThat(((ElementCreationException) thrown).getCode()).isEqualTo("MOUNTAIN_CREATION_ERROR");
    }

    @Test
    @SneakyThrows
    public void testNominalAddTreasure() {
        Map map = new Map();
        map.setWidth(3);
        map.setHeight(4);

        List<String> lineElements = new ArrayList<>();
        lineElements.add(ElementType.TREASURE);
        lineElements.add("1");
        lineElements.add("1");
        lineElements.add("2");

        this.mapService.addTreasure(map, lineElements, 0);

        assertThat(map.getElements()).hasSize(1);

        Element element = map.getElements().get(0);
        assertThat(element).hasSameClassAs(new Treasure());
        assertThat(element.getType()).isEqualTo(ElementType.TREASURE);

        Treasure treasure = (Treasure) element;
        assertThat(treasure.getX()).isEqualTo(1);
        assertThat(treasure.getY()).isEqualTo(1);
        assertThat(treasure.getValue()).isEqualTo(2);
    }

    @Test
    public void testInvalidLineErrorAddTreasure() {
        Map map = new Map();
        map.setWidth(3);
        map.setHeight(4);

        List<String> lineElements = new ArrayList<>();
        lineElements.add(ElementType.TREASURE);
        lineElements.add("A");
        lineElements.add("1");
        lineElements.add("2");

        Throwable thrown = catchThrowable(() ->
                this.mapService.addTreasure(map, lineElements, 0)
        );

        assertThat(thrown).isInstanceOf(ElementCreationException.class);
        assertThat(((ElementCreationException) thrown).getCode()).isEqualTo("TREASURE_CREATION_ERROR");
    }

    @Test
    @SneakyThrows
    public void testNominalAddAdventurer() {
        Map map = new Map();
        map.setWidth(3);
        map.setHeight(4);

        List<String> lineElements = new ArrayList<>();
        lineElements.add(ElementType.ADVENTURER);
        lineElements.add("Lara");
        lineElements.add("1");
        lineElements.add("1");
        lineElements.add(Orientation.NORTH);
        lineElements.add("AAA");

        this.mapService.addAdventurer(map, lineElements, 0);

        assertThat(map.getElements()).hasSize(1);

        Element element = map.getElements().get(0);
        assertThat(element).hasSameClassAs(new Adventurer());
        assertThat(element.getType()).isEqualTo(ElementType.ADVENTURER);

        Adventurer adventurer = (Adventurer) element;
        assertThat(adventurer.getName()).isEqualTo("Lara");
        assertThat(adventurer.getX()).isEqualTo(1);
        assertThat(adventurer.getY()).isEqualTo(1);
        assertThat(adventurer.getOrientation()).isEqualTo(Orientation.NORTH);
        assertThat(adventurer.getActions()).hasSize(3);

        List<String> expectedActions = new ArrayList<>();
        expectedActions.add(Action.MOVE);
        expectedActions.add(Action.MOVE);
        expectedActions.add(Action.MOVE);
        assertThat(adventurer.getActions()).usingRecursiveComparison().isEqualTo(expectedActions);
    }

    @Test
    public void testInvalidLineOrientationErrorAddAdventurer() {
        Map map = new Map();
        map.setWidth(3);
        map.setHeight(4);

        List<String> lineElements = new ArrayList<>();
        lineElements.add(ElementType.ADVENTURER);
        lineElements.add("Lara");
        lineElements.add("1");
        lineElements.add("1");
        lineElements.add("T");
        lineElements.add("AAA");

        Throwable thrown = catchThrowable(() ->
                this.mapService.addAdventurer(map, lineElements, 0)
        );

        assertThat(thrown).isInstanceOf(ElementCreationException.class);
        assertThat(((ElementCreationException) thrown).getCode()).isEqualTo("ADVENTURER_CREATION_ERROR");
    }

    @Test
    public void testInvalidLineActionsErrorAddAdventurer() {
        Map map = new Map();
        map.setWidth(3);
        map.setHeight(4);

        List<String> lineElements = new ArrayList<>();
        lineElements.add(ElementType.ADVENTURER);
        lineElements.add("Lara");
        lineElements.add("1");
        lineElements.add("1");
        lineElements.add(Orientation.NORTH);
        lineElements.add("5");

        Throwable thrown = catchThrowable(() ->
                this.mapService.addAdventurer(map, lineElements, 0)
        );

        assertThat(thrown).isInstanceOf(ElementCreationException.class);
        assertThat(((ElementCreationException) thrown).getCode()).isEqualTo("ADVENTURER_CREATION_ERROR");
    }

    @Test
    public void testGetElementByCoordinates() {
        Mountain mountain = new Mountain();
        mountain.setX(1);
        mountain.setY(1);

        Treasure treasure = new Treasure();
        treasure.setX(2);
        treasure.setY(3);

        Adventurer adventurer = new Adventurer();
        adventurer.setX(2);
        adventurer.setY(3);

        Map map = new Map();
        map.setWidth(3);
        map.setHeight(4);
        map.getElements().add(mountain);
        map.getElements().add(treasure);
        map.getElements().add(adventurer);

        Optional<Element> firstElement = this.mapService.getElementByCoordinates(map, 1, 1);
        Optional<Element> secondElement = this.mapService.getElementByCoordinates(map, 2, 1);
        Optional<Element> thirdElement = this.mapService.getElementByCoordinates(map, 2, 3);

        assertThat(firstElement).isPresent();
        assertThat(firstElement.get()).isInstanceOf(Mountain.class);

        assertThat(secondElement).isEmpty();

        assertThat(thirdElement).isPresent();
        assertThat(thirdElement.get()).isInstanceOf(Adventurer.class);

    }

}
