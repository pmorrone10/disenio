package ar.edu.utn.frba.dds.viewcontrollers;

import ar.edu.utn.frba.dds.models.views.IndicatorResults;
import ar.edu.utn.frba.dds.dao.IndicatorsDao;
import ar.edu.utn.frba.dds.models.SearchModel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by TATIANA on 14/5/2017.
 */
public class IndicatorsResultViewController implements Initializable {

    @FXML
    private TableView<IndicatorResults> tableview;

    @FXML
    private TableColumn<IndicatorResults, String> companyColumn;

    @FXML
    private TableColumn<IndicatorResults, String> indicatorColumn;

    @FXML
    private TableColumn<IndicatorResults, String> resultColumns;

    private ObservableList<IndicatorResults> data;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }

    public void initData(SearchModel searchModel) {
        data = FXCollections.observableArrayList(new IndicatorsDao().searchIndicator(searchModel));
        setupTableview();
        loadTable();
    }

    private void setupTableview(){
        companyColumn.setCellValueFactory(new PropertyValueFactory<>("company"));
        indicatorColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        resultColumns.setCellValueFactory(new PropertyValueFactory<>("result"));
    }

    private void loadTable(){
        this.tableview.setItems(data);
    }
}
