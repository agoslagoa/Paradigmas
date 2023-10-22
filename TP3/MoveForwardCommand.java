package nemo;

public class MoveForwardCommand extends Commands{
	
	private Character value = 'f';
	
	@Override
	public Submarine execute(Submarine submarine) { return submarine.moveForward(); }
	
	@Override
	public boolean applies(Character characterCommand) { return value == characterCommand; }
}