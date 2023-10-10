package g.proux.controller.service;

import g.proux.enumeration.Action;
import g.proux.enumeration.ElementType;
import g.proux.enumeration.Orientation;
import g.proux.exception.ElementCreationException;
import g.proux.exception.InvalidLineException;
import g.proux.model.*;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Stream;

@Slf4j
public class MapService {

    /**
     * Initialise la carte avec ses dimensions.
     *
     * @param map          la carte à initialiser
     * @param lineElements les éléments contenant les dimensions de la carte
     * @throws ElementCreationException se déclenche si la ligne n'est pas valide
     */
    public void initMap(Map map, List<String> lineElements) throws ElementCreationException {
        Integer width;
        Integer height;

        try {
            this.checkLineLength(lineElements, 3);
            width = this.checkIsDigit(lineElements.get(1));
            height = this.checkIsDigit(lineElements.get(2));
        } catch (InvalidLineException ex) {
            String errorMessage = "La création de la carte a échouée.";
            log.error(errorMessage);
            throw new ElementCreationException(errorMessage, "MAP_CREATION_ERROR");
        }

        map.setWidth(width);
        map.setHeight(height);
    }

    /**
     * Ajoute une montagne à la liste des éléments de la carte.
     *
     * @param map          la carte à laquelle la montagne sera ajoutée
     * @param lineElements les éléments avec les coordonnées de la montagne
     * @param lineIndex    l'index de la ligne des éléments
     * @throws ElementCreationException se déclenche si la ligne n'est pas valide
     */
    public void addMountain(Map map, List<String> lineElements, Integer lineIndex) throws ElementCreationException {
        Integer x;
        Integer y;

        try {
            this.checkLineLength(lineElements, 3);
            x = this.checkIsDigit(lineElements.get(1));
            y = this.checkIsDigit(lineElements.get(2));
            this.checkNotOutOfBounds(map, x, y);
        } catch (InvalidLineException ex) {
            String errorMessage = String.format("La création de la montagne ligne %d a échouée.", lineIndex);
            log.error(errorMessage);
            throw new ElementCreationException(errorMessage, "MOUNTAIN_CREATION_ERROR");
        }

        Mountain mountain = new Mountain();
        mountain.setX(x);
        mountain.setY(y);
        map.getElements().add(mountain);
    }

    /**
     * Ajoute un trésor à la liste des éléments de la carte.
     *
     * @param map          la carte à laquelle le trésor sera ajouté
     * @param lineElements les éléments avec les coordonnées et la valeur du trésor
     * @param lineIndex    l'index de la ligne des éléments
     * @throws ElementCreationException se déclenche si la ligne n'est pas valide
     */
    public void addTreasure(Map map, List<String> lineElements, Integer lineIndex) throws ElementCreationException {
        Integer x;
        Integer y;
        Integer value;

        try {
            this.checkLineLength(lineElements, 4);
            x = this.checkIsDigit(lineElements.get(1));
            y = this.checkIsDigit(lineElements.get(2));
            value = this.checkIsDigit(lineElements.get(3));
            this.checkNotOutOfBounds(map, x, y);
        } catch (InvalidLineException ex) {
            String errorMessage = String.format("La création du trésor ligne %d a échouée.", lineIndex);
            log.error(errorMessage);
            throw new ElementCreationException(errorMessage, "TREASURE_CREATION_ERROR");
        }

        Treasure treasure = new Treasure();
        treasure.setX(x);
        treasure.setY(y);
        treasure.setValue(value);
        map.getElements().add(treasure);
    }

    /**
     * Ajoute un aventurier à la liste des éléments de la carte.
     *
     * @param map          la carte à laquelle l'aventurier sera ajoutée
     * @param lineElements les éléments avec les informations de l'aventurier
     * @param lineIndex    l'index de la ligne des éléments
     * @throws ElementCreationException se déclenche si la ligne n'est pas valide
     */
    public void addAdventurer(Map map, List<String> lineElements, Integer lineIndex) throws ElementCreationException {
        Integer x;
        Integer y;
        String orientation;
        List<String> actions;

        try {
            this.checkLineLength(lineElements, 6);
            x = this.checkIsDigit(lineElements.get(2));
            y = this.checkIsDigit(lineElements.get(3));
            this.checkNotOutOfBounds(map, x, y);
            orientation = this.checkValidOrientation(lineElements.get(4));
            actions = this.checkValidActions(lineElements.get(5));
        } catch (InvalidLineException ex) {
            String errorMessage = String.format("La création de l'aventurier ligne %d a échouée.", lineIndex);
            log.error(errorMessage);
            throw new ElementCreationException(errorMessage, "ADVENTURER_CREATION_ERROR");
        }

        Adventurer adventurer = new Adventurer();
        adventurer.setName(lineElements.get(1));
        adventurer.setX(x);
        adventurer.setY(y);
        adventurer.setOrientation(orientation);
        adventurer.setActions(actions);
        map.getElements().add(adventurer);
    }

