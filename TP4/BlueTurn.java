package linea;

public class BlueTurn extends GameStatus {

	@Override
	protected GameStatus playRed(int column, Linea game) {
		throw new RuntimeException(InvalidRedMoveException); 
	}

	@Override
	protected GameStatus playBlue(int column, Linea game) {
		game.play(column);
		if ( game.checkDraw() ) { return new DrawGame(); }
		
		return new RedTurn();
	}
	
	@Override
	protected boolean isGameFinished() { return false; }
	
	@Override
	protected String getCurrentTurn() { return "Blue"; }
	
	@Override
	protected char getKey() { return 'B'; }
}