package nemo;

public class DescendCommand extends Commands{
	
	private Character value = 'd';
	
	@Override
	public Submarine execute(Submarine nemo) { return nemo.descend(); }
	
	@Override
	public boolean applies(Character characterCommand) { return value == characterCommand; }
}