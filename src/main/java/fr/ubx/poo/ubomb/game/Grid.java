package fr.ubx.poo.ubomb.game;


import fr.ubx.poo.ubomb.go.GameObject;
import fr.ubx.poo.ubomb.go.decor.Decor;

import java.util.*;

public interface Grid {
    int width();
    int height();

    GameObject get(Position position);

    void remove(Position position);

    Collection<GameObject> values();


    boolean inside(Position nextPos);

    void set(Position position, GameObject gameObject);
}
