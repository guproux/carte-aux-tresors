package g.proux.controller.service;

import g.proux.enumeration.Action;
import g.proux.exception.InvalidLineException;
import g.proux.exception.NotAllowedActionException;
import g.proux.model.Adventurer;

import g.proux.enumeration.Orientation;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class AdventurerService {

    public void doAction(Adventurer adventurer) {
        String action = !adventurer.getActions().isEmpty() ? adventurer.getActions().get(0) : null;

        if (action != null) {
            try {
                switch (action) {
                    case Action.TURN_LEFT -> this.turnLeft(adventurer);
                    case Action.TURN_RIGHT -> this.turnRight(adventurer);
                    case Action.MOVE -> this.move(adventurer);
                }
            } catch (NotAllowedActionException ex) {

            }

            adventurer.getActions().remove(action);
        }
    }

    private void turnLeft(Adventurer adventurer) throws NotAllowedActionException {
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

        adventurer.setOrientation(newOrientation);
    }

    private void turnRight(Adventurer adventurer) throws NotAllowedActionException {
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

        adventurer.setOrientation(newOrientation);
    }

    private void move(Adventurer adventurer) {
    }

}
