package linea;

public class DrawGame extends GameStatus{

	@Override
	protected GameStatus playRed(int column, Linea game) {
		throw new IllegalStateException(GameOverException);
	}

	@Override
	protected GameStatus playBlue(int column, Linea game) {
		throw new IllegalStateException(GameOverException);
	}
	
	@Override
	protected boolean isGameFinished() { return true; }
	
	@Override
	protected String getCurrentTurn() { return "Draw"; }


	@Override
	protected char getKey() { return 'T'; }
}