package nemo;

public class AscendCommand extends Commands{
	
	private Character value = 'u';
	
	@Override
	public Submarine execute(Submarine nemo) { return nemo.ascend(); }

	@Override
	public boolean applies(Character characterCommand) { return value == characterCommand; }
}