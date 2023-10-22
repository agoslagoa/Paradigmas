package nemo;

public abstract class Orientation {

    public abstract String getCardinalPoint();

    // Obtener el punto cardinal a la derecha
    public abstract Orientation getRight();

    // Obtener el punto cardinal a la izquierda
    public abstract Orientation getLeft();

	public abstract Position computeNewCoordinates( Position currentPosition ); 
}