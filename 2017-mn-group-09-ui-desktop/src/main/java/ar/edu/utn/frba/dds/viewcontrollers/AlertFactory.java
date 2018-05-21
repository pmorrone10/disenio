package ar.edu.utn.frba.dds.viewcontrollers;

import javafx.scene.control.Alert;

/**
 * Created by pablomorrone on 9/5/17.
 */
public class AlertFactory {

    public static Alert errorAlert(String title,  String subtitle){
        Alert a = new Alert(Alert.AlertType.ERROR);
        a.setTitle(title);
        a.setContentText(subtitle);
        return a;
    }

    public static Alert informationAlert(String title, String subtitle){
        Alert a = new Alert(Alert.AlertType.INFORMATION);
        a.setTitle(title);
        a.setHeaderText(null);
        a.setContentText(subtitle);
        return a;
    }


}
