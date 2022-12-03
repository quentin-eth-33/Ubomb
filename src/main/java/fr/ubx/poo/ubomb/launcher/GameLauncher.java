package fr.ubx.poo.ubomb.launcher;

import fr.ubx.poo.ubomb.game.Configuration;
import fr.ubx.poo.ubomb.game.Game;
import fr.ubx.poo.ubomb.game.Level;
import fr.ubx.poo.ubomb.game.Position;

import java.io.*;
import java.util.Properties;
import java.lang.*;


public class GameLauncher {

    public static Game load() {
        Configuration configuration = new Configuration(new Position(0, 0), 3, 5, 4000, 5, 1000);
        Level[] levels = new Level[1];
        levels[0]= new Level(new MapLevelDefault());
        return new Game(configuration,levels );
    }

    public static Game load(File file) throws IOException {
        MapRepoString mapRepoString = new MapRepoString();
        MapRepoStringRLE mapRepoStringRLE = new MapRepoStringRLE();
        Properties config = new Properties();
        Reader in = new FileReader(file);
        config.load(in);

        if(!config.containsKey("player"))
            new MapException("Missing player starting position in .properties file.");

        String[] startingPosition = config.getProperty("player").split( "x");
        boolean compression = Boolean.parseBoolean(config.getProperty("compression", "false"));
        int nbLevels = Integer.parseInt(config.getProperty("levels", "1"));
        int bombBagCapacity	 = Integer.parseInt(config.getProperty( "bombBagCapacity", "3"));
        int playerLives	 = Integer.parseInt(config.getProperty( "playerLives", "5"));
        long playerInvincibilityTime	 = Long.parseLong(config.getProperty( "playerInvincibilityTime", "4000"));
        int monsterVelocity = Integer.parseInt(config.getProperty( "monsterVelocity", "5"));
        long monsterInvincibilityTime	 = Long.parseLong(config.getProperty("monsterInvincibilityTime", "10000"));
        Level[] levels = new Level[nbLevels];
        String name;
        MapLevel mapLevel;
        for (int i= 0 ; i < nbLevels  ; i++) {
            name = "level" + (i+1);
            if (compression)
                mapLevel = mapRepoStringRLE.load( config.getProperty(name));
            else
                mapLevel = mapRepoString.load( config.getProperty(name));
            levels[i] = new Level(mapLevel);
        }

        Configuration configuration = new Configuration(new Position(Integer.parseInt(startingPosition[0]), Integer.parseInt(startingPosition[1])), bombBagCapacity, playerLives, playerInvincibilityTime, monsterVelocity, monsterInvincibilityTime);
        return new Game(configuration, levels);
    }
}
