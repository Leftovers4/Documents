package presentation.util.other;

import javafx.scene.control.ComboBox;
import util.AddTradProducer;
import util.EnumFactory;

import java.time.LocalTime;
import java.util.Iterator;

/**
 * Created by Hitiger on 2016/12/11.
 * Description :
 */
public class MyComboBox {

    public static void initHourBox(ComboBox comboBox){
        for (int i = 0; i < 24; i++){
            comboBox.getItems().add(i);
        }
        comboBox.setValue(LocalTime.now().getHour());
    }

    public static void initMinBox(ComboBox comboBox){
        for (int i = 0; i < 60; i++){
            comboBox.getItems().add(i);
        }
        comboBox.setValue(LocalTime.now().getMinute());
    }

    public static void initAreaBox(ComboBox cityBox, ComboBox areaBox){
        cityBox.getItems().clear();
        Iterator<String> cityList = AddTradProducer.getAllAddress();
        while (cityList.hasNext()){
            cityBox.getItems().add(cityList.next());
        }
        cityBox.getSelectionModel().selectedItemProperty().addListener(
                (o, oldValue, newValue) ->{
                    areaBox.getItems().clear();
                    Iterator<String> areaList = AddTradProducer.getTradingAreasByAddress(newValue.toString());
                    while (areaList.hasNext()) areaBox.getItems().add(areaList.next());

                    areaBox.setValue(areaBox.getItems().get(0));
                }
        );
    }

    public static void initRoomBox(ComboBox comboBox){
        Iterator<String> roomList = EnumFactory.getAllRoomTypes();
        while (roomList.hasNext()) comboBox.getItems().add(roomList.next());
        comboBox.getItems().remove(comboBox.getItems().size()-1);
        comboBox.setValue(comboBox.getItems().get(0));
    }
}
