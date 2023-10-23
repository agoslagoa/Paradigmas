package nemo;

import java.util.ArrayList;

public class Submarine {
	
    protected ArrayList<DepthManager> depthStates = new ArrayList<>(); 
    
    private Position currentPosition;	
    private Orientation currentOrientation; 	
    private DepthManager currentDepthState = new ShootableSurface(); 
    
    public Submarine( Position initialCoordinates, Orientation initialOrientation ) {
        currentPosition = initialCoordinates;	
        currentOrientation = initialOrientation; 
        
        depthStates.add(currentDepthState);
    }
    
    public Submarine setNewDepthState() {
    	currentDepthState = depthStates.get(depthStates.size() - 1);
    	return this;
    }
    
    public Submarine setNewPosition(Position newCoordinates) {
    	currentPosition = newCoordinates;
    	return this;
    }
    
    public Submarine setNewDirection(Orientation newOrientation) {
    	currentOrientation = newOrientation;
    	return this;
    }
    
    public Submarine moveForward() {
    	setNewPosition( currentOrientation.computeNewCoordinates(currentPosition) );
    	return this;
    }
    
    public Submarine ascend() {
    	currentDepthState.ascend(this);
    	return this;
    }
    
    public Submarine descend() {
    	currentDepthState.descend(this);
    	return this;
    }
    
    public Submarine turnRight() {
    	setNewDirection( currentOrientation.getRight() );
    	return this;
    }
    
    public Submarine turnLeft() {
    	setNewDirection( currentOrientation.getLeft() );
    	return this;
    }
    
    public Submarine releaseCapsule() {
    	currentDepthState.releaseCapsule();
    	return this;
    }
    
    public void processInstruction(String instruction) {
        instruction.chars()
            .filter(command -> command != ' ')
            .forEach(command -> Commands.commandFor((char) command, this));
    }
    
    public int getDepth() { return currentDepthState.getCurrentDepth(); }

    public String getOrientation() { return currentOrientation.getCardinalPoint(); }
    
    public Position getPosition() { return currentPosition; }
}
