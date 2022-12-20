package fr.ubx.poo.ubomb.game;

import fr.ubx.poo.ubomb.go.GameObject;
import fr.ubx.poo.ubomb.go.decor.bonus.*;
import fr.ubx.poo.ubomb.go.decor.*;
import fr.ubx.poo.ubomb.launcher.Entity;
import fr.ubx.poo.ubomb.launcher.MapLevel;
import fr.ubx.poo.ubomb.go.character.Monster;

import java.util.*;

public class Level implements Grid {

    private final int width;

    private final int height;

    private final MapLevel entities;

    private final List<Monster> monsters = new LinkedList<>();

    private final Map<Position, GameObject> elements = new HashMap<>();

    public Level(MapLevel entities) {
        this.entities = entities;
        this.width = entities.width();
        this.height = entities.height();

        for (int i = 0; i < width; i++)
            for (int j = 0; j < height; j++) {
                Position position = new Position(i, j);
                Entity entity = entities.get(i, j);
                switch (entity) {
                    case Stone:
                        elements.put(position, new Stone(position));
                        break;
                    case Tree:
                        elements.put(position, new Tree(position));
                        break;
                    case Key:
                        elements.put(position, new Key(position));
                        break;
                    case Princess:
                        elements.put(position, new Princess(position));
                        break;

                    case Monster:
                        elements.put(position, new Monster(position));
                        break;

                    case DoorNextClosed:
                        elements.put(position, new DoorNextClosed(position));
                        break;

                    case DoorPrevOpened:
                        elements.put(position, new DoorPrevOpened(position));
                        break;

                    case DoorNextOpened:
                        elements.put(position, new DoorNextOpened(position));
                        break;

                    case Empty: break;
                    default:
                        break;
                        //throw new RuntimeException("EntityCode " + entity.name() + " not processed");
                }
            }
    }

    @Override
    public int width() {
        return this.width;
    }

    @Override
    public int height() {
        return this.height;
    }

    public GameObject get(Position position) {
        return elements.get(position);
    }

    @Override
    public void remove(Position position) {
        elements.remove(position);
    }

    public Collection<GameObject> values() {
        return elements.values();
    }


    @Override
    public boolean inside(Position position) {
        return true;
    }

    @Override
    public void set(Position position, GameObject gameObject) {
        if (!inside(position))
            throw new IllegalArgumentException("Illegal Position");
        if (gameObject != null)
            elements.put(position, gameObject);
    }
    public List<Monster> getMonsters() {
        return this.monsters;
    }
    public void addMonster(Monster monster)  {
        monsters.add(monster);
    }
}
