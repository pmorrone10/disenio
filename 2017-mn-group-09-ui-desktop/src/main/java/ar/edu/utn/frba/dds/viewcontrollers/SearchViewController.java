package ar.edu.utn.frba.dds.viewcontrollers;

import ar.edu.utn.frba.dds.Session;
import ar.edu.utn.frba.dds.dao.CompaniesDao;
import ar.edu.utn.frba.dds.exception.InvalidRangeDateException;
import ar.edu.utn.frba.dds.models.Company;
import ar.edu.utn.frba.dds.models.SearchModel;
import ar.edu.utn.frba.dds.models.views.CompanyTable;
import ar.edu.utn.frba.dds.models.users.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.StringConverter;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by pablomorrone on 13/4/17.
 */
public class SearchViewController implements Initializable {

    @FXML
    private AnchorPane anchorpane;

    @FXML
    private ComboBox<Company> combobox;

    @FXML
    private DatePicker from;

    @FXML
    private DatePicker to;

    @FXML
    private Button searchButton;

    @FXML
    private Button identifiersButton;

    @FXML
    private Button methodologiesButton;

    @FXML
    private TableView<CompanyTable> tableview;

    @FXML
    private TableColumn<CompanyTable, String> companyColumn;

    @FXML
    private TableColumn<CompanyTable, String> creationCompanyColumn;

    @FXML
    private TableColumn<CompanyTable, String> accountColumn;

    @FXML
    private TableColumn<CompanyTable, String> valueColumns;

    @FXML
    private TableColumn<CompanyTable, String> timesatmpColumn;

    private ObservableList<CompanyTable> data;

    private CompaniesDao companiesDao;

    private User user;

    public User getUser() {
        return user;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.user = Session.getSession().getUser();
        this.companiesDao = new CompaniesDao();
        data = FXCollections.observableArrayList(CompanyTable.transform(companiesDao.getCompanies()));
        setupCombobox();
        setupTableview();
        loadTable();
        searchButton.setOnAction(event -> search(getUser()));
        identifiersButton.setOnAction(event -> calcIdentifiers());
        methodologiesButton.setOnAction(event -> showMethodology());
    }

    private void setupCombobox(){
        this.combobox.setItems(FXCollections.observableArrayList(companiesDao.getCompanies()));
        this.combobox.setConverter(new StringConverter<Company>() {
            @Override
            public String toString(Company object) {
                return object.getName();
            }

            @Override
            public Company fromString(String string) {
                return null;
            }});
    }

    private void setupTableview(){
        companyColumn.setCellValueFactory(new PropertyValueFactory<>("company"));
        creationCompanyColumn.setCellValueFactory(new PropertyValueFactory<>("creationDate"));
        accountColumn.setCellValueFactory(new PropertyValueFactory<>("account"));
        valueColumns.setCellValueFactory(new PropertyValueFactory<>("value"));
        timesatmpColumn.setCellValueFactory(new PropertyValueFactory<>("timestamp"));
    }

    private void loadTable(){
        this.tableview.setItems(data);
    }

    private void search(User user) {
        try {
            data = FXCollections.observableArrayList(companiesDao.searchCompanies(getSearchModel(user)));
            loadTable();
        }catch (InvalidRangeDateException ex){
            AlertFactory.errorAlert("",ex.getMessage()).show();
        }
    }

    private void calcIdentifiers(){
        try{
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/views/indicatorsResult.fxml"));
            Parent root1 = (Parent) fxmlLoader.load();
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.initStyle(StageStyle.DECORATED);
            stage.setTitle(String.format("Resultados de identificadores"));
            stage.setScene(new Scene(root1));
            IndicatorsResultViewController controller =
                    fxmlLoader.<IndicatorsResultViewController>getController();
            controller.initData(getSearchModel(getUser()));
            stage.show();

        }catch (IOException e){
            failLoadScreen(e.getMessage());
        }
    }

    private void showMethodology(){
        try{
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/views/methodologyResult.fxml"));
            Parent root1 = (Parent) fxmlLoader.load();
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.initStyle(StageStyle.DECORATED);
            stage.setTitle(String.format("Metodologias"));
            stage.setScene(new Scene(root1));
            MethodologyResultViewController m = fxmlLoader.getController();
            m.initData(getSearchModel(getUser()));
            stage.show();
        } catch (IOException e) {
            failLoadScreen(e.getMessage());
        }
    }

    private SearchModel getSearchModel(User user){
        return new SearchModel(user, this.combobox.getValue(),this.from.getValue(),this.to.getValue());
    }

    private void failLoadScreen(String message){
        final Logger logger = Logger.getLogger(SearchViewController.class);
        logger.debug("No se puede instanciar. Error: " + message);
        AlertFactory.errorAlert("Error","No se puede cargar la pantalla. Intente mas tarde.").show();
    }
}
