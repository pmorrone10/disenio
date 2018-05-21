package ar.edu.utn.frba.dds.viewcontrollers;

import ar.edu.utn.frba.dds.dao.IndicatorsDao;
import ar.edu.utn.frba.dds.exception.DuplicateValueException;
import ar.edu.utn.frba.dds.exception.UnknownIndicatorException;
import ar.edu.utn.frba.dds.models.Indicator;
import ar.edu.utn.frba.dds.models.views.IndicatorTable;
import ar.edu.utn.frba.dds.models.users.User;
import ar.edu.utn.frba.dds.utils.ConfigurationUtils;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import org.apache.log4j.Logger;
import org.hibernate.Session;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by pablomorrone on 13/5/17.
 */
public class IndicatorViewController implements Initializable {

    final static Logger logger = Logger.getLogger(IndicatorViewController.class);

    //TODO:cambiar este valor hardcodeado a un archivo de configuracion o algun lugar en comun para obtenerlo
    final String property = "antiguedad";
    @FXML
    private TextField txtIndicator;

    @FXML
    private TextField txtEpression;

    @FXML
    private Label label;

    @FXML
    private Button addButton;

    @FXML
    private Button newButton;

    @FXML
    private Button deleteButton;

    @FXML
    private TableView<IndicatorTable> tableview;

    @FXML
    private TableColumn<IndicatorTable,String> columnName;

    @FXML
    private TableColumn<IndicatorTable,String> columnExpression;

    private IndicatorsDao iDao;

    private User user;

    public User getUser() {
        return user;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.user = ar.edu.utn.frba.dds.Session.getSession().getUser();
        this.iDao = new IndicatorsDao();
        this.label.setText(labelText());
        setupTableview();
        loadTable();
        newButton.setOnAction(event -> cleanFields());
        addButton.setOnAction(event -> add());
        deleteButton.setOnAction(event -> delete());
    }

    private void setupTableview(){
        columnName.setCellValueFactory(new PropertyValueFactory<>("name"));
        columnExpression.setCellValueFactory(new PropertyValueFactory<>("expression"));
        this.tableview.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) ->
        {
            if (newSelection != null) {
                txtIndicator.setText(newSelection.getName());
                txtEpression.setText(newSelection.getExpression());
            }
        });
    }

    private void loadTable(){
        this.tableview.setItems(FXCollections.observableArrayList(IndicatorTable.transform(iDao.getIndicators(getUser()))));
    }

    private void cleanFields() {
        txtIndicator.setText("");
        txtEpression.setText("");
    }

    private void add() {
        Indicator indicator = new Indicator(getUser(), txtIndicator.getText(), txtEpression.getText());
        try {
            iDao.addIndicatorIfDontExist(indicator);
            cleanFields();
            loadTable();
        }catch (DuplicateValueException ex){
            AlertFactory.errorAlert("","Ya se encuentra registrado un indicador con el mismo nombre.").show();
        }

    }

    private void delete() {
        try {
            iDao.deleteIndicator(txtIndicator.getText());
            cleanFields();
            loadTable();
        }catch (UnknownIndicatorException ex){
            AlertFactory.errorAlert("","No existe el indicador que uds desea borrar").show();
        }catch (Exception ex){
            AlertFactory.errorAlert("","No se pudo borrar el indicador").show();
        }

    }

    private String labelText(){
        return "Si se desea calcular la antigüedad de una empresa usar la variable " + ConfigurationUtils.getConfiguration("ar.edu.utn.frba.dds.models.indicator.antiquityLabel");
    }
}
