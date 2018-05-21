package ar.edu.utn.frba.dds.models.methodology;

import ar.edu.utn.frba.dds.models.Company;
import ar.edu.utn.frba.dds.models.Indicator;
import ar.edu.utn.frba.dds.models.users.User;
import ar.edu.utn.frba.dds.models.indicator.IndicatorResult;
import ar.edu.utn.frba.dds.models.methodology.comparer.MethodologyComparer;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

/**
 * Created by pablomorrone on 14/6/17.
 */
@Entity
@Table(name = "methodology_companies")
@PrimaryKeyJoinColumn(name = "methodologies_id", referencedColumnName = "id")
public class MethodologyCompanies extends Methodology {

    @ManyToOne
    @JoinColumn(name = "companies_id")
    private Company companyToCompare;

    public MethodologyCompanies(User user, String name, Company companyToCompare, Indicator indicator, MethodologyComparer comparator) {
        super(user, name, indicator, comparator);
        this.setCompanyToCompare(companyToCompare);
    }

    public MethodologyCompanies() {
    }

    @Override
    public List<MethodologyResult> run(List<Company> companies, LocalDate from, LocalDate to) {
        try {
            for (IndicatorResult i : getIndicator().eval(this.getCompanyToCompare(), from, to)) {
                getComparer().setReferenceValue(i.getValue());
            }
        }catch (Exception e) {}
        return super.run(companies, from, to);
    }

    @Override
    public boolean equals(Object object){
        if (object == null) return false;
        try {
            MethodologyCompanies m = (MethodologyCompanies) object;
            return (this.getCompanyToCompare().equals(m.getCompanyToCompare())
                    && super.equals(object));
        }
        catch (Exception e){}
        return false;
    }

    @Override
    public String getSpecialValue() {
        return companyToCompare.getName();
    }

    @Override
    public String getDescription() {
        return "Con Empresas";
    }

    public Company getCompanyToCompare() {
        return companyToCompare;
    }

    public void setCompanyToCompare(Company companyToCompare) {
        this.companyToCompare = companyToCompare;
    }

}