    /**
     * Vérifie la longueur d'une liste d'éléments.
     *
     * @param lineElementsToCheck la liste d'éléments à vérifier
     * @param lengthExpected      la longueur voulue pour la liste
     * @throws InvalidLineException se déclenche si la longueur de la liste ne correspond pas
     */
    private void checkLineLength(List<String> lineElementsToCheck, Integer lengthExpected) throws InvalidLineException {
        if (lineElementsToCheck.size() != lengthExpected) {
            String errorMessage = String.format("La ligne n'est pas valide car elle ne contient pas %d éléments.", lengthExpected);
            log.error(errorMessage);
            throw new InvalidLineException(errorMessage, "INVALID_ELEMENTS_NUMBER");
        }
    }

    /**
     * Vérifie si une chaîne de caractère est un chiffre et la renvoie en entier.
     *
     * @param lineElement la chaîne de caractère à vérifier
     * @return l'entier issu de la chaîne
     * @throws InvalidLineException se déclenche si la chaîne n'est pas un chiffre
     */
    private Integer checkIsDigit(String lineElement) throws InvalidLineException {
        Pattern pattern = Pattern.compile("\\d");
        if (!pattern.matcher(lineElement).matches()) {
            String errorMessage = String.format("La ligne n'est pas valide car %s n'est pas un entier.", lineElement);
            log.error(errorMessage);
            throw new InvalidLineException(errorMessage, "ELEMENT_NOT_DIGIT");
        }

        return Integer.parseInt(lineElement);
    }

    /**
     * Vérifie que l'élément n'est pas en dehors de la carte.
     *
     * @param map la carte avec ses dimensions
     * @param x   l'abscisse de l'élément
     * @param y   l'ordonnée de l'élément
     * @throws InvalidLineException se déclenche si l'élément est en dehors de la carte
     */
    private void checkNotOutOfBounds(Map map, Integer x, Integer y) throws InvalidLineException {
        if (x > map.getWidth() || y > map.getHeight()) {
            String errorMessage = "La ligne n'est pas valide car les coordonnées sont en dehors de la carte.";
            log.error(errorMessage);
            throw new InvalidLineException(errorMessage, "ELEMENT_OUT_OF_BOUNDS");
        }
    }

    /**
     * Vérifie que l'orientation est connue.
     *
     * @param orientation l'orientation à vérifier
     * @return l'orientation validée
     * @throws InvalidLineException se déclenche si l'orientation n'est pas connue
     */
    private String checkValidOrientation(String orientation) throws InvalidLineException {
        return switch (orientation) {
            case Orientation.NORTH, Orientation.EAST, Orientation.SOUTH, Orientation.WEST -> orientation;
            default -> {
                String errorMessage = String.format("La ligne n'est pas valide car l'orientation %s n'est pas connue.", orientation);
                log.error(errorMessage);
                throw new InvalidLineException(errorMessage, "UNKNOWED_ORIENTATION");
            }
        };
    }

    /**
     * Vérifie que la liste d'actions d'un aventurier est valide.
     *
     * @param lineElement la chaîne de caractère contenant les actions
     *
     * @return les actions sous forme de liste
     *
     * @throws InvalidLineException se déclenche si une action n'est pas connue
     */
    private List<String> checkValidActions(String lineElement) throws InvalidLineException {
        List<String> actions = Arrays.stream(lineElement.split("")).toList();

        for (String action : actions) {
            switch (action) {
                case Action.TURN_LEFT, Action.TURN_RIGHT, Action.MOVE -> {}
                default -> {
                    String errorMessage = String.format("La ligne n'est pas valide car l'action %s n'est pas connue.", action);
                    log.error(errorMessage);
                    throw new InvalidLineException(errorMessage, "UNKNOWED_ORIENTATION");
                }
            }
        }

        return actions;
    }

}
