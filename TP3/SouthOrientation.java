package nemo;

public class SouthOrientation extends Orientation {
    @Override
    public String getCardinalPoint() { return "S"; }

    @Override
    public Orientation getRight() { return new WestOrientation(); }

    @Override
    public Orientation getLeft() { return new EastOrientation(); }
    
    @Override
    public Position computeNewCoordinates(Position currentPosition) {
    	return new Position( currentPosition.getX(), currentPosition.getY() - 1 ); 
    }
}
