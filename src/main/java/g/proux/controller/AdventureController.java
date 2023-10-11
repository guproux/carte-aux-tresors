package g.proux.controller;

import g.proux.controller.service.AdventurerService;
import g.proux.controller.service.MapService;
import g.proux.enumeration.ElementType;
import g.proux.exception.ElementCreationException;
import g.proux.model.Adventurer;
import g.proux.model.Element;
import g.proux.model.Map;
import g.proux.view.MapView;
import lombok.RequiredArgsConstructor;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Arrays;
import java.util.List;

@RequiredArgsConstructor
public class AdventureController {

    private final MapView view;

    private final MapService mapService;
    private final AdventurerService adventurerService;

    private Map map;

    /**
     * Lis le fichier contenant les informations de la carte et initialise la carte.
     *
     * @param fileName le nom du fichier contenant les informations de la carte
     * @throws IOException              se déclenche si une erreur survient à la lecture du fichier
     * @throws ElementCreationException se déclenche si la création d'un élément échoue
     */
    public void readFileAndCreateMap(String fileName) throws IOException, ElementCreationException {
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
    }

    /**
     * Simule la recherche des trésors par les aventuriers.
     */
    public void searchTreasures() {
        List<Adventurer> adventurers = this.mapService.getAdventurers(this.map);

        while (adventurers.stream().anyMatch(a -> !a.getActions().isEmpty())) {
            for (Adventurer adventurer : adventurers) {
                this.adventurerService.doAction(adventurer, this.map);
            }
            this.printMap();
        }
    }

    /**
     * Ecrit la carte dans un fichier de sortie.
     *
     * @param outputFileName le nom du fichier de sortie
     * @throws IOException se déclenche si le fichier n'est pas disponible en écriture
     */
    public void writeMapIntoFile(String outputFileName) throws IOException {
        String lineSeparator = System.lineSeparator();
        Path path = Paths.get(outputFileName);

        Files.deleteIfExists(path);

        StringBuilder sb = new StringBuilder(this.map.writeLine());
        sb.append(lineSeparator);
        Files.write(path, sb.toString().getBytes(StandardCharsets.UTF_8), StandardOpenOption.CREATE);

        for (Element element : this.map.getElements()) {
            sb = new StringBuilder(element.writeLine());
            sb.append(lineSeparator);
            Files.write(path, sb.toString().getBytes(StandardCharsets.UTF_8), StandardOpenOption.APPEND);
        }
    }

    private void printMap() {
        this.view.printMap(this.map);
    }

}
