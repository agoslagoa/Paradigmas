package nemo;

public class ShootableDepth extends DepthManager {
	
    public ShootableDepth() {
        currentDepth = 1;
    }
	
	@Override
	public Submarine descend(Submarine submarine) {
        submarine.depthStates.add(new NonShootableDepth(currentDepth + 1));
        return submarine.setNewDepthState();
	}

	@Override
	public Submarine ascend(Submarine submarine) {
        submarine.depthStates.remove(submarine.depthStates.size() - 1);
        return submarine.setNewDepthState();
	}
	
	@Override
	public void releaseCapsule() { }
	
	@Override
	public int getCurrentDepth() { return  currentDepth; }
}