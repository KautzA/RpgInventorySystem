package application.rpgItem;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import application.coordinate.CardinalRotation;
import application.coordinate.Coordinate;
import application.itemWeight.IItemWeight;
import application.itemWeight.ItemWeightKg;

public class TestRpgItem {
	public static void main(String[] args) {
		System.out.println("Test RpgItem.java");
		System.out.println(testGetOccupiedPoints());
	}

    static boolean testGetOccupiedPoints() {
        System.out.println("testGetOccupiedPoints");
        boolean result = true;
        boolean localResult = true;

        IItemWeight weightA = new ItemWeightKg(0);

        RpgItem unmovedItem = new RpgItem("TestUnmovedItem",
                                        "",
                                        "",
                                        1,
                                        weightA,
                                        1,
                                        new ArrayList<RpgItem>(),
                                        new Coordinate(0,0),
                                        CardinalRotation.ZERO,
                                        new ArrayList<Coordinate>(Arrays.asList(new Coordinate(0,0),
                                                                                new Coordinate(1,0),
                                                                                new Coordinate(2,1))),
                                        new ArrayList<Coordinate>());
        RpgItem movedRotatedItem = new RpgItem("TestComplexItem",
                                               "",
                                               "",
                                               1,
                                               weightA,
                                               1,
                                               new ArrayList<RpgItem>(),
                                               new Coordinate(4,5),
                                               CardinalRotation.HALF_PI,
                                               new ArrayList<Coordinate>(Arrays.asList(new Coordinate(0,0),
                                                                                       new Coordinate(1,0),
                                                                                       new Coordinate(2,1))),
                                               new ArrayList<Coordinate>());
        {
            localResult = true;
            System.out.print(" - Testing unmovedItem --- ");
            List<Coordinate> offsetCoordinates = unmovedItem.getOccupiedPoints();
            localResult &= offsetCoordinates.size() == 3;
            localResult &= offsetCoordinates.contains(new Coordinate(0,0));
            localResult &= offsetCoordinates.contains(new Coordinate(1,0));
            localResult &= offsetCoordinates.contains(new Coordinate(2,1));
            System.out.println(localResult);
            result &= localResult;
            localResult = true;
        }

        {
            localResult = true;
            System.out.print(" - Testing movedRotatedItem --- ");
            List<Coordinate> offsetCoordinates = movedRotatedItem.getOccupiedPoints();
            localResult &= offsetCoordinates.size() == 3;
            localResult &= offsetCoordinates.contains(new Coordinate(4,5));
            localResult &= offsetCoordinates.contains(new Coordinate(4,6));
            localResult &= offsetCoordinates.contains(new Coordinate(3,7));
            System.out.println(localResult);
            result &= localResult;
            localResult = true;
        }

    	return result;
    }

}
