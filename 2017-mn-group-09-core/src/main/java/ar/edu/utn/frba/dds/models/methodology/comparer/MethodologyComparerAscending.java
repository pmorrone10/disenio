package ar.edu.utn.frba.dds.models.methodology.comparer;

import ar.edu.utn.frba.dds.models.methodology.MethodologyResult;
import javax.persistence.Entity;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by TATIANA on 12/6/2017.
 */
@Entity
public class MethodologyComparerAscending extends MethodologyComparer {

    public MethodologyComparerAscending(Double referenceValue) {
        super(referenceValue);
        setName();
    }

    @Override
    public int sorter(MethodologyResult result1, MethodologyResult result2) {
        return result1.getFrom().compareTo(result2.getFrom());
    }

    @Override
    public boolean filter(MethodologyResult t) {
        return true;
    }

    @Override
    public List<MethodologyResult> getResult(List<MethodologyResult> results) {
        results.sort(MethodologyResultComparator);
        Double val = Double.MIN_VALUE;
        Boolean alwaysAscending = true;

        for (MethodologyResult r : results){
            if (val <= r.getResult()) val = r.getResult();
            else alwaysAscending = false;
        }

        if (alwaysAscending) return results;

        return new ArrayList<>();
    }

    public MethodologyComparerAscending() {
        super();
        setName();
    }

    @Override
    public boolean equals(Object obj){
        if (obj == null) return false;
        return (this.getClass().equals(obj.getClass()) && super.equals(obj));
    }

    @Override
    public void setName(){
        setName(MethodologyComparerTypeEnum.ASCENDING.getDescription());
    }

}
