package ar.edu.utn.frba.dds.viewcontrollers;

/**
* Created by pablomorrone on 12/6/17.
*/
import ar.edu.utn.frba.dds.Session;
import ar.edu.utn.frba.dds.dao.CompaniesDao;
import ar.edu.utn.frba.dds.dao.IndicatorsDao;
import ar.edu.utn.frba.dds.dao.MethodologiesDao;
import ar.edu.utn.frba.dds.exception.DeleteException;
import ar.edu.utn.frba.dds.exception.DuplicateValueException;
import ar.edu.utn.frba.dds.exception.IncompleteFormException;
import ar.edu.utn.frba.dds.models.Company;
import ar.edu.utn.frba.dds.models.Indicator;
import ar.edu.utn.frba.dds.models.users.User;
import ar.edu.utn.frba.dds.models.methodology.*;
import ar.edu.utn.frba.dds.models.views.MethodologyTable;
import ar.edu.utn.frba.dds.models.methodology.comparer.MethodologyComparerTypeEnum;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.StringConverter;
import org.apache.log4j.Logger;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;


public class MethodologyViewController implements Initializable{

    final static Logger logger = Logger.getLogger(MethodologyViewController.class);

    @FXML
    private TextField txtName;

    @FXML
    private ComboBox<MethodologyTypeEnum> cbMethodologyType;

    @FXML
    private ComboBox<MethodologyComparerTypeEnum> cbOperation;

    @FXML
    private ComboBox<Indicator> cbIndicator;

    @FXML
    private TextField txtValue;

    @FXML
    private ComboBox<Company> cbCompanies;

    @FXML
    private Button addButton;

    @FXML
    private Button deleteButton;

    @FXML
    private TableView<MethodologyTable> tableviews;

    @FXML
    private TableColumn<MethodologyTable, String> columnName;

    @FXML
    private TableColumn<MethodologyTable, String> columnType;

    @FXML
    private TableColumn<MethodologyTable, String> columnOperation;

    @FXML
    private TableColumn<MethodologyTable, String> columnIndicator;

    @FXML
    private TableColumn<MethodologyTable, String> columnValue;

    private MethodologiesDao mDao;

    private CompaniesDao companiesDao;

    private IndicatorsDao iDao;

    private User user;

    public User getUser() {
        return user;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.user = Session.getSession().getUser();
        this.mDao = new MethodologiesDao();
        this.iDao = new IndicatorsDao();
        this.companiesDao = new CompaniesDao();
        setupInicialFields();
        setupCompanies();
    }

    private void setupInicialFields(){
        this.txtValue.setVisible(false);
        this.cbCompanies.setVisible(false);
        this.cbOperation.setVisible(false);
        setupIndicator();
        setupMethodology();
        setupCompanies();
        setupTableview();
        loadTable();
        this.addButton.setOnAction(event -> addMethodology());
        this.deleteButton.setOnAction(event -> delete());
    }

