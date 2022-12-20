package fr.ubx.poo.ubomb.go.character;

import fr.ubx.poo.ubomb.game.Direction;
import fr.ubx.poo.ubomb.game.Game;
import fr.ubx.poo.ubomb.game.Position;
import fr.ubx.poo.ubomb.go.GameObject;
//import fr.ubx.poo.ubomb.go.GameObject;
//import fr.ubx.poo.ubomb.go.decor.bonus.Bonus;

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

    /* Ne fonctionne pas
    @Override
    public boolean canMove(Direction direction) {
        // Need to be updated ;-)
        Position nextPos = direction.nextPosition(getPosition());

        GameObject next = game.grid(inLevel).get(nextPos);

        if (next != null){

            return next.getIsAccessible();

        }

        // Je ne sais pas pourquoi "next instanceof Monster " est toujours faux
        else if(next instanceof Monster || nextPos.getX() < 0 ||nextPos.getY() < 0 || nextPos.getX() >= game.grid(inLevel).width() || nextPos.getY() >= game.grid(inLevel).height()){
            return false;
        }
        else{
            return true;
        }

    }

     */
    @Override
    public void doMove(Direction direction) {
        Position nextPos = direction.nextPosition(getPosition());
        setSaveLastPosition(this.getPosition());

        game.grid(inLevel).set(nextPos, this);
        game.grid(inLevel).remove(getPosition());
        setPosition(nextPos);
    }

}
