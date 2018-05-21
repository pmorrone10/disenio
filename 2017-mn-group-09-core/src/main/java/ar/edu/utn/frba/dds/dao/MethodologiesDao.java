package ar.edu.utn.frba.dds.dao;

import ar.edu.utn.frba.dds.exception.DuplicateValueException;
import ar.edu.utn.frba.dds.exception.UnknownIndicatorException;
import ar.edu.utn.frba.dds.models.users.User;
import ar.edu.utn.frba.dds.models.methodology.Methodology;
import ar.edu.utn.frba.dds.models.methodology.comparer.MethodologyComparer;
import java.util.List;

/**
 * Created by pablomorrone on 4/9/17.
 */
public class MethodologiesDao extends BaseDao {

    public MethodologiesDao(){
    }

    public Methodology getMethodology(int id){
        return getById(Methodology.class,id);
    }

    public List<Methodology> getMethodologies(String username) {
        return getMethodologies(new UsersDao().exist(username));
    }
    public List<Methodology> getMethodologies(User user){
        return getListByPropertyValue(Methodology.class, "user", user);
    }
    private Methodology methodologyExist(Methodology e) { return methodologyExist(e.getName()); }
    private Methodology methodologyExist(String name) { return getByPropertyValue(Methodology.class, "name", name); }
    public void addMethodologyIfDontExist(Methodology methodology){
        Methodology m2 = methodologyExist(methodology);
        if (m2 == null){
            methodology = addComparerToMethodology(methodology);
            methodology = addIndicatorToMethodology(methodology);
            update(methodology);
        }else {
            throw new DuplicateValueException();
        }
    }
    public Methodology findMethodology(String name){
        return methodologyExist(name);
    }
    public void deleteMethodology(Methodology methodology){
        Methodology m = methodologyExist(methodology);
        if (m!= null){
            List<Methodology> mets = getListByPropertyValue(Methodology.class,"methodology_comparators_id",m.getComparer().getId());
            if (mets.size() > 1) {
                m.setComparer(null);
            }
            /*mets = getListByPropertyValue(Methodology.class,"indicators_id",m.getIndicator().getId());
            if (mets.size() > 1){
                m.setIndicator(null);
            }*/
            m.setIndicator(null);
            deleteElement(m);
        }
    }

    public List<MethodologyComparer> getComparers(){
        return list(MethodologyComparer.class);
    }

    public MethodologyComparer findComparer(String name){
        return getByPropertyValue(MethodologyComparer.class,"name",name);
    }

    private Methodology addComparerToMethodology(Methodology met){
        MethodologyComparer c =  findComparer(met.getComparer().getName());
        if (c != null){
            met.setComparer(c);
        }
        return met;
    }

    private Methodology addIndicatorToMethodology(Methodology met){
        try {
            met.setIndicator(new IndicatorsDao().findIndicator(met.getIndicator().getName()));
        }catch (UnknownIndicatorException ex){}
        return met;
    }
}
