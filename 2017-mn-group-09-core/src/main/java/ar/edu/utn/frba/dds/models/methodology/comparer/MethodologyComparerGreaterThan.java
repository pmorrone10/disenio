package ar.edu.utn.frba.dds.models.methodology.comparer;

import ar.edu.utn.frba.dds.models.methodology.MethodologyResult;

import javax.persistence.Entity;

/**
 * Created by TATIANA on 12/6/2017.
 */
@Entity
public class MethodologyComparerGreaterThan extends MethodologyComparer {

    public MethodologyComparerGreaterThan(Double referenceValue) {
        super(referenceValue);
        setName();
    }

    public MethodologyComparerGreaterThan() {
        super();
        setName();
    }

    @Override
    public int sorter(MethodologyResult result1, MethodologyResult result2) {
        return result2.getResult().compareTo(result1.getResult());
    }

    @Override
    public boolean filter(MethodologyResult t) {
        return t.getResult() > getReferenceValue();
    }

    @Override
    public boolean equals(Object obj){
        if (obj == null) return false;
        return (this.getClass().equals(obj.getClass()) && super.equals(obj));
    }

    @Override
    public void setName(){
        setName(MethodologyComparerTypeEnum.GREAT_THAN.getDescription());
    }
}
