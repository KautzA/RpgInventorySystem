package application.rpgItemXmlParser;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.dom.DOMSource;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import application.coordinate.CardinalRotation;
import application.coordinate.Coordinate;
import application.coordinate.Rotation;
import application.itemWeight.IItemWeight;
import application.itemWeight.ItemWeightFactory;
import application.rpgItem.RpgItem;

public class RpgItemXmlParser {
	public static void saveXML(RpgItem item, OutputStream outputStream) throws ParserConfigurationException {
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = factory.newDocumentBuilder();
		Document xmlDocument = builder.newDocument();
		xmlDocument.appendChild(ItemToNode(item, xmlDocument));

		try {
			Source source = new DOMSource(xmlDocument);
			StreamResult result = new StreamResult(new OutputStreamWriter(outputStream, "ISO-8859-1"));
			Transformer xformer = TransformerFactory.newInstance().newTransformer();
			xformer.transform(source, result);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void saveXmlToFile(RpgItem item, String filepath)
			throws FileNotFoundException, ParserConfigurationException {
		OutputStream xmlFile = new FileOutputStream(filepath);
		saveXML(item, xmlFile);

	}

	public static RpgItem parseXML(InputStream xmlSource)
			throws ParserConfigurationException, SAXException, IOException {
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = factory.newDocumentBuilder();
		Document document = builder.parse(xmlSource);
		Element root = document.getDocumentElement();

		return NodeToRpgItem(root);
	}

	static RpgItem NodeToRpgItem(Node itemXml) {
		String name = null;
		String description = "";
		String link = "";
		int stackSize = 0;
		String weightUnit = "lbs";
		float weightValue = 0;
		float contentsWeightScale = 1;
		ArrayList<RpgItem> contents = new ArrayList<RpgItem>();
		Coordinate externalOffset = new Coordinate(0, 0);
		Rotation externalRotation = CardinalRotation.ZERO;
		ArrayList<Coordinate> externalPoints = new ArrayList<Coordinate>();
		ArrayList<Coordinate> internalPoints = new ArrayList<Coordinate>();

		NodeList children = itemXml.getChildNodes();
		int index = 0;
		while (index < children.getLength()) {
			Node child = children.item(index);
			switch (child.getNodeName()) {
			case ("itemname"):
				name = NodeToString(child);
				break;
			case ("itemdescription"):
				description = NodeToString(child);
				break;
			case ("link"):
				link = NodeToString(child);
				break;
			case ("stacksize"):
				int value = Integer.valueOf(NodeToString(child));
				if (value >= 0) {
					stackSize = value;
				}
				break;
			case ("weightunit"):
				weightUnit = NodeToString(child);
				break;
			case ("weightvalue"):
				weightValue = Float.valueOf(NodeToString(child));
				break;
			case ("contentsweightscale"):
				contentsWeightScale = Float.valueOf(NodeToString(child));
				break;
			case ("rpgitemgrid"):
				contents.add(NodeToRpgItem(child));
				break;
			case ("externaloffset"):
				externalOffset = NodeToCoordinate(child);
				break;
			case ("externalrotation"):
				externalRotation = NodeToRotation(child);
				break;
			case ("externalpoints"):
				externalPoints.addAll(NodeToCoordinateList(child));
				break;
			case ("internalpoints"):
				internalPoints.addAll(NodeToCoordinateList(child));
				break;

			}

			index++;
		}

		IItemWeight weight = ItemWeightFactory.GetWeight(weightUnit, weightValue);

		return new RpgItem(name, description, link, stackSize, weight, contentsWeightScale, contents, externalOffset,
				externalRotation, externalPoints, internalPoints);
	}

	static Rotation NodeToRotation(Node child) {
		switch (child.getChildNodes().item(0).getNodeValue()) {
		case ("0"):
		case ("zero"):
		case ("up"):
			return CardinalRotation.ZERO;
		case ("90"):
		case ("half_pi"):
		case ("left"):
			return CardinalRotation.HALF_PI;
		case ("180"):
		case ("pi"):
		case ("down"):
			return CardinalRotation.PI;
		case ("270"):
		case ("three_halves_pi"):
		case ("right"):
			return CardinalRotation.THREE_HALVES_PI;
		default:
			return CardinalRotation.ZERO;
		}
	}

	static List<Coordinate> NodeToCoordinateList(Node coordinateList) {
		ArrayList<Coordinate> result = new ArrayList<Coordinate>();
		NodeList children = coordinateList.getChildNodes();
		for (int index = 0; index < children.getLength(); index++) {
			Node child = children.item(index);
			result.add(NodeToCoordinate(child));
		}
		return result;
	}

	static Coordinate NodeToCoordinate(Node coordinate) {
		int x_coord = 0;
		int y_coord = 0;

		NodeList children = coordinate.getChildNodes();
		for (int index = 0; index < children.getLength(); index++) {
			Node child = children.item(index);
			switch (child.getNodeName()) {
			case ("x"):
				x_coord = Integer.valueOf(child.getChildNodes().item(0).getNodeValue());
				break;
			case ("y"):
				y_coord = Integer.valueOf(child.getChildNodes().item(0).getNodeValue());
				break;
			}
			index++;
		}
		return new Coordinate(x_coord, y_coord);
	}

	static String NodeToString(Node xmltag) {
		NodeList children = xmltag.getChildNodes();
		switch (children.getLength()) {
		case (0):
			return "";
		case (1):
			return children.item(0).getNodeValue();
		default:
			throw new IllegalArgumentException("Unexpected number of children in node");
		}
	}

	static Node ItemToNode(RpgItem item, Document parentXml) {
		Node result = parentXml.createElement("rpgitemgrid");
		result.appendChild(CreateTextNode("itemname", item.name, parentXml));
		result.appendChild(CreateTextNode("itemdescription", item.description, parentXml));
		result.appendChild(CreateTextNode("link", item.link, parentXml));
		result.appendChild(CreateTextNode("stacksize", String.valueOf(item.stackSize), parentXml));
		result.appendChild(CreateTextNode("weightunit", item.weight.getUnits(), parentXml));
		result.appendChild(CreateTextNode("weightvalue", String.valueOf(item.weight.getValue()), parentXml));
		result.appendChild(CreateTextNode("contentsweightscale", String.valueOf(item.contentsWeightScale), parentXml));
		for (RpgItem child_item : item.contents) {
			result.appendChild(ItemToNode(child_item, parentXml));
		}
		result.appendChild(CoordinateToNode("externaloffset", item.externalOffset, parentXml));
		result.appendChild(RotationToNode("externalrotation", item.externalRotation, parentXml));
		result.appendChild(CoordinateListToNode("externalpoints", item.externalPoints, parentXml));
		result.appendChild(CoordinateListToNode("internalpoints", item.internalPoints, parentXml));

		return result;
	}

	static Node CoordinateListToNode(String name, List<Coordinate> coordinates, Document parentXml) {
		Node result = parentXml.createElement(name);
		for (Coordinate point : coordinates) {
			result.appendChild(CoordinateToNode("coordinate", point, parentXml));
		}
		return result;
	}

	static Node CoordinateToNode(String name, Coordinate coordinate, Document parentXml) {
		Node result = parentXml.createElement(name);
		Node coord_x = CreateTextNode("x", String.valueOf(coordinate.x), parentXml);
		Node coord_y = CreateTextNode("y", String.valueOf(coordinate.y), parentXml);
		result.appendChild(coord_x);
		result.appendChild(coord_y);
		return result;
	}

	static Node RotationToNode(String name, Rotation rotation, Document parentXml) {
		if (rotation instanceof CardinalRotation) {
			switch ((CardinalRotation) rotation) {
			case ZERO:
				return CreateTextNode(name, "0", parentXml);
			case HALF_PI:
				return CreateTextNode(name, "90", parentXml);
			case PI:
				return CreateTextNode(name, "180", parentXml);
			case THREE_HALVES_PI:
				return CreateTextNode(name, "270", parentXml);
			default:
				return CreateTextNode(name, "0", parentXml);
			}
		}
		return CreateTextNode(name, "0", parentXml);
	}

	static Node CreateTextNode(String name, String value, Document parentXml) {
		Node result = parentXml.createElement(name);
		result.appendChild(parentXml.createTextNode(value));
		return result;
	}
}
