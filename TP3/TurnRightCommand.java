package nemo;

public class TurnRightCommand extends Commands{
	
	private Character value = 'r';

	@Override
	public Submarine execute(Submarine nemo) { return nemo.turnRight(); }
	
	@Override
	public boolean applies(Character characterCommand) { return value == characterCommand; }
}