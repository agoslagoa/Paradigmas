package linea;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Linea {
	private static String BluePlayer = "Blue";
	private static String RedPlayer = "Red";
	
	public static String InvalidBoardDimensionsException = "El tablero debe tener dimensiones al menos 4x4.";
	public static String invalidColumnException = "Esta columna no existe.";
    public static String FullCollumnException = "La columna está llena.";
	
	private List<List<Character>> board = new ArrayList<List<Character>>();
	private GameStatus gameStatus;
	private GameMode typeOfGame;
	private boolean finished;
	private int altura;
    private int base;

    public Linea(int base, int altura, char typeOfGame) {
    	
    	if( base < 4 || altura < 4 ) { throw new RuntimeException(InvalidBoardDimensionsException); }
    	
        this.base = base;
        this.altura = altura;
        this.typeOfGame = GameMode.getTypeOfGame(Character.toUpperCase(typeOfGame));
        this.gameStatus = new RedTurn();
        
        board = IntStream.range(0, base)
                .mapToObj(i -> new ArrayList<Character>())
                .collect(Collectors.toList());
    }
    
    public void playRedAt(int column) {
    	gameStatus = gameStatus.playRed(column, this);
    	validateGameOver(RedPlayer);
    }


    public void playBlueAt(int column) {
    	gameStatus = gameStatus.playBlue(column, this);
    	validateGameOver(BluePlayer);
    }
    
	public void play(int column) {      
      if (column < 1 || column > getBase()) { throw new IllegalStateException(invalidColumnException); }
      
      if (board.get(column - 1).size() >= altura) { throw new IllegalStateException(FullCollumnException); }
      
      board.get(column - 1).add(gameStatus.getKey());

      finished = checkWinner(gameStatus.getKey());
	}
	
    public boolean isPieceAt(int col, int row, char piece) {
        return col >= 0 && col < base && row >= 0 && row < altura &&
                board.get(col).size() > row && board.get(col).get(row) == piece;
    }
    
	private void validateGameOver(String piece) { 
		if (finished) { 
			gameStatus = new GameOver(piece); } 
		}
    
    public boolean checkWinner(char piece) { 
    	return typeOfGame.validateWin(piece, this); 
    	}

    public boolean checkDraw() { 
    	return board.stream().allMatch(column -> column.size() >= altura); 
    	}

    public boolean checkHorizontal(char pieceToCheck) {
        return IntStream.range(0, altura)
                .anyMatch(row -> IntStream.range(0, base - 3)
                        .anyMatch(col -> IntStream.range(0, 4)
                                .allMatch(i -> isPieceAt(col + i, row, pieceToCheck))));
    }

    public boolean checkVertical(char pieceToCheck) {
        return IntStream.range(0, base)
                .anyMatch(col -> IntStream.range(0, altura - 3)
                        .anyMatch(row -> IntStream.range(0, 4)
                                .allMatch(i -> isPieceAt(col, row + i, pieceToCheck))));
    }

    public boolean checkDiagonal(char pieceToCheck) {
        return IntStream.range(0, base - 3)
                .anyMatch(col -> IntStream.range(0, altura - 3)
                        .anyMatch(row -> countConsecutiveDiagonalPieces(pieceToCheck, col, row)));
    }

    private boolean countConsecutiveDiagonalPieces(char pieceToCheck, int col, int row) {
        return IntStream.range(0, 4)
                .filter(i -> isPieceAt(col + i, row + i, pieceToCheck))
                .count() == 4 || IntStream.range(0, 4)
                .filter(i -> isPieceAt(col + i, row - i + 3, pieceToCheck))
                .count() == 4;
    }

    public String show() {
        StringBuilder display = new StringBuilder();

        IntStream.iterate(altura - 1, i -> i >= 0, i -> i - 1)
                 .forEach(i -> {
                     IntStream.range(0, base)
                              .mapToObj(j -> board.get(j).size() > i ? "| " + board.get(j).get(i) + " " : "|   ")
                              .forEach(display::append);
                     display.append("|\n"); });

        display.append("-".repeat(base * 4 + 1)).append("\n");
        IntStream.range(1, base + 1)
                 .mapToObj(i -> "  " + i + " ")
                 .forEach(display::append);
        display.append("\n"); 
        
        display.append("Current Turn: ").append(gameStatus.getCurrentTurn()).append("\n");
        
        if (finished) { display.append("Game Over!\n"); }
        
        return display.toString();
    }
    
    public boolean finished() { return gameStatus.isGameFinished(); }
    
    public int getBase() { return base; }

    public int getAltura() { return altura; }

    public String getTurno() { return gameStatus.getCurrentTurn(); }     
    
    public char getVarianteDeTriunfo() { return typeOfGame.getVariant(); }
    
    public GameStatus getCurrentStatus() { return gameStatus; }
}