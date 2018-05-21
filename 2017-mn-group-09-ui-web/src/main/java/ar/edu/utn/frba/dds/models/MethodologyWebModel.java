package ar.edu.utn.frba.dds.models;

import ar.edu.utn.frba.dds.models.methodology.Methodology;
import ar.edu.utn.frba.dds.models.methodology.MethodologyTypeEnum;
import ar.edu.utn.frba.dds.models.methodology.comparer.MethodologyComparerTypeEnum;
import ar.edu.utn.frba.dds.models.views.MethodologyTable;

import java.util.List;

/**
 * Created by pablomorrone on 13/10/17.
 */
public class MethodologyWebModel {

    private List<MethodologyTypeEnum> methodologyTypes;
    private List<Company> companies;
    private List<Indicator> indicators;
    private List<MethodologyComparerTypeEnum> comparerTypeEnums;
    private List<Methodology> methodologies;
    private List<MethodologyTable> mtables;
    private AlertModel alert;

    public MethodologyWebModel(){
        this.alert = new AlertModel(false,"",false);
    }

    public List<MethodologyTypeEnum> getMethodologyTypes() {
        return methodologyTypes;
    }

    public void setMethodologyTypes(List<MethodologyTypeEnum> methodologyTypes) {
        this.methodologyTypes = methodologyTypes;
    }

    public List<Company> getCompanies() {
        return companies;
    }

    public void setCompanies(List<Company> companies) {
        this.companies = companies;
    }

    public List<Indicator> getIndicators() {
        return indicators;
    }

    public void setIndicators(List<Indicator> indicators) {
        this.indicators = indicators;
    }

    public List<MethodologyComparerTypeEnum> getComparerTypeEnums() {
        return comparerTypeEnums;
    }

    public void setComparerTypeEnums(List<MethodologyComparerTypeEnum> comparerTypeEnums) {
        this.comparerTypeEnums = comparerTypeEnums;
    }

    public List<Methodology> getMethodologies() {
        return methodologies;
    }

    public void setMethodologies(List<Methodology> methodologies) {
        this.methodologies = methodologies;
    }

    public List<MethodologyTable> getMtables() {
        return mtables;
    }

    public void setMtables(List<MethodologyTable> mtables) {
        this.mtables = mtables;
    }

    public AlertModel getAlert() {
        return alert;
    }

    public void setAlert(AlertModel alert) {
        this.alert = alert;
    }
}
