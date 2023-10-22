package nemo;

public class ShootableSurface extends DepthManager{

    public ShootableSurface() {
        currentDepth = 0;
    }
	
	@Override
	public Submarine descend(Submarine submarine) { 
        submarine.depthStates.add(new ShootableDepth());
        return submarine.setNewDepthState(); 
	}

	@Override
	public Submarine ascend(Submarine submarine) { return submarine; }
	
	@Override
	public void releaseCapsule() { }
	
	@Override
	public int getCurrentDepth() { return  currentDepth; }

}