/*
 * Copyright (c) 2020. Laurent Réveillère
 */

package fr.ubx.poo.ubomb.view;

import fr.ubx.poo.ubomb.go.GameObject;
import fr.ubx.poo.ubomb.go.decor.bonus.*;
import fr.ubx.poo.ubomb.go.decor.*;
import javafx.scene.layout.Pane;
import fr.ubx.poo.ubomb.go.character.Monster;

import static fr.ubx.poo.ubomb.view.ImageResource.*;


public final class SpriteFactory {

    public static Sprite create(Pane layer, GameObject gameObject) {
        if (gameObject instanceof Stone)
            return new Sprite(layer, STONE.getImage(), gameObject);
        if (gameObject instanceof Tree)
            return new Sprite(layer, TREE.getImage(), gameObject);
        if (gameObject instanceof Key)
            return new Sprite(layer, KEY.getImage(), gameObject);
        if (gameObject instanceof Princess)
            return new Sprite(layer, PRINCESS.getImage(), gameObject);
        /*
        if (gameObject instanceof Monster)
            return new Sprite(layer, MONSTER_UP.getImage(), gameObject);

         */
        if (gameObject instanceof DoorNextOpened)
            return new Sprite(layer, DOOR_OPENED_PLUS.getImage(), gameObject);
        if (gameObject instanceof DoorNextClosed)
            return new Sprite(layer, DOOR_CLOSED_PLUS.getImage(), gameObject);
        if (gameObject instanceof DoorPrevOpened)
            return new Sprite(layer, DOOR_OPENED_MINUS.getImage(), gameObject);
        throw new RuntimeException("Unsupported sprite for decor " + gameObject);
    }
}
