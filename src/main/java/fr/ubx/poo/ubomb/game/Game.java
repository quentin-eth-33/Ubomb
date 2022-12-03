package fr.ubx.poo.ubomb.game;

import fr.ubx.poo.ubomb.go.GameObject;
import fr.ubx.poo.ubomb.go.character.*;

import fr.ubx.poo.ubomb.view.Sprite;
import fr.ubx.poo.ubomb.view.SpriteFactory;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

public class Game {

    private final Configuration configuration;
    private final Player player;

    private final List<Monster> monsters = new LinkedList<>();
    private final Grid grid;

    public Game(Configuration configuration, Grid grid) {
        this.configuration = configuration;
        this.grid = grid;
        player = new Player(this, configuration.playerPosition());
        for (var valueGrid : this.grid().values()) {
            if(valueGrid instanceof Monster) {
                Monster monster = new Monster(this, ((Monster)valueGrid).getPosition());
                this.grid().set(monster.getPosition(), monster);
                monsters.add(monster);
            }
        }
    }

    public Configuration configuration() {
        return configuration;
    }

    // Returns the player, monsters and bomb at a given position
    public List<GameObject> getGameObjects(Position position) {
        List<GameObject> gos = new LinkedList<>();
        if (player().getPosition().equals(position))
            gos.add(player);
        return gos;
    }

    public Grid grid() {
        return grid;
    }

    public List<Monster> getMonsters() {
        return this.monsters;
    }

    public Player player() {
        return this.player;
    }


}
