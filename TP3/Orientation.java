package nemo;

public abstract class Orientation {

    public abstract String getCardinalPoint();

    public abstract Orientation getRight();

    public abstract Orientation getLeft();

	public abstract Position computeNewCoordinates( Position currentPosition ); 
}
