package hellofx;

import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.TreeMap;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;


public class ReadXML {
	private TreeMap<Integer, String> map = new TreeMap<Integer, String>();
	private String inputFile;
	private String out;
	private int countOfParameters;
	
	ReadXML(String inFile, String outFile){
		this.inputFile = inFile;
		this.out = outFile;
	}
	
	public int getCountOfParameters() {
		return countOfParameters;
	}
	
	public void load() throws ParserConfigurationException, SAXException, IOException {
		int number;
		String name;
		
		Document doc;
		
		InputStream inputStream = new FileInputStream(this.inputFile);
		DocumentBuilder parser = DocumentBuilderFactory.newInstance().newDocumentBuilder();
		doc = parser.parse(inputStream);
		final NodeList topNodes = doc.getElementsByTagName("Param");
		
		for(int i =0; i<topNodes.getLength(); i++) {
			final Node node = topNodes.item(i);
			if(!(node instanceof Element))
				continue;
			final Element element = (Element)node;
			if("Param".equals(element.getTagName())) {
				number = Integer.parseInt(element.getAttribute("number"));
				name = element.getAttribute("name");
				map.put(number, name);
			}
		}
		countOfParameters = map.size();
		inputStream.close();
	}
	
	void print() throws IOException {
		FileWriter outF = new FileWriter(out);
		
		for(Map.Entry<Integer, String> item : map.entrySet()) {
			outF.write(item.getKey() + " " + item.getValue() + "\n");
			
		}
		outF.write(String.format("%d", map.size()));
		outF.close();
	}
	
	public TreeMap<Integer, String> getMap() {
		return map;
	}
	
}
