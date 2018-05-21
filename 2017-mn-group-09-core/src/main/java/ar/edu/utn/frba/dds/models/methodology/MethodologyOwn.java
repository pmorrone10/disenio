package ar.edu.utn.frba.dds.models.methodology;

import ar.edu.utn.frba.dds.models.Company;
import ar.edu.utn.frba.dds.models.Indicator;
import ar.edu.utn.frba.dds.models.users.User;
import ar.edu.utn.frba.dds.models.methodology.comparer.MethodologyComparer;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by pablomorrone on 14/6/17.
 */
@Entity
@Table(name = "methodology_own")
public class MethodologyOwn extends MethodologyCompanies {

    public MethodologyOwn(User user, String name, Company me, Indicator indicator, MethodologyComparer comparator) {
        super(user, name, me, indicator, comparator);
    }

    public MethodologyOwn() {
    }

    @Override
    public List<MethodologyResult> run(List<Company> companies, LocalDate from, LocalDate to) {
        List<MethodologyResult> ret = new ArrayList<>();

        for (Company c: companies) {
            try {
                setCompanyToCompare(c);
                List<Company> cs = new ArrayList<>();
                cs.add(c);
                List<MethodologyResult> results = super.run(cs, from, to);
                ret.addAll(results);
            }catch (Exception e) {}
        }

        return ret;
    }

    @Override
    public boolean equals(Object object){
        if (object == null) return false;
        try {
            MethodologyCompanies m = (MethodologyCompanies) object;
            return (this.getName().equals(m.getName())
                    && this.getIndicator().equals(m.getIndicator())
                    && this.getComparer().equals(m.getComparer()));
        }
        catch (Exception e){}
        return false;
    }

    @Override
    public String getSpecialValue(){
        return "";
    }

    @Override
    public String getDescription() {
        return "Misma Empresa";
    }


}
