package hellofx;
import java.util.ArrayList;


public class Data {
    private ArrayList<TmDat> data;
    public boolean isPrintable;
    
    public Data(TmDat tData){
        data = new ArrayList<TmDat>();
        data.add(tData);
        isPrintable = false;
    }
    
    public TmDat getData(int i){
        return data.get(i);
    }
    
    public int getSize(){
        return data.size();
    }
    
    public void addData(TmDat data){
        this.data.add(data);
    }
}
