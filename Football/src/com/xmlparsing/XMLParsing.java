package com.xmlparsing;

import java.io.IOException;
import java.io.StringReader;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.w3c.dom.CharacterData;

public class XMLParsing {
	private String xml;

	public XMLParsing(String xml) {
		this.xml = xml;
	}

	public void readXML() {
		DocumentBuilder db;
		try {
			db = DocumentBuilderFactory.newInstance().newDocumentBuilder();
	
		InputSource is = new InputSource();
		is.setCharacterStream(new StringReader(xml));

		Document doc = db.parse(is);
		NodeList nodes = doc.getElementsByTagName("Event");
	
		for (int i = 0; i < nodes.getLength(); i++) {
			Element element = (Element) nodes.item(i);
			System.out.println("Sports Name " + element.getAttribute("sportsName"));
			NodeList team1 = element.getElementsByTagName("team1");
			Element line = (Element) team1.item(0);
			System.out.println("Team 1 Name: " + getCharacterDataFromElement(line));

			NodeList team2 = element.getElementsByTagName("team2");
			line = (Element) team2.item(0);
			System.out.println("Team 2 Name: " + getCharacterDataFromElement(line));
			
			NodeList date = element.getElementsByTagName("sportDate");
			line = (Element) date.item(0);
			System.out.println("Team 2 Name: " + getCharacterDataFromElement(line));
			
			NodeList time = element.getElementsByTagName("sportTime");
			line = (Element) time.item(0);
			System.out.println("Team 2 Name: " + getCharacterDataFromElement(line));
			
			
			
		}
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static String getCharacterDataFromElement(Element e) {
		Node child = e.getFirstChild();
		if (child instanceof CharacterData) {
			CharacterData cd = (CharacterData) child;
			return cd.getData();
		}
		return "";
	}

}
