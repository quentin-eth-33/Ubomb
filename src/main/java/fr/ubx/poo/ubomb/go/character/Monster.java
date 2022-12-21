package fr.ubx.poo.ubomb.go.character;

import fr.ubx.poo.ubomb.engine.Timer;
import fr.ubx.poo.ubomb.game.Direction;
import fr.ubx.poo.ubomb.game.Game;
import fr.ubx.poo.ubomb.game.Position;
import fr.ubx.poo.ubomb.go.GameObject;
import fr.ubx.poo.ubomb.go.decor.bonus.Bonus;

public class Monster extends Character {

    private Timer timerMoveMonster;

    private int monsterVelocity;
    public Monster(Game game, Position position) {
        super(game, position);
        this.setDirection(Direction.DOWN);
        this.setLives(1);
        monsterVelocity =5;
        setInvincibilityTime(1000);

        // Il faut changer la formule du Timer
        timerMoveMonster = new Timer(/*(long)Math.pow((double)1,(double)10)/monsterVelocity*/monsterVelocity*200);
        timerMoveMonster.start();
    }

    public Monster(Position position) {
        super(position);
        this.setDirection(Direction.DOWN);
        this.setLives(1);
        monsterVelocity =5;
        setInvincibilityTime(1000);
    }

    public Timer getTimerMoveMonster(){
        return timerMoveMonster;
    }

    public void setTimerMoveMonster(Timer timerMonster){
        this.timerMoveMonster =  timerMonster;
    }

    public void setMonsterVelocity(int velocity){
        this.monsterVelocity = velocity;
    }

    public int getMonsterVelocity(){
        return this.monsterVelocity;
    }

    @Override
    public void doMove(Direction direction) {
        Position nextPos = direction.nextPosition(getPosition());
        GameObject next = game.grid(inLevel).get(nextPos);

        // Surement faux
        if (next instanceof Player) {
            ((Player)next).setLives(((Player)next).getLives()-1);
        }
        setPosition(nextPos);
    }
}
