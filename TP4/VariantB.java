package linea;

public class VariantB extends GameMode{

	@Override
	public boolean validateWin(char piece, Linea game) {
		return game.checkDiagonal(piece);
	}

	@Override
	public char getVariant() { return 'B'; }
}