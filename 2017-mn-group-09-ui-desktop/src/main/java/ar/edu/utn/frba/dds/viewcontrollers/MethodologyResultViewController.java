package ar.edu.utn.frba.dds.viewcontrollers;

/**
 * Created by pablomorrone on 12/6/17.
 */
import ar.edu.utn.frba.dds.Session;
import ar.edu.utn.frba.dds.models.views.MethodologyResultTable;
import ar.edu.utn.frba.dds.dao.MethodologiesDao;
import ar.edu.utn.frba.dds.models.SearchModel;
import ar.edu.utn.frba.dds.models.users.User;
import ar.edu.utn.frba.dds.models.methodology.Methodology;
import ar.edu.utn.frba.dds.models.methodology.MethodologyResult;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.StringConverter;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class MethodologyResultViewController implements Initializable {

    @FXML
    private ComboBox<Methodology> cbMethodology;

    @FXML
    private Button searchButton;

    @FXML
    private TableView<MethodologyResultTable> tableview;

    @FXML
    private TableColumn<MethodologyResultTable, String> companyColumn;

    @FXML
    private TableColumn<MethodologyResultTable, String> fromColumn;

    @FXML
    private TableColumn<MethodologyResultTable, String> toColumn;

    @FXML
    private TableColumn<MethodologyResultTable, Double> valueColumn;

    private ObservableList<MethodologyResultTable> data;

    private SearchModel searchModel;

    private MethodologiesDao mDao;

    private User user;

    public User getUser() {
        return user;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.user = Session.getSession().getUser();
        this.mDao = new MethodologiesDao();
        setupMethodology();
        setupTable();
        searchButton.setOnAction(event -> apply());
    }

    public void initData(SearchModel searchModel){
        this.searchModel = searchModel;
    }

    private void setupMethodology(){
        this.cbMethodology.setItems(FXCollections.observableArrayList(mDao.getMethodologies(getUser())));
        this.cbMethodology.setConverter(new StringConverter<Methodology>() {
            @Override
            public String toString(Methodology object) {
                return object.getName();
            }

            @Override
            public Methodology fromString(String string) {
                return null;
            }});
    }

    private void apply(){
        Methodology methodology = this.cbMethodology.getValue();
        List<MethodologyResult> results = methodology.run(searchModel.getCompanies(), searchModel.getFrom(), searchModel.getTo());
        data = FXCollections.observableArrayList(MethodologyResultTable.transform(results));
        reloadTable();
    }

    public void setupTable(){
        companyColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        fromColumn.setCellValueFactory(new PropertyValueFactory<>("from"));
        toColumn.setCellValueFactory(new PropertyValueFactory<>("to"));
        valueColumn.setCellValueFactory(new PropertyValueFactory<>("result"));
    }

    public void reloadTable(){
        this.tableview.setItems(this.data);
    }
}

