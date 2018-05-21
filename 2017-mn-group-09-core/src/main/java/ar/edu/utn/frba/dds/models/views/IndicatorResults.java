package ar.edu.utn.frba.dds.models.views;

import ar.edu.utn.frba.dds.dao.IndicatorsDao;
import ar.edu.utn.frba.dds.models.Company;
import ar.edu.utn.frba.dds.models.Indicator;
import ar.edu.utn.frba.dds.models.SearchModel;
import ar.edu.utn.frba.dds.models.indicator.IndicatorResult;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by pablomorrone on 11/4/17.
 */
public class IndicatorResults {

    private SimpleStringProperty name;
    private SimpleStringProperty company;
    private SimpleStringProperty result;

    public IndicatorResults(String name, String company, String result){
        this.name = new SimpleStringProperty(name);
        this.company = new SimpleStringProperty(company);
        this.result = new SimpleStringProperty(result);
    }

    public static List<IndicatorResults> transform(SearchModel searchModel){
        IndicatorsDao dao = new IndicatorsDao();
        List<IndicatorResults> lst = new ArrayList<IndicatorResults>();
        for (Company c: searchModel.getCompanies()) {
            for (Indicator i: dao.getIndicators(searchModel.getUser())) {
                for (IndicatorResult r: i.eval(c, searchModel.getFrom(), searchModel.getTo()))
                lst.add(new IndicatorResults(i.getName(), c.getName(), r.getValue().toString()));
            }
        }
        return lst;
    }

    public String getName() {
        return name.get();
    }

    public StringProperty nameProperty(){
        return name;
    }

    public String getCompany() {
        return company.get();
    }

    public StringProperty companyProperty(){
        return company;
    }

    public String getResult() {
        return result.get();
    }

    public StringProperty resultProperty(){
        return result;
    }
}
