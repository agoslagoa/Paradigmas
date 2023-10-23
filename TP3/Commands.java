package nemo;

import java.util.ArrayList;
import java.util.Arrays;

public abstract class Commands {
	
	private static ArrayList<Commands> commands = new ArrayList<>(Arrays.asList(new AscendCommand(), new DescendCommand(), new ReleaseCapsuleCommand(), new MoveForwardCommand(), new TurnRightCommand(), new TurnLeftCommand()));
	
	public abstract Submarine execute(Submarine nemo);
	  
	public abstract boolean applies(Character characterCommand);
	  
	public static void commandFor(char commandValue, Submarine nemo) {
		    commands.stream()
		        .filter(command -> command.applies(commandValue))
		        .forEach(command -> command.execute(nemo));
	}
}
