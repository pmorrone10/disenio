package ar.edu.utn.frba.dds.viewcontrollers;

import ar.edu.utn.frba.dds.exception.InvalidFileFormatException;
import ar.edu.utn.frba.dds.controllers.AutomaticLoader;
import ar.edu.utn.frba.dds.exception.ParserErrorException;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.control.Button;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by pablomorrone on 12/4/17.
 */
public class FileUploaderViewController implements Initializable{

    @FXML
    private Button searchFileButton;

    @FXML
    private TextField pathTextField;

    @FXML
    private Button loadButton;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.searchFileButton.setOnAction(event -> openFileChooser());
        this.loadButton.setOnAction(event -> loadFile());
    }

    private void openFileChooser(){
        FileChooser chooser = new FileChooser();
        chooser.setTitle("Elija el archivo");
        File f = chooser.showOpenDialog(new Stage());
        if (f.exists()){
            updateTextfield(f.getAbsolutePath());
        }
    }

    private void loadFile(){
        AutomaticLoader c = new AutomaticLoader();
        try {
            c.LoadCompaniesFromFile(pathTextField.getText());
            AlertFactory.informationAlert("Cargar de Archivo","Carga de archivo completa").show();
        }catch (InvalidFileFormatException ex){
            AlertFactory.errorAlert("Fallo la carga del archivo",ex.getMessage()).show();
        }catch (ParserErrorException e){
            AlertFactory.errorAlert("Fallo la carga del archivo",e.getMessage()).show();
        }
    }

    private void updateTextfield(String text){
        this.pathTextField.setText(text);
    }
}
