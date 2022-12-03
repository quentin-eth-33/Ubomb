/*
 * Copyright (c) 2020. Laurent Réveillère
 */

package fr.ubx.poo.ubomb.go.character;

import fr.ubx.poo.ubomb.game.Direction;
import fr.ubx.poo.ubomb.game.Game;
import fr.ubx.poo.ubomb.game.Position;
import fr.ubx.poo.ubomb.go.GameObject;
import fr.ubx.poo.ubomb.go.Movable;
import fr.ubx.poo.ubomb.go.TakeVisitor;
import fr.ubx.poo.ubomb.go.decor.*;
import fr.ubx.poo.ubomb.go.decor.bonus.*;

public class Player extends Character implements Movable, TakeVisitor {


    // Ajout
    private int numberKeys =0;

    private boolean princessFound = false;

    public Player(Game game, Position position) {
        super(game, position);
        this.setDirection(Direction.DOWN);
        this.setLives(game.configuration().playerLives());
    }


    @Override
    public void take(Key key) {
        numberKeys++;
        System.out.println("Take the key ...");
        key.remove();
    }

    // Ajout
    public int getNumberKeys()
    {
        return numberKeys;
    }





    public boolean getPrincessFound() {
        return princessFound;
    }

    public void setPrincessFound(boolean val) {
        this.princessFound = val;
    }





    public void requestOpen(){
        Position nextPosition = this.getDirection().nextPosition(getPosition());
        GameObject nextObject = game.grid().get(nextPosition);
        if(nextObject instanceof DoorNextClosed && this.getNumberKeys() >=1)
        {
            System.out.println("Eligible");
            this.numberKeys --;
            DoorNextOpened d =new DoorNextOpened(nextPosition);
            game.grid().set(nextPosition, d);
            nextObject.setModified(true);
        }
        //openDoor = true;
    }

    @Override
    public void doMove(Direction direction) {
        // This method is called only if the move is possible, do not check again
        Position nextPos = direction.nextPosition(getPosition());

        GameObject next = game.grid().get(nextPos);

        if (next instanceof Bonus bonus) {
            bonus.takenBy(this);
        }
        else if(next instanceof Monster)
        {
            this.setLives(getLives()-1);
        }
        setPosition(nextPos);
    }




    @Override
    public void explode() {
        // TODO
    }
}

