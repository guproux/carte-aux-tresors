package g.proux.controller.service;

import g.proux.enumeration.Action;
import g.proux.enumeration.Orientation;
import g.proux.exception.NotAllowedActionException;
import g.proux.model.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
public class AdventurerService {

    private final MapService mapService;

    /**
     * Fait faire à un aventurier une action.
     *
     * @param adventurer l'aventurier qui fait l'action
     * @param map        la carte sur laquelle l'aventurier fait l'action
     */
    public void doAction(Adventurer adventurer, Map map) {
        Optional<String> oAction = adventurer.getActions().stream().findFirst();

        if (oAction.isPresent()) {
            String action = oAction.get();
            try {
                switch (action) {
                    case Action.TURN_LEFT -> this.turnLeft(adventurer);
                    case Action.TURN_RIGHT -> this.turnRight(adventurer);
                    case Action.MOVE -> this.move(adventurer, map);
                }
            } catch (NotAllowedActionException ex) {
                log.info("{} ne fait rien ce tour.", adventurer.getName());
            }

            adventurer.setActions(adventurer.getActions().subList(1, adventurer.getActions().size()));
        } else {
            log.info("{} ne fait rien ce tour.", adventurer.getName());
        }
    }

    /**
     * Tourne un aventurier vers la gauche.
     *
     * @param adventurer l'aventurier que l'on tourne
     * @throws NotAllowedActionException se déclenche si l'orientation n'est pas connu
     */
    public void turnLeft(Adventurer adventurer) throws NotAllowedActionException {
        String newOrientation = switch (adventurer.getOrientation()) {
            case Orientation.NORTH -> Orientation.WEST;
            case Orientation.EAST -> Orientation.NORTH;
            case Orientation.SOUTH -> Orientation.EAST;
            case Orientation.WEST -> Orientation.SOUTH;
            default -> {
                String errorMessage = String.format("L'orientation %s n'est pas connue.", adventurer.getOrientation());
                log.error(errorMessage);
                throw new NotAllowedActionException(errorMessage, "UNKNOWED_ORIENTATION");
            }
        };

        log.info("{} se tourne vers la gauche.", adventurer.getName());
        adventurer.setOrientation(newOrientation);
    }

    /**
     * Tourne un aventurier vers la droite.
     *
     * @param adventurer l'aventurier que l'on tourne
     * @throws NotAllowedActionException se déclenche si l'orientation n'est pas connu
     */
    public void turnRight(Adventurer adventurer) throws NotAllowedActionException {
        String newOrientation = switch (adventurer.getOrientation()) {
            case Orientation.NORTH -> Orientation.EAST;
            case Orientation.EAST -> Orientation.SOUTH;
            case Orientation.SOUTH -> Orientation.WEST;
            case Orientation.WEST -> Orientation.NORTH;
            default -> {
                String errorMessage = String.format("L'orientation %s n'est pas connue.", adventurer.getOrientation());
                log.error(errorMessage);
                throw new NotAllowedActionException(errorMessage, "UNKNOWED_ORIENTATION");
            }
        };

        log.info("{} se tourne vers la droite.", adventurer.getName());
        adventurer.setOrientation(newOrientation);
    }

    /**
     * Déplace un aventurier en fonction de son orientation.
     *
     * @param adventurer l'aventurier qui se déplace
     * @param map        la carte sur laquelle l'aventurier se déplace
     * @throws NotAllowedActionException se déclenche si l'aventurier ne peut pas se déplacer
     */
    public void move(Adventurer adventurer, Map map) throws NotAllowedActionException {
        Integer newX = adventurer.getX();
        Integer newY = adventurer.getY();
        switch (adventurer.getOrientation()) {
            case Orientation.NORTH -> newY--;
            case Orientation.EAST -> newX++;
            case Orientation.SOUTH -> newY++;
            case Orientation.WEST -> newX--;
        }

        if (newX < 0 || newY < 0 || newX > map.getWidth() || newY > map.getHeight()) {
            String errorMessage = String.format("%s sort de la carte si il avance.", adventurer.getName());
            log.error(errorMessage);
            throw new NotAllowedActionException(errorMessage, "ADVENTURER_OUT_OF_BOUNDS");
        }

        Optional<Element> oElement = this.mapService.getElementByCoordinates(map, newX, newY);
        if (oElement.isPresent()) {
            this.checkDestinationElement(oElement.get(), adventurer);
        }

        log.info("{} se déplace aux coordonnées (x: {}, y: {}).", adventurer.getName(), newX, newY);
        adventurer.setX(newX);
        adventurer.setY(newY);
    }

    /**
     * Vérifie si l'élément de destination a une incidence sur le déplacement.
     *
     * @param element    l'élément de destination
     * @param adventurer l'aventurier qui se déplace
     * @throws NotAllowedActionException se déclenche si l'élément de destination est bloquant
     */
    private void checkDestinationElement(Element element, Adventurer adventurer) throws NotAllowedActionException {
        if (element instanceof BlockingElement) {
            String errorMessage = String.format("%s ne peut pas avancer.", adventurer.getName());
            log.error(errorMessage);
            throw new NotAllowedActionException(errorMessage, "BLOCKED_ADVENTURER");
        } else if (element instanceof Treasure treasure && treasure.getValue() > 0) {
            // Collecte un bout du trésor trouvé
            adventurer.setLoot(adventurer.getLoot() + 1);
            treasure.setValue(treasure.getValue() - 1);
        }
    }

}
