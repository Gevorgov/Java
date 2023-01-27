package hellofx;

import java.io.File;
import java.io.IOException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class Config {
	public static File conf;
	static String tmi;  
	static String xml;
	static String dim;
	static String out;	
	static void read (File conf2) throws SAXException, IOException, ParserConfigurationException
	{
		String tmi_in, tmi_out, xml_in, dim_in;		
		DocumentBuilder parser = DocumentBuilderFactory.newInstance().newDocumentBuilder();
		Document doc = parser.parse(conf2);
		NodeList tmi_list = doc.getElementsByTagName("tmi");
		for(int i=0; i < tmi_list.getLength(); i++)
		{
			Node node = tmi_list.item(i);
			Element element = (Element)node;
			tmi_in = element.getAttribute("in");
			tmi_out = element.getAttribute("out");
			tmi = tmi_in;
			out = tmi_out;
		}
		
		
		
		NodeList xml_list = doc.getElementsByTagName("xml");
		for(int i=0; i < xml_list.getLength(); i++)
		{
			Node node = xml_list.item(i);
			Element element = (Element)node;
			xml_in = element.getAttribute("in");
			xml = xml_in;
		}
		
		NodeList dim_list = doc.getElementsByTagName("dim");
		for(int i=0; i < dim_list.getLength(); i++)
		{
			Node node = dim_list.item(i);
			Element element = (Element)node;
			dim_in = element.getAttribute("in");
			dim = dim_in;
		}
		
    }
}
