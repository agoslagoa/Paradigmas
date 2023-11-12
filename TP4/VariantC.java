package linea;

public class VariantC extends GameMode{

	@Override
	public boolean validateWin(char piece, Linea game) {
		return game.checkHorizontal(piece) || game.checkVertical(piece) || game.checkDiagonal(piece);
	}

	@Override
	public char getVariant() { return 'C'; }
}