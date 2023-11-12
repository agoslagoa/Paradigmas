package linea;

public class RedTurn extends GameStatus {

	@Override
	protected GameStatus playRed(int column, Linea game) {
		game.play(column);
		if ( game.checkDraw() ) { return new DrawGame(); }
		
		return new BlueTurn(); 
	}

	@Override
	protected GameStatus playBlue(int column, Linea game) {
		throw new RuntimeException(InvalidBlueMoveException);
	}
	
	@Override
	protected boolean isGameFinished() { return false; }
	
	@Override
	protected String getCurrentTurn() { return "Red"; }

	@Override
	protected char getKey() { return 'R'; }
}