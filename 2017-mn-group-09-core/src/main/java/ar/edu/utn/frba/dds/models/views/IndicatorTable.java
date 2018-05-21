package ar.edu.utn.frba.dds.models.views;

import ar.edu.utn.frba.dds.models.Indicator;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by pablomorrone on 11/4/17.
 */
public class IndicatorTable {

    private SimpleStringProperty name;
    private SimpleStringProperty expression;

    public IndicatorTable(String name, String expression){
        this.name = new SimpleStringProperty(name);
        this.expression = new SimpleStringProperty(expression);
    }

    public static List<IndicatorTable> transform(List<Indicator> indicators){
        List<IndicatorTable> lst = new ArrayList<>();
        for (Indicator e: indicators) {
            lst.add(new IndicatorTable(e.getName(), e.getExpression()));
        }
        return lst;
    }

    public String getName() {
        return name.get();
    }

    public StringProperty nameProperty(){
        return name;
    }

    public String getExpression() {
        return expression.get();
    }

    public StringProperty expressionProperty(){
        return expression;
    }
}
