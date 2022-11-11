package application.rpgItemXmlParser;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.Arrays;

import application.coordinate.CardinalRotation;
import application.coordinate.Coordinate;
import application.itemWeight.IItemWeight;
import application.itemWeight.ItemWeightKg;
import application.rpgItem.RpgItem;

public class TestRpgItemXmlParser {
	public static void main(String[] args) {
		System.out.println("Test RpgItemXmlParser.java");
		System.out.println(testCircle());
		System.out.println("abc".compareTo("abe"));
	}

	static boolean testCircle() {
		System.out.println("testCircle");
		boolean result = true;
		boolean localResult = true;

		IItemWeight weightA = new ItemWeightKg(0);

		RpgItem emptyItem = new RpgItem("TestEmptyBag", weightA, new Coordinate(0, 0), CardinalRotation.ZERO);
		RpgItem containingItem = new RpgItem("TestNonEmptyBag", "", "", 1, weightA, 1, Arrays.asList(emptyItem),
				new Coordinate(0, 0), CardinalRotation.ZERO, Arrays.asList(new Coordinate(0, 0)),
				Arrays.asList(new Coordinate(0, 0)));

		{
			localResult = true;
			System.out.print(" - Testing basicItem to and from xml --- ");
			try {
				ByteArrayOutputStream xmlOut = new ByteArrayOutputStream();
				RpgItemXmlParser.saveXML(emptyItem, xmlOut);
				RpgItem parsedItem = RpgItemXmlParser.parseXML(new ByteArrayInputStream(xmlOut.toByteArray()));
				localResult &= (parsedItem.equals(emptyItem));
			} catch (Exception e) {
				e.printStackTrace();
				localResult = false;
			}
			System.out.println(localResult);
			result &= localResult;
			localResult = true;
		}

		{
			localResult = true;
			System.out.print(" - Testing basicItem to saved xml --- ");
			try {
				RpgItemXmlParser.saveXmlToFile(emptyItem, "testBasicItem.xml");
			} catch (Exception e) {
				e.printStackTrace();
				localResult = false;
			}
			System.out.println(localResult);
			result &= localResult;
			localResult = true;
		}

		{
			localResult = true;
			System.out.print(" - Testing complexItem to and from xml --- ");
			try {
				ByteArrayOutputStream xmlOut = new ByteArrayOutputStream();
				RpgItemXmlParser.saveXML(containingItem, xmlOut);
				System.out.println(xmlOut.toString());
				RpgItem parsedItem = RpgItemXmlParser.parseXML(new ByteArrayInputStream(xmlOut.toByteArray()));
				localResult &= (parsedItem.equals(containingItem));
			} catch (Exception e) {
				e.printStackTrace();
				localResult = false;
			}
			System.out.println(localResult);
			result &= localResult;
			localResult = true;
		}

		{
			localResult = true;
			System.out.print(" - Testing basicItem to saved xml --- ");
			try {
				RpgItemXmlParser.saveXmlToFile(containingItem, "testContainingItem.xml");
			} catch (Exception e) {
				e.printStackTrace();
				localResult = false;
			}
			System.out.println(localResult);
			result &= localResult;
			localResult = true;
		}
		return result;
	}
}
