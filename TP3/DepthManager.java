package nemo;

public abstract class DepthManager {
	
	protected int currentDepth;
	
	public abstract int getCurrentDepth();
	
	public abstract void releaseCapsule();
	
	public abstract Submarine descend(Submarine Submarine); 
	
	public abstract Submarine ascend(Submarine Submarine);
} 