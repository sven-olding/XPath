import java.io.ByteArrayInputStream;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class Main {
	private static final String xmlToParse = "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\">\r\n"
			+ "   <soapenv:Body>\r\n" + "      <getGeschaeftsvorfallReturn xmlns=\"urn:DefaultNamespace\">\r\n"
			+ "         <geschaeftsvorfall xmlns=\"\">\r\n" + "            <id>39679SOLGB9MDRWF7C</id>\r\n"
			+ "            <name>Erfassung Neugeschäft</name>\r\n" + "            <erfolgreich>true</erfolgreich>\r\n"
			+ "            <buchung>\r\n" + "               <id>39679SOLGB9MDRYF7C</id>\r\n"
			+ "               <name>Buchung Kredit</name>\r\n" + "               <anzeigetext xsi:nil=\"true\"/>\r\n"
			+ "               <erfolgreich>true</erfolgreich>\r\n" + "            </buchung>\r\n"
			+ "            <buchung>\r\n" + "               <id>39679SOLGB9MDS2F7C</id>\r\n"
			+ "               <name>Buchung Rahmen</name>\r\n" + "               <anzeigetext xsi:nil=\"true\"/>\r\n"
			+ "               <erfolgreich>true</erfolgreich>\r\n" + "            </buchung>\r\n"
			+ "            <buchung>\r\n" + "               <id>39679SOLGB9MDS4F7C</id>\r\n"
			+ "               <name>Buchung Verbindung</name>\r\n"
			+ "               <anzeigetext xsi:nil=\"true\"/>\r\n"
			+ "               <erfolgreich>true</erfolgreich>\r\n" + "            </buchung>\r\n"
			+ "            <buchung>\r\n" + "               <id>39679SOLGB9MDS6F7C</id>\r\n"
			+ "               <name>Auszahlung</name>\r\n" + "               <anzeigetext xsi:nil=\"true\"/>\r\n"
			+ "               <erfolgreich>true</erfolgreich>\r\n" + "            </buchung>\r\n"
			+ "            <buchung>\r\n" + "               <id>39679SOLGB9MDS8F7C</id>\r\n"
			+ "               <name>Zahlungsverkehrsbuchung 391</name>\r\n"
			+ "               <anzeigetext xsi:nil=\"true\"/>\r\n"
			+ "               <erfolgreich>true</erfolgreich>\r\n" + "            </buchung>\r\n"
			+ "            <buchung>\r\n" + "               <id>39679SOLGB9MDSAF7C</id>\r\n"
			+ "               <name>SWIFT</name>\r\n" + "               <anzeigetext xsi:nil=\"true\"/>\r\n"
			+ "               <erfolgreich>true</erfolgreich>\r\n" + "            </buchung>\r\n"
			+ "         </geschaeftsvorfall>\r\n" + "         <erfolgreich xmlns=\"\">true</erfolgreich>\r\n"
			+ "      </getGeschaeftsvorfallReturn>\r\n" + "   </soapenv:Body>\r\n" + "</soapenv:Envelope>";

	public static void main(String[] args) {
		DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
		docBuilderFactory.setNamespaceAware(false);
		try {
			DocumentBuilder builder = docBuilderFactory.newDocumentBuilder();
			Document xmlDocument = builder.parse(new ByteArrayInputStream(xmlToParse.getBytes("UTF-8")));

			XPath xPath = XPathFactory.newInstance().newXPath();
			String expression = null;
			NodeList nodeList = null;

			// Geschäftsvorfall ID
			System.out.println("## Geschäftsvorfall ID");
			expression = "/Envelope/Body/getGeschaeftsvorfallReturn/geschaeftsvorfall/name/text()";
			nodeList = (NodeList) xPath.compile(expression).evaluate(xmlDocument, XPathConstants.NODESET);
			printNodeList(nodeList);

			// Buchungsnamen
			System.out.println("## Buchungsnamen");
			expression = "/Envelope/Body/getGeschaeftsvorfallReturn/geschaeftsvorfall//buchung/name/text()";
			nodeList = (NodeList) xPath.compile(expression).evaluate(xmlDocument, XPathConstants.NODESET);
			printNodeList(nodeList);

			// Letzte Buchung
			System.out.println("## Letzte Buchung");
			expression = "/Envelope/Body/getGeschaeftsvorfallReturn/geschaeftsvorfall//buchung[last()]/id/text()";
			nodeList = (NodeList) xPath.compile(expression).evaluate(xmlDocument, XPathConstants.NODESET);
			printNodeList(nodeList);

			// Buchung mit Name = Auszahlung
			System.out.println("## Buchung mit Name = Auszahlung");
			expression = "/Envelope/Body/getGeschaeftsvorfallReturn/geschaeftsvorfall//buchung[name='Auszahlung']/id/text()";
			nodeList = (NodeList) xPath.compile(expression).evaluate(xmlDocument, XPathConstants.NODESET);
			printNodeList(nodeList);

		} catch (ParserConfigurationException | SAXException | IOException | XPathExpressionException e) {
			e.printStackTrace();
		}

	}

	private static void printNodeList(NodeList nodeList) {
		for (int i = 0; i < nodeList.getLength(); i++) {
			Node node = nodeList.item(i);
			System.out.println(node.getNodeValue());
		}
	}
}
