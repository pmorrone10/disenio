package ar.edu.utn.frba.dds.dao;

import ar.edu.utn.frba.dds.exception.DuplicateValueException;
import ar.edu.utn.frba.dds.exception.UnknownIndicatorException;
import ar.edu.utn.frba.dds.models.Company;
import ar.edu.utn.frba.dds.models.Indicator;
import ar.edu.utn.frba.dds.models.SearchModel;
import ar.edu.utn.frba.dds.models.users.User;
import ar.edu.utn.frba.dds.models.indicator.IndicatorResult;
import ar.edu.utn.frba.dds.models.views.IndicatorResults;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by pablomorrone on 20/9/17.
 */
public class IndicatorsDao extends BaseDao {

    public IndicatorsDao(){
    }
    public Indicator getIndicator(int id) {
        return getById(Indicator.class,id);
    }
    public List<Indicator> getIndicators(User user) {
        return getListByPropertyValue(Indicator.class, "user", user);
    }

    private Indicator indicatorExist(Indicator e) { return indicatorExist(e.getName()); }
    private Indicator indicatorExist(String name) { return getByPropertyValue(Indicator.class, "name", name); }
    public void addIndicator(Indicator indicator){
        save(indicator);
    }
    public Indicator findIndicator(String name){
        Indicator ret = indicatorExist(name);
        if (ret == null) throw new UnknownIndicatorException();
        return ret;
    }
    public List<IndicatorResults> searchIndicator(SearchModel searchModel){
        List<IndicatorResults> lst = new ArrayList<>();
        for (Company c: searchModel.getCompanies()) {
            for (Indicator i: this.getIndicators(searchModel.getUser())) {
                for (IndicatorResult r : i.eval(c, searchModel.getFrom(), searchModel.getTo()))
                    lst.add(new IndicatorResults(i.getName(), c.getName(), r.getValue().toString()));
            }
        }
        return lst;
    }

    /*public void deleteIndicator(Indicator indicator){
        deleteIndicator(indicator.getName());
    }*/

    public void deleteIndicator(Indicator indicator){
        try{
            deleteElement(indicator);
        }catch (DuplicateValueException ex){
            throw new DuplicateValueException("No se puede borrar el indicador ya que esta en uso en alguna metodologia");
        }
        catch (Exception ex){
            throw new UnknownIndicatorException("No se pudo borrar");
        }
    }

    public void deleteIndicator(String name) {
        Indicator ret = indicatorExist(name);
        if (ret != null) {
            deleteElement(ret);
        }else {
            throw new UnknownIndicatorException("No existe el indicador");
        }
    }

    public void addIndicatorIfDontExist(Indicator indicator){
        Indicator i1 = indicatorExist(indicator);
        if (i1 == null) {
            update(indicator);
        }
        else{
            throw new DuplicateValueException();
        }
    }
}
