package tests.rpgItemXmlParser;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Arrays;

import javax.xml.parsers.ParserConfigurationException;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.xml.sax.SAXException;

import application.coordinate.CardinalRotation;
import application.coordinate.Coordinate;
import application.itemWeight.IItemWeight;
import application.itemWeight.ItemWeightKg;
import application.rpgItem.RpgItem;
import application.rpgItemXmlParser.RpgItemXmlParser;

public class TestRpgItemXmlParser {
	RpgItem emptyItem;
	RpgItem containingItem;

	@Before
	public void setupRpgItems() {
		IItemWeight weightA = new ItemWeightKg(0);
		this.emptyItem = new RpgItem("TestEmptyBag", weightA, new Coordinate(0, 0), CardinalRotation.ZERO);
		this.containingItem = new RpgItem("TestNonEmptyBag", "", "", 1, weightA, 1, Arrays.asList(emptyItem),
				new Coordinate(0, 0), CardinalRotation.ZERO, Arrays.asList(new Coordinate(0, 0)),
				Arrays.asList(new Coordinate(0, 0)));
	}

	@Test
	public void basicItemToAndFromXml() throws ParserConfigurationException, SAXException, IOException {
		ByteArrayOutputStream xmlOut = new ByteArrayOutputStream();
		RpgItemXmlParser.saveXML(emptyItem, xmlOut);
		RpgItem parsedItem = RpgItemXmlParser.parseXML(new ByteArrayInputStream(xmlOut.toByteArray()));
		Assert.assertEquals(parsedItem, emptyItem);
	}

	@Test
	public void containingItemToAndFromXml() throws ParserConfigurationException, SAXException, IOException {
		ByteArrayOutputStream xmlOut = new ByteArrayOutputStream();
		RpgItemXmlParser.saveXML(containingItem, xmlOut);
		RpgItem parsedItem = RpgItemXmlParser.parseXML(new ByteArrayInputStream(xmlOut.toByteArray()));
		Assert.assertEquals(parsedItem, containingItem);
	}
}
