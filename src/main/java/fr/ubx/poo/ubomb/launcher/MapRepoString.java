package fr.ubx.poo.ubomb.launcher;

public class MapRepoString implements MapRepo {
    final char EOL = 'x';

    @Override
    public MapLevel load(String string) {
        String[] splittedString = string.split( "x");
        int lenght = splittedString.length;
        int width =splittedString[0].length();
        for ( String str : splittedString) {
            if(str.length()!=width)
                throw new MapException("Missing EOL");
        }

        MapLevel grid = new MapLevel(lenght,width);

        for (int i =0 ; i< lenght; i++ ) {
            for (int j =0 ; j< width; j++ ) {
                grid.set(i,j, Entity.fromCode(splittedString[i].charAt(j)));
            }
        }
        return grid;
    }

    @Override
    public String export(MapLevel mapLevel) {
        return null;
    }

}
