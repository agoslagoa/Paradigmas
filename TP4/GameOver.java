package linea;

public class GameOver extends GameStatus {

	private String winner;

	public GameOver(String currentTurn) { this.winner = currentTurn; }

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
	protected String getCurrentTurn() { return winner + " wins the game!"; }

	@Override
	protected char getKey() { return 'W'; }
	
}