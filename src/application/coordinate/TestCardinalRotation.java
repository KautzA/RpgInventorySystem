package application.coordinate;

public class TestCardinalRotation {
	public static void main(String[] args) {
		System.out.println("Test CardinalRotation.java");
		System.out.println(testRotateCoordinate());
	}

	static boolean testRotateCoordinate() {
    	System.out.println("testRotateCoordinate");
    	boolean result = true;
    	boolean localResult = true;

    	{
	    	localResult = true;
	    	System.out.print(" - Testing 1,3 by 0 --- ");
	    	Coordinate testResult = CardinalRotation.ZERO.rotateCoordinate(new Coordinate(1,3));
	    	localResult &= (testResult.x == 1);
	    	localResult &= (testResult.y == 3);
	    	System.out.println(localResult);
	    	result &= localResult;
	    	localResult = true;
    	}

    	{
    		localResult = true;
	    	System.out.print(" - Testing -1,-8 by 0 --- ");
	    	Coordinate testResult = CardinalRotation.ZERO.rotateCoordinate(new Coordinate(-1,-8));
	    	localResult &= (testResult.x == -1);
	    	localResult &= (testResult.y == -8);
	    	System.out.println(localResult);
	    	result &= localResult;
    	}

    	{
	    	localResult = true;
	    	System.out.print(" - Testing 1,3 by pi/2 --- ");
	    	Coordinate testResult = CardinalRotation.HALF_PI.rotateCoordinate(new Coordinate(1,3));
	    	localResult &= (testResult.x == -3);
	    	localResult &= (testResult.y == 1);
	    	System.out.println(localResult);
	    	result &= localResult;
	    	localResult = true;
    	}

    	{
    		localResult = true;
	    	System.out.print(" - Testing -1,-8 by pi/2 --- ");
	    	Coordinate testResult = CardinalRotation.HALF_PI.rotateCoordinate(new Coordinate(-1,-8));
	    	localResult &= (testResult.x == 8);
	    	localResult &= (testResult.y == -1);
	    	System.out.println(localResult);
	    	result &= localResult;
    	}

    	{
	    	localResult = true;
	    	System.out.print(" - Testing 1,3 by pi --- ");
	    	Coordinate testResult = CardinalRotation.PI.rotateCoordinate(new Coordinate(1,3));
	    	localResult &= (testResult.x == -1);
	    	localResult &= (testResult.y == -3);
	    	System.out.println(localResult);
	    	result &= localResult;
	    	localResult = true;
    	}

    	{
    		localResult = true;
	    	System.out.print(" - Testing -1,-8 by pi --- ");
	    	Coordinate testResult = CardinalRotation.PI.rotateCoordinate(new Coordinate(-1,-8));
	    	localResult &= (testResult.x == 1);
	    	localResult &= (testResult.y == 8);
	    	System.out.println(localResult);
	    	result &= localResult;
    	}

    	{
	    	localResult = true;
	    	System.out.print(" - Testing 1,3 by 3/2 pi --- ");
	    	Coordinate testResult = CardinalRotation.THREE_HALVES_PI.rotateCoordinate(new Coordinate(1,3));
	    	localResult &= (testResult.x == 3);
	    	localResult &= (testResult.y == -1);
	    	System.out.println(localResult);
	    	result &= localResult;
	    	localResult = true;
    	}

    	{
    		localResult = true;
	    	System.out.print(" - Testing -1,-8 by 3/2 pi --- ");
	    	Coordinate testResult = CardinalRotation.THREE_HALVES_PI.rotateCoordinate(new Coordinate(-1,-8));
	    	localResult &= (testResult.x == -8);
	    	localResult &= (testResult.y == 1);
	    	System.out.println(localResult);
	    	result &= localResult;
    	}
    	
    	
		return result;
	}
}
