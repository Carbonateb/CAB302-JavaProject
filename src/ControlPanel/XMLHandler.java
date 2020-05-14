package ControlPanel;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import javax.xml.parsers.*;
import java.io.*;
import java.io.IOException;


/**
 * Class for various tasks related to XML file handling
 *
 * @author Connor McHugh - n10522662
 */
public class XMLHandler {
	/**
	 * Method to read information for an XML file and return it
	 * @param filePath path to the XML file to work on
	 * @param tag name of the tag to get information from in the XML file
	 * @param attribute name of the attribute of a tag to get information from in the XML file: if "null" is passed as
	 *                  this parameter, it is ignored, and information is taken from tag instead
	 * @return the information that is found in the given XML file, according to the tag and attribute parameters
	 * @throws ParserConfigurationException
	 * @throws IOException
	 * @throws SAXException
	 */
	public static String xmlReader(String filePath, String tag, String attribute) throws ParserConfigurationException, IOException, SAXException {
		File file = new File(filePath);
		DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory
			.newInstance();
		DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
		Document document = documentBuilder.parse(file);

		if (document.getElementsByTagName("billboard").getLength() != 0) {
				if (document.getElementsByTagName(tag).getLength() != 0) {
					if (attribute.equals("null")) {
						return document.getElementsByTagName(tag).item(0).getTextContent();
					} else {
						return document.getElementsByTagName(tag).item(0).getAttributes().getNamedItem(attribute).getNodeValue();
					}
				} else {
//					System.out.println("Tag doesn't exist in the provided XML file!");
					return null;
				}
		} else {
//			System.out.println("This is not a valid billboard XML file!");
			return null;
		}



		// Check whether we need to find information from within the XML tags or the tag attribute
//		if (attribute.equals("null") && !tag.equals("billboard")) {
//			return document.getElementsByTagName(tag).item(0).getTextContent();
//		} else if (attribute.equals("null")) {
//			System.out.println("Cannot get value of surrounding <billboard> tags");
//			return null;
//		} else {
//			return document.getElementsByTagName(tag).item(0).getAttributes().getNamedItem(attribute).getNodeValue();
//		}
	}

	public static void xmlWriter() {

	}
}
