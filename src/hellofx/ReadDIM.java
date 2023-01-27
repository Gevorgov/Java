package hellofx;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.TreeMap;

public class ReadDIM {
	public TreeMap<Integer, String> dimArr;
	private String inDIM;
	
	ReadDIM(String inDIM){
		dimArr = new TreeMap<Integer, String>();
		this.inDIM = inDIM;
	}
	
	public void load() throws FileNotFoundException, IOException {
		try {
			BufferedReader reader = new BufferedReader(new FileReader(inDIM));
			String line;
			int num = 1;
			while((line = reader.readLine()) != null) {
				if(line.length()>1)
					dimArr.put(num, line);
				num++;
			}
			reader.close();
		} 
		finally {
			
        }
	}
}
