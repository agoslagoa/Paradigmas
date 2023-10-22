package nemo;

public class TurnLeftCommand extends Commands{
	
	private Character value = 'l';

	@Override
	public Submarine execute(Submarine nemo) { return nemo.turnLeft(); }
	
	@Override
	public boolean applies(Character characterCommand) { return value == characterCommand; }
}