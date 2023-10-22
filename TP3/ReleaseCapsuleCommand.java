package nemo;

public class ReleaseCapsuleCommand extends Commands{
	
	private Character value = 'm';

	@Override
	public Submarine execute(Submarine nemo) { return nemo.releaseCapsule(); }
	
	@Override
	public boolean applies(Character characterCommand) { return value == characterCommand; }
}