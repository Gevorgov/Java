package hellofx;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.ArrayList;

import java.util.TreeMap;
import java.util.TreeSet;

public class ReadTMI {

    private FileInputStream inStream;
    private int countService;
    private int countGood;
    private int type_0;
    private int type_1;
    private int type_2;
    private int type_3;
    private ReadXML rXML;
    private ReadDIM rDIM;

    private TreeSet<Integer> parametersSet;
    public static TreeMap<String, Data> dataMap;


    ReadTMI(String inpFile, String outFile, ReadXML XML_tree, ReadDIM dim) throws IOException{
        this.inStream = new FileInputStream(inpFile);
        this.countService = 0;
        this.countGood = 0;
        this.type_0 = 0;
        this.type_1 = 0;
        this.type_2 = 0;
        this.type_3 = 0;
        this.rXML = XML_tree;
        this.rDIM = dim;
        this.parametersSet = new TreeSet<Integer>();
        this.dataMap = new TreeMap<String, Data>();
    }

    public int getCountService() {
        return countService;
    }

    public int getCountGood() {
        return countGood;
    }

    public int getCountParameters() {
        return parametersSet.size();
    }

    public void run() throws IOException {

        byte[] buf = new byte[16];
        int countService = 0;
        int countGood = 0;

        while(inStream.read(buf) != -1) {
            int num = ((buf[0] << 8) & 0xff00) | (buf[1] & 0xff);
            if(num == 0xffff) {
                countService++;
                if(buf[6] == 1) {
                    inStream.skipNBytes(16);
                }
            }
            else {
                parametersSet.add(num); //количество параметров
                int type = (buf[7] & 0xF);
                String dim = rDIM.dimArr.get((int)(buf[6]));
                if(dim == null) {
                    dim = new String("dim=" + buf[6]);
                }
                String name = rXML.getMap().get(num);
                long time = (long)(buf[2]<<24 & 0xFF000000 | buf[3]<<16 & 0xFF0000 | buf[4]<<8 & 0xFF00 | buf[5] & 0xFF);
                TmDat data = null;

                if(type == 0) {
                    long lValue = ((buf[12] << 24) & 0xFF000000) | ((buf[13]<<16) & 0xFF0000) | 
                                ((buf[14] << 8) & 0xFF00) | (buf[15] & 0xFF);
                    data = new TmLong(name, num, time, dim, type, lValue);
                    type_0++;
                }
                else if(type == 1) {
                    double dValue = ByteBuffer.wrap(new byte[]
                        {buf[8],buf[9],buf[10],buf[11],buf[12],buf[13],buf[14],buf[15]}).getDouble();
                    data = new TmDouble(name, num, time, dim, type, dValue);
                    type_1++;
                }
                else if(type == 2) {
                    int len = (buf[10] << 8 & 0xFF00) | (buf[11] & 0xFF);
                    String form = "%" + len + "s";
                    String bin = Integer.toBinaryString((buf[12] << 24 & 0xFF000000) | (buf[13] << 16 & 0xFF0000) | 
                        (buf[14] << 8 & 0xFF00) | (buf[15] & 0xFF));
                    String val = String.format(form, bin).replace(' ', '0');
                    data = new TmCode(name, num, time, dim, type, val, len);
                    type_2++;
                }
                else if(type == 3) {
                    int len = buf[10] << 8 & 0xFF00 | buf[11] & 0xFF;
                    if(len > 4) {
                        inStream.skipNBytes(len-4);
                    }
                    data = new TmPoint(name, num, time, dim, type, len);
                    type_3++;
                }
                else {
                    System.out.println("Incorrect type");
                }
                if(data!= null) {
                    countGood++;
                    if(dataMap.containsKey(data.name)){
                        dataMap.get(data.name).addData(data);
                    }
                    else{
                        dataMap.put(data.name, new Data(data));
                    }
                    
                }
            }
        }
        this.countService = countService;
        this.countGood = countGood;
    }

    public int getType0(){
        return this.type_0;
    }

    public int getType1(){
        return this.type_1;
    }

    public int getType2(){
        return this.type_2;
    }

    public int getType3(){
        return this.type_3;
    }

    public ArrayList<String> getParametersList(){
        ArrayList<String> resultList = new ArrayList<String>();
        
        dataMap.forEach((key, value) -> resultList.add(key));
        return resultList;
    }
    
    public TreeMap<String, Data> getData(){
        return this.dataMap;
    } 
}
