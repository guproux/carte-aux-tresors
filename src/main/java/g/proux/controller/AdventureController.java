package g.proux.controller;

import g.proux.controller.service.MapService;
import g.proux.enumeration.ElementType;
import g.proux.exception.ElementCreationException;
import g.proux.model.Map;
import g.proux.view.MapView;
import lombok.RequiredArgsConstructor;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

@RequiredArgsConstructor
public class AdventureController {

    private final MapView view;

    private final MapService mapService;

    private Map map;

    public Map readFileAndCreateMap(String fileName) throws IOException, ElementCreationException {
        this.map = new Map();
        Path path = Paths.get(fileName);

        Integer lineIndex = 0;
        List<String> lines = Files.readAllLines(path);
        for (String line : lines) {
            List<String> lineElements = Arrays.stream(line.split(" - ")).toList();

            switch (lineElements.get(0)) {
                case ElementType.MAP -> this.mapService.initMap(this.map, lineElements);
                case ElementType.MOUNTAIN -> this.mapService.addMountain(this.map, lineElements, lineIndex);
                case ElementType.TREASURE -> this.mapService.addTreasure(this.map, lineElements, lineIndex);
                case ElementType.ADVENTURER -> this.mapService.addAdventurer(this.map, lineElements, lineIndex);
            }

            lineIndex++;
        }

        this.printMap();

        return map;
    }

    private void printMap() {
        this.view.printMap(map);
    }
}
