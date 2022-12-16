package fr.ubx.poo.ubomb.go.character;

import fr.ubx.poo.ubomb.game.Direction;
import fr.ubx.poo.ubomb.game.Game;
import fr.ubx.poo.ubomb.game.Position;
import fr.ubx.poo.ubomb.go.GameObject;
import fr.ubx.poo.ubomb.go.decor.bonus.Bonus;

public class Monster extends Character {

    public Monster(Game game, Position position) {
        super(game, position);
        this.setDirection(Direction.DOWN);
        this.setLives(1);
        this.setSaveLastPosition(position);
    }

    public Monster(Position position) {
        super(position);
        this.setDirection(Direction.DOWN);
        this.setLives(1);
    }

    @Override
    public void doMove(Direction direction) {
        Position nextPos = direction.nextPosition(getPosition());
        setSaveLastPosition(this.getPosition());
        GameObject next = game.grid(inLevel).get(nextPos);
        game.grid(inLevel).set(nextPos, this);
        game.grid(inLevel).remove(getPosition());
        setPosition(nextPos);
    }

}
