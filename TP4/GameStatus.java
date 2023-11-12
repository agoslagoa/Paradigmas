package linea;

public abstract class GameStatus {
	
	public static String InvalidRedMoveException = "No es el turno de las rojas";
	public static String InvalidBlueMoveException = "No es el turno de las azules";
	public static String GameOverException = "El juego ya ha terminado.";

	protected abstract String getCurrentTurn();
	
	protected abstract char getKey();

	protected abstract boolean isGameFinished();
	
	protected abstract GameStatus playRed(int column, Linea game);
	
	protected abstract GameStatus playBlue(int column, Linea game);
}