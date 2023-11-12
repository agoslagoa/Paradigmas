package linea;

import java.util.ArrayList;
import java.util.Arrays;

public abstract class GameMode {
	
	public static String InvalidTypeOfGameException = "Invalid type of game";
	
	public static final ArrayList<GameMode> typesOfGame = new ArrayList<>(Arrays.asList( new VariantA(), new VariantB(), new VariantC() ));
	
	public static GameMode getTypeOfGame(char typeOfGame) {
        return typesOfGame.stream()
                .filter(game -> game.getVariant() == typeOfGame)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException(InvalidTypeOfGameException));
    }

    public abstract boolean validateWin(char piece, Linea game);
    
	public abstract char getVariant();	
}