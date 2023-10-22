package nemo;

public class EastOrientation extends Orientation {
    @Override
    public String getCardinalPoint() { return "E"; }

    @Override
    public Orientation getRight() { return new SouthOrientation(); }

    @Override
    public Orientation getLeft() { return new NorthOrientation(); }
    
    @Override
    public Position computeNewCoordinates(Position currentPosition) { 
    	return new Position( currentPosition.getX() + 1, currentPosition.getY() ); 
    }
    
}