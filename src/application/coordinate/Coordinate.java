package application.coordinate;

public class Coordinate {
	/** The x coordinate of the point */
	public final int x;
	
	/** The y coordinate of the point */
	public final int y;
	
	public Coordinate(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	public Coordinate (Coordinate other) {
		this.x = other.x;
		this.y = other.y;
	}
	
	public Coordinate add(Coordinate other) {
		return new Coordinate(this.x + other.x,
							  this.y + other.y);
	}
	
	public Coordinate subtract(Coordinate other) {
		return new Coordinate(this.x - other.x,
				  			  this.y - other.y);
	}
}
