package ar.edu.utn.frba.dds.models.views;

import ar.edu.utn.frba.dds.models.methodology.MethodologyResult;
import ar.edu.utn.frba.dds.utils.DateUtils;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by pablomorrone on 1/8/17.
 */
public class MethodologyResultTable {

    private SimpleStringProperty name;
    private SimpleStringProperty from;
    private SimpleStringProperty to;
    private SimpleDoubleProperty result;

    public MethodologyResultTable(MethodologyResult m){
        this.name = new SimpleStringProperty(m.getCompany().getName());
        this.from = new SimpleStringProperty(DateUtils.getStringFromDate(m.getFrom()));
        this.to = new SimpleStringProperty(DateUtils.getStringFromDate(m.getTo()));
        this.result = new SimpleDoubleProperty(m.getResult());
    }

    public static List<MethodologyResultTable> transform(List<MethodologyResult> methodologies){
        List<MethodologyResultTable> lst = new ArrayList<>();
        for (MethodologyResult m : methodologies){
            lst.add(new MethodologyResultTable(m));
        }
        return lst;
    }

    public String getName(){return name.get();}

    public StringProperty nameProperty(){ return name;}

    public String getFrom(){return from.get();}

    public StringProperty fromProperty(){ return from;}

    public String getTo(){return to.get();}

    public StringProperty toProperty(){return to;}

    public Double getResult(){return result.get();}

    public DoubleProperty resultProperty(){return result;}

}
