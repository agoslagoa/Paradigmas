package nemo;

public class NorthOrientation extends Orientation {
    @Override
    public String getCardinalPoint() { return "N"; }

    @Override
    public Orientation getRight() { return new EastOrientation(); }

    @Override
    public Orientation getLeft() { return new WestOrientation(); }	
    
    @Override
    public Position computeNewCoordinates(Position currentPosition) {
    	return new Position( currentPosition.getX(), currentPosition.getY() + 1 ); 
    }
}