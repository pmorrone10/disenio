package ar.edu.utn.frba.dds.models.methodology;

import ar.edu.utn.frba.dds.models.Indicator;
import ar.edu.utn.frba.dds.models.users.User;
import ar.edu.utn.frba.dds.models.methodology.comparer.MethodologyComparer;
import javax.persistence.*;

/**
 * Created by pablomorrone on 14/6/17.
 */
@Entity
@Table(name = "methodology_values")
@PrimaryKeyJoinColumn(name = "methodologies_id", referencedColumnName = "id")
public class MethodologyValue extends Methodology {

    @Column(name = "value")
    private Double valueToCompare;

    public MethodologyValue(User user, String name, Double valueToCompare, Indicator indicator, MethodologyComparer comparator){
        super(user, name, indicator, comparator);
        this.valueToCompare = valueToCompare;
        getComparer().setReferenceValue(getValueToCompare());
    }

    public MethodologyValue() {
    }

    @Override
    public String getSpecialValue() {
        return valueToCompare.toString();
    }

    @Override
    public String getDescription(){
        return "Con valor";
    }

    public Double getValueToCompare() {
        return valueToCompare;
    }

    public void setValueToCompare(Double valueToCompare) {
        this.valueToCompare = valueToCompare;
    }

    @Override
    public MethodologyComparer getComparer() {
        MethodologyComparer comparer = super.getComparer();
        comparer.setReferenceValue(getValueToCompare());
        return comparer;
    }


    @Override
    public boolean equals(Object object){
        if (object == null) return false;
        try {
            MethodologyValue m = (MethodologyValue) object;
            return (this.getValueToCompare().equals(m.getValueToCompare())
                    && super.equals(object));
        }
        catch (Exception e){}
        return false;
    }

    @Override
    public int hashCode() {
        return getValueToCompare() != null ? getValueToCompare().hashCode() : 0;
    }
}
