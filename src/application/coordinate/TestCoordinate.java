package application.coordinate;

public class TestCoordinate {

	public static void main(String[] args) {
		System.out.println("Test Coordinate.java");
		System.out.println(testConstruction());
		System.out.println(testAdd());
		System.out.println(testSubtract());
		System.out.println(testEquals());
	}
	
    static boolean testConstruction() {
    	System.out.println("testConstruction");
    	boolean result = true;
    	boolean localResult = true;

    	localResult = true;
    	System.out.print(" - Testing 0,0 --- ");
    	Coordinate testCoord = new Coordinate(0, 0);
    	localResult &= (testCoord.x == 0);
    	localResult &= (testCoord.y == 0);
    	System.out.println(localResult);
    	result &= localResult;

    	localResult = true;
    	System.out.print(" - Testing 1,3 --- ");
    	Coordinate testCoord13 = new Coordinate(1, 3);
    	localResult &= (testCoord13.x == 1);
    	localResult &= (testCoord13.y == 3);
    	System.out.println(localResult);
    	result &= localResult;

    	localResult = true;
    	System.out.print(" - Testing 3,1 --- ");
    	Coordinate testCoord31 = new Coordinate(3, 1);
    	localResult &= (testCoord31.x == 3);
    	localResult &= (testCoord31.y == 1);
    	System.out.println(localResult);
    	result &= localResult;

    	localResult = true;
    	System.out.print(" - Testing 2,2 --- ");
    	Coordinate testCoord22 = new Coordinate(2, 2);
    	localResult &= (testCoord22.x == 2);
    	localResult &= (testCoord22.y == 2);
    	System.out.println(localResult);
    	result &= localResult;
    	
    	System.out.print(" - Testing -2,-2 --- ");
    	Coordinate testCoordm2m2 = new Coordinate(-2, -2);
    	localResult &= (testCoordm2m2.x == -2);
    	localResult &= (testCoordm2m2.y == -2);
    	System.out.println(localResult);
    	result &= localResult;
    	
    	return result;
    }
	
    static boolean testAdd() {
    	System.out.println("testAdd");
    	boolean result = true;
    	boolean localResult = true;
    	
    	Coordinate testCoord = new Coordinate(0, 0);
    	result &= (testCoord.x == 0);
    	result &= (testCoord.y == 0);
    	
    	Coordinate testCoord13 = new Coordinate(1, 3);
    	result &= (testCoord13.x == 1);
    	result &= (testCoord13.y == 3);
    	
    	Coordinate testCoord31 = new Coordinate(3, 1);
    	result &= (testCoord31.x == 3);
    	result &= (testCoord31.y == 1);
    	
    	Coordinate testCoord22 = new Coordinate(2, 2);
    	result &= (testCoord22.x == 2);
    	result &= (testCoord22.y == 2);

    	localResult = true;
    	System.out.print(" - Testing 1,3 + 1,3 --- ");
    	Coordinate test13Plus13 = testCoord13.add(testCoord13);
    	localResult &= (testCoord13.x == 1);
    	localResult &= (testCoord13.y == 3);
    	localResult &= (test13Plus13.x == 2);
    	localResult &= (test13Plus13.y == 6);
    	System.out.println(localResult);
    	result &= localResult;

    	localResult = true;
    	System.out.print(" - Testing 2,2 + 3,1 --- ");
    	Coordinate test22Plus31 = testCoord22.add(testCoord31);
    	localResult &= (testCoord22.x == 2);
    	localResult &= (testCoord22.y == 2);
    	localResult &= (testCoord31.x == 3);
    	localResult &= (testCoord31.y == 1);
    	localResult &= (test22Plus31.x == 5);
    	localResult &= (test22Plus31.y == 3);
    	System.out.println(localResult);
    	result &= localResult;

    	return result;
    }

    static boolean testSubtract() {
    	System.out.println("testSubtract");
    	boolean result = true;
    	boolean localResult = true;
    	
    	Coordinate testCoord = new Coordinate(0, 0);
    	result &= (testCoord.x == 0);
    	result &= (testCoord.y == 0);
    	
    	Coordinate testCoord13 = new Coordinate(1, 3);
    	result &= (testCoord13.x == 1);
    	result &= (testCoord13.y == 3);
    	
    	Coordinate testCoord31 = new Coordinate(3, 1);
    	result &= (testCoord31.x == 3);
    	result &= (testCoord31.y == 1);
    	
    	Coordinate testCoord22 = new Coordinate(2, 2);
    	result &= (testCoord22.x == 2);
    	result &= (testCoord22.y == 2);

    	localResult = true;
    	System.out.print(" - Testing 1,3 - 1,3 --- ");
    	Coordinate test13Minus13 = testCoord13.subtract(testCoord13);
    	localResult &= (testCoord13.x == 1);
    	localResult &= (testCoord13.y == 3);
    	localResult &= (test13Minus13.x == 0);
    	localResult &= (test13Minus13.y == 0);
    	System.out.println(localResult);
    	result &= localResult;

    	localResult = true;
    	System.out.print(" - Testing 2,2 - 3,1 --- ");
    	Coordinate test22Plus31 = testCoord22.subtract(testCoord31);
    	localResult &= (testCoord22.x == 2);
    	localResult &= (testCoord22.y == 2);
    	localResult &= (testCoord31.x == 3);
    	localResult &= (testCoord31.y == 1);
    	localResult &= (test22Plus31.x == -1);
    	localResult &= (test22Plus31.y == 1);
    	System.out.println(localResult);
    	result &= localResult;

    	return result;
    }

    @SuppressWarnings("unlikely-arg-type")
	static boolean testEquals() {
    	System.out.println("testEquals");
    	boolean result = true;
    	boolean localResult = true;
    	
    	Coordinate testCoord00a = new Coordinate(0,0);
    	result &= (testCoord00a.x == 0);
    	result &= (testCoord00a.y == 0);
    	
    	Coordinate testCoord00b = new Coordinate(0,0);
    	result &= (testCoord00b.x == 0);
    	result &= (testCoord00b.y == 0);

    	Coordinate testCoord13 = new Coordinate(1, 3);
    	result &= (testCoord13.x == 1);
    	result &= (testCoord13.y == 3);
    	
    	
    	localResult = true;
    	System.out.print(" - Testing equals self 0,0 --- ");
    	localResult &= testCoord00a.equals(testCoord00a);
    	System.out.println(localResult);
    	result &= localResult;

    	localResult = true;
    	System.out.print(" - Testing equals new 0,0 --- ");
    	localResult &= testCoord00a.equals(testCoord00b);
    	System.out.println(localResult);
    	result &= localResult;

    	localResult = true;
    	System.out.print(" - Testing nequals new 0,0 to 1,3 --- ");
    	localResult &= !testCoord00a.equals(testCoord13);
    	System.out.println(localResult);
    	result &= localResult;
    	localResult = true;
    	
    	System.out.print(" - Testing nequals new 0,0 to str --- ");
    	localResult &= !testCoord00a.equals("");
    	System.out.println(localResult);
    	result &= localResult;

    	return result;
    }
}
