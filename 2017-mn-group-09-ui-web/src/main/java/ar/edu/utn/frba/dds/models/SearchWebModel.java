package ar.edu.utn.frba.dds.models;

import ar.edu.utn.frba.dds.models.methodology.Methodology;
import ar.edu.utn.frba.dds.models.methodology.MethodologyResult;
import ar.edu.utn.frba.dds.models.views.CompanyTable;
import ar.edu.utn.frba.dds.models.views.IndicatorResults;

import java.util.List;

/**
 * Created by pablomorrone on 16/10/17.
 */
public class SearchWebModel {

    private SearchModel searchModel;
    private List<CompanyTable> companyTables;
    private List<Company> companies;
    private List<IndicatorResults> indicatorResults;
    private List<Methodology> methodologies;
    private List<MethodologyResult> methodologiesResult;
    private boolean showModal = false;

    public SearchWebModel(){

    }

    public List<CompanyTable> getCompanyTables() {
        return companyTables;
    }

    public void setCompanyTables(List<CompanyTable> companyTables) {
        this.companyTables = companyTables;
    }

    public List<Company> getCompanies() {
        return companies;
    }

    public void setCompanies(List<Company> companies) {
        this.companies = companies;
    }

    public SearchModel getSearchModel() {
        return searchModel;
    }

    public void setSearchModel(SearchModel searchModel) {
        this.searchModel = searchModel;
    }

    public List<IndicatorResults> getIndicatorResults() {
        return indicatorResults;
    }

    public void setIndicatorResults(List<IndicatorResults> indicatorResults) {
        this.indicatorResults = indicatorResults;
    }

    public List<Methodology> getMethodologies() {
        return methodologies;
    }

    public void setMethodologies(List<Methodology> methodologies) {
        this.methodologies = methodologies;
    }

    public List<MethodologyResult> getMethodologiesResult() {
        return methodologiesResult;
    }

    public void setMethodologiesResult(List<MethodologyResult> methodologiesResult) {
        this.methodologiesResult = methodologiesResult;
    }

    public boolean isShowModal() {
        return showModal;
    }

    public void setShowModal(boolean showModal) {
        this.showModal = showModal;
    }
}
