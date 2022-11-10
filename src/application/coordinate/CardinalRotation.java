package application.coordinate;

public enum CardinalRotation implements Rotation {
	ZERO,
	HALF_PI,
	PI,
	THREE_HALVES_PI;

	@Override
	public Coordinate rotateCoordinate(Coordinate coord) {
		switch (this) {
			case ZERO:
				return new Coordinate(coord);
			case HALF_PI:
				return new Coordinate(-coord.y, coord.x);
			case PI:
				return new Coordinate(-coord.x, -coord.y);
			case THREE_HALVES_PI:
				return new Coordinate(coord.y, -coord.x);
			default:
				return new Coordinate(coord);
		}
	}

}
