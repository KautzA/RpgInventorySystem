package application.rpgItem;

import java.util.Arrays;

import application.coordinate.CardinalRotation;
import application.coordinate.Coordinate;
import application.itemWeight.IItemWeight;
import application.itemWeight.ItemWeightKg;

public class TestRpgItem {
    public static void main(String[] args) {
        System.out.println("Test RpgItem.java");
        System.out.println(testEquals());
    }

	static boolean testEquals() {
	    System.out.println("testEquals");
        boolean result = true;
        boolean localResult = true;

        IItemWeight weightA = new ItemWeightKg(0);

        RpgItem emptyItem = new RpgItem("TestEmptyBag", weightA, new Coordinate(0,0), CardinalRotation.ZERO);
        RpgItem emptyItemCopy = new RpgItem("TestEmptyBag", weightA, new Coordinate(0,0), CardinalRotation.ZERO);
        RpgItem containingItem = new RpgItem("TestNonEmptyBag",
                                             "",
                                             "",
                                             1,
                                             weightA,
                                             1,
                                             Arrays.asList(emptyItem),
                                             new Coordinate(0,0),
                                             CardinalRotation.ZERO,
                                             Arrays.asList(new Coordinate(0,0)),
                                             Arrays.asList(new Coordinate(0,0)));
        RpgItem containingItemCopy = new RpgItem("TestNonEmptyBag",
                                                 "",
                                                 "",
                                                 1,
                                                 weightA,
                                                 1,
                                                 Arrays.asList(emptyItemCopy),
                                                 new Coordinate(0,0),
                                                 CardinalRotation.ZERO,
                                                 Arrays.asList(new Coordinate(0,0)),
                                                 Arrays.asList(new Coordinate(0,0)));

        RpgItem containingItemDifferentContents = new RpgItem("TestNonEmptyBag",
                                                              "",
                                                              "",
                                                              1,
                                                              weightA,
                                                              1,
                                                              Arrays.asList(containingItem),
                                                              new Coordinate(0,0),
                                                              CardinalRotation.ZERO,
                                                              Arrays.asList(new Coordinate(0,0)),
                                                              Arrays.asList(new Coordinate(0,0)));

        {
            localResult = true;
            System.out.print(" - Testing basicItem with iteself --- ");
            localResult &= (emptyItem.equals(emptyItem));
            System.out.println(localResult);
            result &= localResult;
            localResult = true;
        }

        {
            localResult = true;
            System.out.print(" - Testing basicItem with a duplicate --- ");
            localResult &= (emptyItem.equals(emptyItemCopy));
            System.out.println(localResult);
            result &= localResult;
            localResult = true;
        }

        {
            localResult = true;
            System.out.print(" - Testing containingItem not equal to emptyitem --- ");
            localResult &= !(containingItem.equals(emptyItem));
            System.out.println(localResult);
            result &= localResult;
            localResult = true;
        }

        {
            localResult = true;
            System.out.print(" - Testing containingItem with a duplicate --- ");
            localResult &= (containingItem.equals(containingItemCopy));
            System.out.println(localResult);
            result &= localResult;
            localResult = true;
        }

        {
            localResult = true;
            System.out.print(" - Testing containingItem not equal to item with different contents --- ");
            localResult &= !(containingItem.equals(containingItemDifferentContents));
            System.out.println(localResult);
            result &= localResult;
            localResult = true;
        }

        return result;
    }

}
