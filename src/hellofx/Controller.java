package hellofx;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.MultipleSelectionModel;
import javafx.stage.FileChooser;
import javafx.stage.Window;
import javafx.scene.control.CheckBox;
public class Controller implements Initializable {
    
    @FXML 
    
    private ListView<CheckBox> List_check;

   @FXML
    private LineChart<?, ?> chart;

    @FXML
    private Button config_button;

    @FXML
    private Button open_button;

    @FXML
    private Button show_button;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        CheckBox prib = new CheckBox("Go");
        List_check.getItems().addAll(prib);

        config_button.setOnAction(event ->{
            final FileChooser fileChooser = new FileChooser();
            Config.conf=fileChooser.showOpenDialog(config_button.getScene().getWindow());
            System.out.println(Config.conf);
            try {
                Config.read(Config.conf);
            } catch (SAXException | IOException | ParserConfigurationException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        });
        open_button.setOnAction(event ->{
            ReadXML r = new ReadXML(Config.xml, "output.txt");
		    try {
                r.load();
            } catch (ParserConfigurationException | SAXException | IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        TmDat tmDat;

		ReadDIM d = new ReadDIM(Config.dim);
        ReadTMI TMI;
		try {
            TMI = new ReadTMI(Config.tmi, Config.out, r, d);
            TMI.run();
            
            for (Map.Entry<String, Data> item : TMI.dataMap.entrySet()) {
                CheckBox p = new CheckBox(item.getKey());
                tmDat=item.getValue().getData(0);
                if(tmDat.type==1){
                List_check.getItems().addAll(p);         
                }
            }
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        ObservableList<CheckBox> boxes =  List_check.getItems();
 
        });
      
        show_button.setOnAction(event ->{
            ObservableList<CheckBox> boxes =  List_check.getItems();
            String name;
            TmDat tmDat;
            ObservableList datas = FXCollections.observableArrayList();

            for(int i=0;i<boxes.size();i++){
                if(boxes.get(i).isSelected()){
                    name=boxes.get(i).getText();
                    
                     for(int j=0;j<ReadTMI.dataMap.get(name).getSize();j++){
                       tmDat = ReadTMI.dataMap.get(name).getData(j);
                       Double y=Double.parseDouble(tmDat.getParam()); 
                       datas.add(new XYChart.Data(Long.toString(tmDat.time),y));
                     }
                }
            }
            
            XYChart.Series series = new XYChart.Series();
            series.setData(datas);
            chart.getData().clear();
            chart.getData().addAll(series);//,series1);

        });

        }

       

}

