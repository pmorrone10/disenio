package ar.edu.utn.frba.dds.viewcontrollers;

/**
 * Created by pablomorrone on 12/4/17.
 */
import ar.edu.utn.frba.dds.LoginManager;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import org.apache.log4j.Logger;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class MainViewController implements Initializable {

    final static Logger logger = Logger.getLogger(MainViewController.class);

    @FXML
    private Label title;

    @FXML
    private Button loadFileButton;

    @FXML
    private Button queryButton;

    @FXML
    private Button accountButton;

    @FXML
    private Button methodologyButton;

    @FXML
    private Button exitButton;

    @FXML
    private AnchorPane anchorpane;

    @FXML
    private AnchorPane secAnchorPane;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }

    public void initSessionID(final LoginManager loginManager){
        this.loadFileButton.setOnAction(event -> showLoadFileView());
        this.queryButton.setOnAction(event -> showQueryView());
        this.accountButton.setOnAction(event -> showAccountView());
        this.methodologyButton.setOnAction(event -> showMethodologyView());
        this.exitButton.setOnAction(event -> closeApp());
    }

    private void showLoadFileView(){
        replaceMainPane("/views/fileUploader.fxml");
    }

    private void showQueryView(){
        replaceMainPane("/views/search.fxml");
    }

    private void showAccountView(){
        replaceMainPane("/views/indicators.fxml");
    }

    private void showMethodologyView() {replaceMainPane("/views/methodology.fxml");}

    private void replaceMainPane(String path) {
        try {
            AnchorPane newPane = FXMLLoader.load(getClass().getResource(path));
            List<Node> parentChildren = anchorpane.getChildren();
            parentChildren.set(parentChildren.indexOf(secAnchorPane), newPane);
            secAnchorPane = newPane;
            secAnchorPane.setPrefHeight(anchorpane.getHeight());
            secAnchorPane.setPrefWidth(anchorpane.getWidth());
            anchorpane.setTopAnchor(secAnchorPane, (double) 0);
            anchorpane.setBottomAnchor(secAnchorPane, (double) 0);
            anchorpane.setLeftAnchor(secAnchorPane, (double) 0);
            anchorpane.setRightAnchor(secAnchorPane, (double) 0);

        }catch (Exception e){
            AlertFactory.errorAlert("Error","No se puede cargar la pantalla. Intente mas tarde.").show();
            logger.debug("No se puede instanciar. Error: " + e.toString());
        }
    }

    private void closeApp(){
        Platform.exit();
        System.exit(0);
    }
}
