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
    }

    public Monster(Position position) {
        super(position);
        this.setDirection(Direction.DOWN);
        this.setLives(1);
    }

    @Override
    public void doMove(Direction direction) {
        Position nextPos = direction.nextPosition(getPosition());
        GameObject next = game.grid(inLevel).get(nextPos);

        // Je ne sais pas si c'est fonctionnel
        if (next instanceof Player) {
            ((Player)next).setLives(((Player)next).getLives()-1);
        }
        setPosition(nextPos);
    }


}