    private void setupTableview(){
        columnName.setCellValueFactory(new PropertyValueFactory<>("name"));
        columnIndicator.setCellValueFactory(new PropertyValueFactory<>("indicator"));
        columnOperation.setCellValueFactory(new PropertyValueFactory<>("operation"));
        columnValue.setCellValueFactory(new PropertyValueFactory<>("value"));
        columnType.setCellValueFactory(new PropertyValueFactory<>("type"));
        this.tableviews.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) ->
        {
            if (newSelection != null) {
                txtName.setText(newSelection.getName());
                txtValue.setText(newSelection.getValue());

            }
        });
    }

    private void loadTable(){
        this.tableviews.setItems(FXCollections.observableArrayList(MethodologyTable.some(mDao.getMethodologies(getUser()))));
    }

    private void setupIndicator(){
        this.cbIndicator.setItems(FXCollections.observableArrayList(iDao.getIndicators(getUser())));
        this.cbIndicator.setConverter(new StringConverter<Indicator>() {
            @Override
            public String toString(Indicator object) {
                return object.getName();
            }

            @Override
            public Indicator fromString(String string) {
                return null;
            }});
    }

    private void setupCompanies(){
        this.cbCompanies.setItems(FXCollections.observableArrayList(companiesDao.getCompanies()));
        this.cbCompanies.setConverter(new StringConverter<Company>() {
            @Override
            public String toString(Company object) {
                return object.getName();
            }

            @Override
            public Company fromString(String string) {
                return null;
            }
        });
    }

    private void setupMethodology(){
        this.cbMethodologyType.setItems(FXCollections.observableArrayList(MethodologyTypeEnum.values()));
        this.cbMethodologyType.setConverter(new StringConverter<MethodologyTypeEnum>() {
            @Override
            public String toString(MethodologyTypeEnum object) {
                return object.getDescription();
            }

            @Override
            public MethodologyTypeEnum fromString(String string) {
                /*return dao.getByPropertyValue(MethodologyType.class,"name",
                        string);*/
                return null;
            }});

        this.cbMethodologyType.valueProperty().addListener((observable, oldValue, newValue) -> {
            try {
                switch (newValue.getId()){
                    case 1:
                        cbCompanies.setVisible(true);
                        cbOperation.setVisible(true);
                        txtValue.setVisible(false);
                        break;
                    case 2:
                        cbOperation.setVisible(true);
                        break;
                    case 3:
                        cbCompanies.setVisible(false);
                        cbOperation.setVisible(true);
                        txtValue.setVisible(true);
                        break;
                    default:
                        cbCompanies.setVisible(false);
                        txtValue.setVisible(false);
                        cbOperation.setVisible(false);
                        break;
                }
                setupMethodologyComparator(MethodologyTypeEnum.getComparer(newValue));
            }catch (NullPointerException ex){
                cbCompanies.setVisible(false);
                txtValue.setVisible(false);
                cbOperation.setVisible(false);
            }

        });
    }

    private void setupMethodologyComparator(List<MethodologyComparerTypeEnum> ops){
        this.cbOperation.setItems(FXCollections.observableArrayList(ops));
        this.cbOperation.setConverter(new StringConverter<MethodologyComparerTypeEnum>() {
            @Override
            public String toString(MethodologyComparerTypeEnum object) {
                return object.getDescription();
            }
            @Override
            public MethodologyComparerTypeEnum fromString(String string) {
                return MethodologyComparerTypeEnum.fromString(string);
            }
        });
    }

    private void addMethodology(){
        try{
            validateField();
            Methodology met = MethodologyFactory.metodology(getUser(), cbMethodologyType.getValue(),txtName.getText(),cbCompanies.getValue(),MethodologyComparerTypeEnum.getValue(cbOperation.getValue()),cbIndicator.getValue(),txtValue.getText());
            mDao.addMethodologyIfDontExist(met);
            AlertFactory.informationAlert("","Se agrego correctamente.").show();
            loadTable();
            clearFields();
        }catch (NullPointerException ex){
            AlertFactory.errorAlert("","Complete todos los campos").show();
            logger.debug(ex.getMessage());
        }catch (IncompleteFormException ex){
            AlertFactory.errorAlert("",ex.getMessage().toString()).show();
            logger.debug(ex.getMessage());
        } catch (DuplicateValueException ex){
            AlertFactory.errorAlert("","Ya existe esta metodologia").show();
            logger.debug(ex.getMessage());
        } catch (Exception ex){
            AlertFactory.errorAlert("","Ups! Hubo un error, intente mas tarde").show();
            logger.debug(ex.getMessage().toString());
        }

    }

    private void validateField() throws IncompleteFormException {
        try {
            if (txtName.getText().isEmpty()  || cbIndicator.getValue() == null || cbOperation.getValue() == null || cbMethodologyType.getValue() == null){
                throw new IncompleteFormException("Complete todos los datos");
            }
            //validateSpecialField(cbMethodologyType.getValue());
        }catch (NullPointerException ex){
            throw new IncompleteFormException("Complete todos los datos");
        }
    }

    private void validateSpecialField(MethodologyTypeEnum methodology){
        switch (methodology){
            case COMPANIES: {
                if (cbMethodologyType.getValue().equals(null)){throw new IncompleteFormException("Complete la empresa a comparar");}
                break;
            }
            case VALUE:{
                if(txtValue.getText().isEmpty() || txtValue.getText() == null){throw new IncompleteFormException("Complete el valor de la metodologia");}
                break;
            }
            default:;
        }
    }

    private void clearFields(){
        this.txtName.setText("");
        this.txtValue.setText("");
        this.cbOperation.valueProperty().setValue(null);
        this.cbIndicator.valueProperty().setValue(null);
        this.cbMethodologyType.valueProperty().setValue(null);
        this.cbCompanies.valueProperty().setValue(null);
    }

    private void delete(){

        try{
            Methodology methodology = mDao.findMethodology(txtName.getText());
            mDao.deleteMethodology(methodology);
            loadTable();
            clearFields();
        }catch (DeleteException ex){
            AlertFactory.errorAlert("","No se pudo borrar la metodologia");
            logger.debug(ex.getMessage());
        }

    }
}
