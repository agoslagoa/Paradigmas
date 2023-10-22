package nemo;

public class NonShootableDepth extends DepthManager{
	
	public static String ChocolateBrownieException = "¡Cápsula liberada a profundidad no permitida! Submarino destruido por exceso de chocolate.";
	
    public NonShootableDepth(int updatedDepth) {
        currentDepth = updatedDepth;
    }

	@Override
	public Submarine descend(Submarine submarine) {
        submarine.depthStates.add(new NonShootableDepth(currentDepth + 1 ));
		return submarine.setNewDepthState();
		
	}

	@Override
	public Submarine ascend(Submarine submarine) {
        submarine.depthStates.remove(submarine.depthStates.size() - 1);
        return submarine.setNewDepthState();
		
	}
	
	@Override
	public void releaseCapsule() { throw new IllegalStateException(ChocolateBrownieException); }

	@Override
	public int getCurrentDepth() { return  currentDepth; }
} 