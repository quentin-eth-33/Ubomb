package fr.ubx.poo.ubomb.go.decor;
import fr.ubx.poo.ubomb.game.Position;

public class DoorNextClosed extends Door {

    public DoorNextClosed(Position position) {
        super(position);
        setIsAccessible(false);
    }
}
