package nemo;

import java.util.ArrayList;

public class Submarine {
	
    protected ArrayList<DepthManager> depthStates = new ArrayList<>(); 
    
    private Position currentPosition;			// Posición del submarino en el eje cartesiano
    private Orientation currentOrientation; 	// Orientación del submarino (N, S, E, W)
    private DepthManager currentDepthState = new ShootableSurface(); // Estado de profundidad actual
    
    public Submarine( Position initialCoordinates, Orientation initialOrientation ) {
        currentPosition = initialCoordinates;		// inicializa la posición
        currentOrientation = initialOrientation; 	// Inicializa la orientación
        
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