package ar.edu.utn.frba.dds.models.views;

import ar.edu.utn.frba.dds.models.methodology.Methodology;
import javafx.beans.property.SimpleStringProperty;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by pablomorrone on 14/6/17.
 */
public class MethodologyTable {

    private SimpleStringProperty type;
    private SimpleStringProperty name;
    private SimpleStringProperty operation;
    private SimpleStringProperty value;
    private SimpleStringProperty indicator;

    public MethodologyTable(Methodology m){
        this.name = new SimpleStringProperty(m.getName());
        this.indicator = new SimpleStringProperty(m.getIndicator().getName());
        this.operation = new SimpleStringProperty(m.getComparer().getName());
        this.value = new SimpleStringProperty(m.getSpecialValue());
        this.type = new SimpleStringProperty(m.getDescription());
    }

    public static List<MethodologyTable> some(List<Methodology> methodologies){
        List<MethodologyTable> lst = new ArrayList<>();
        for (Methodology e: methodologies) {
            try {
                lst.add(new MethodologyTable(e));
            }catch (NullPointerException ex){
                //no-ops
            }
        }
        return lst;
    }

    public String getType() {
        return type.get();
    }

    public SimpleStringProperty typeProperty() {
        return type;
    }

    public void setType(String type) {
        this.type.set(type);
    }

    public String getName() {
        return name.get();
    }

    public SimpleStringProperty nameProperty() {
        return name;
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public String getOperation() {
        return operation.get();
    }

    public SimpleStringProperty operationProperty() {
        return operation;
    }

    public void setOperation(String operation) {
        this.operation.set(operation);
    }

    public String getValue() {
        return value.get();
    }

    public SimpleStringProperty valueProperty() {
        return value;
    }

    public void setValue(String value) {
        this.value.set(value);
    }

    public String getIndicator() {
        return indicator.get();
    }

    public SimpleStringProperty indicatorProperty() {
        return indicator;
    }

    public void setIndicator(String indicator) {
        this.indicator.set(indicator);
    }
}
