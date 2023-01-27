package hellofx;

import java.io.FileWriter;
import java.io.IOException;
import java.sql.Time;

public class TmDat implements Comparable<TmDat>  {
	public int num;
	public String name;
	public long time;
	public String razm;
	public int type;
	
	TmDat(String name, int num, long time, String dim, int type) {
		this.name = name;
		this.num = num;
		this.time = time;
		this.razm = dim;
		this.type = type;
	}

	public String getParam() {
		return "Must be overrided";
	}
	
	@Override
	public int compareTo(TmDat st) {
		if(name.compareTo(st.name)==0)
			return 1;
		return name.compareTo(st.name);
	}
        
        public String geType(){
            String res;
            switch (this.type) {
                case 0:
                    res = "Long";
                    break;
                case 1:
                    res = "Double";
                    break;
                case 2:
                    res = "Code";
                    break;
                case 3:
                    res = "Point";
                    break;
                default:
                    res = "Unknown";
            }
            return res;
        }
}
