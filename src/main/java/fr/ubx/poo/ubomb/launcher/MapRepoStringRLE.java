package fr.ubx.poo.ubomb.launcher;

public class MapRepoStringRLE implements MapRepo{
    final char EOL = 'x';

    @Override
    public MapLevel load(String string) {
        String[] splittedString = string.split( "x");
        MapLevel mapLevel;
        for ( int k = 0 ; k<splittedString.length; k++) {
            StringBuilder lineString = new StringBuilder();
            char lastChar = EOL;
            for(int i = 0 ; i < splittedString[k].length(); i++) {
                char currentChar =splittedString[k].charAt(i);

                if (!isANumber(currentChar)) {
                    lineString.append(currentChar);
                    lastChar= currentChar;
                }
                else {
                    for (int j =1 ; j< Integer.parseInt(String.valueOf(currentChar)) ; j++)
                        lineString.append(lastChar);
                }
            }
            splittedString[k] = lineString.toString();

        }

        int lenght = splittedString.length;
        int width =splittedString[0].length();
        for ( String str : splittedString) {

            if(str.length()!=width)
                throw new MapException("Missing EOL");
        }

        mapLevel= new MapLevel(lenght,width);

        for (int i =0 ; i< lenght; i++ ) {
            for (int j =0 ; j< width; j++ ) {
                mapLevel.set(i,j, Entity.fromCode(splittedString[i].charAt(j)));
            }
        }
        return mapLevel;
    }

    @Override
    public String export(MapLevel mapLevel) {
        return null;
    }
    public static boolean isANumber(char str) {
        try {
            Integer.parseInt(String.valueOf(str));
            return true;
        } catch(NumberFormatException e){
            return false;
        }
    }
}
