package nemo;

public class WestOrientation extends Orientation {
    @Override
    public String getCardinalPoint() { return "W"; }

    @Override
    public Orientation getRight() { return new NorthOrientation(); }

    @Override
    public Orientation getLeft() { return new SouthOrientation(); }
    
    @Override
    public Position computeNewCoordinates(Position currentPosition) {
    	return new Position( currentPosition.getX() - 1, currentPosition.getY() );
    }
}