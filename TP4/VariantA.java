package linea;

public class VariantA extends GameMode {

    @Override
    public boolean validateWin(char piece, Linea game) {
        return game.checkHorizontal(piece) || game.checkVertical(piece);
    } 

	@Override
	public char getVariant() { return 'A'; }
}