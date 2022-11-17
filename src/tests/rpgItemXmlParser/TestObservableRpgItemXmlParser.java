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
import application.rpgItem.ObservableRpgItem;
import application.rpgItemXmlParser.ObservableRpgItemXmlParser;

public class TestObservableRpgItemXmlParser {
	ObservableRpgItem emptyItem;
	ObservableRpgItem containingItem;

	@Before
	public void setupRpgItems() {
		IItemWeight weightA = new ItemWeightKg(0);
		this.emptyItem = new ObservableRpgItem("TestEmptyBag", weightA, new Coordinate(0, 0), CardinalRotation.ZERO);
		this.containingItem = new ObservableRpgItem("TestNonEmptyBag", "", "", 1, weightA, 1, Arrays.asList(emptyItem),
				new Coordinate(0, 0), CardinalRotation.ZERO, Arrays.asList(new Coordinate(0, 0)),
				Arrays.asList(new Coordinate(0, 0)));
	}

	@Test
	public void basicItemToAndFromXml() throws ParserConfigurationException, SAXException, IOException {
		ByteArrayOutputStream xmlOut = new ByteArrayOutputStream();
		ObservableRpgItemXmlParser.saveXML(emptyItem, xmlOut);
		ObservableRpgItem parsedItem = ObservableRpgItemXmlParser.parseXML(new ByteArrayInputStream(xmlOut.toByteArray()));
		Assert.assertEquals(parsedItem, emptyItem);
	}

	@Test
	public void containingItemToAndFromXml() throws ParserConfigurationException, SAXException, IOException {
		ByteArrayOutputStream xmlOut = new ByteArrayOutputStream();
		ObservableRpgItemXmlParser.saveXML(containingItem, xmlOut);
		ObservableRpgItem parsedItem = ObservableRpgItemXmlParser.parseXML(new ByteArrayInputStream(xmlOut.toByteArray()));
		Assert.assertEquals(parsedItem, containingItem);
	}
}
