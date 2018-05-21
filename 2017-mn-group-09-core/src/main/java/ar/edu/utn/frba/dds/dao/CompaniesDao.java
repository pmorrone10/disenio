package ar.edu.utn.frba.dds.dao;

import ar.edu.utn.frba.dds.models.AccountName;
import ar.edu.utn.frba.dds.models.Company;
import ar.edu.utn.frba.dds.models.SearchModel;
import ar.edu.utn.frba.dds.models.views.CompanyTable;
import ar.edu.utn.frba.dds.utils.DateUtils;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by pablomorrone on 28/8/17.
 */
/**
 * Esta clase es de ejemplo estos no son los metodos definitivos.
 * **/
public class CompaniesDao extends BaseDao {

    public CompaniesDao(){}
    private Company companyExist(Company e) { return companyExist(e.getName()); }
    private Company companyExist(String name) { return getByPropertyValue(Company.class, "name", name); }
    public AccountName accountNameExist(String name) { return getByPropertyValue(AccountName.class, "name", name); }
    public AccountName addAccountNameIfDontExist(String name){
        AccountName ret = accountNameExist(name);

        if (ret == null){
            ret = new AccountName();
            ret.setName(name);
            save(ret);

        }

        return ret;
    }
    public void addCompanyIfDontExist(Company company){
        Company e2 = companyExist(company);
        if (e2 != null){
            e2.addAccount(company.getFirtAccount());
            update(e2);
        }else{
            if (company.getAccounts().size() > 0){
                company.getFirtAccount().setName(company.getFirtAccount().getName());
            }
            save(company);
        }
    }
    public void addCompaniesIfDontExist(List<Company> companies){
        for (Company e: companies) {
            this.addCompanyIfDontExist(e);
        }
    }
    public Company findCompany(String name){
        return companyExist(name);
    }
    public List<CompanyTable> searchCompanies(SearchModel searchModel){
        searchModel.validateDates();
        return CompanyTable.transform(searchModel.getCompanies()).stream().filter(companyTable -> DateUtils.isBetween(searchModel.getFrom(), searchModel.getTo(),companyTable.getTimestamp())).collect(Collectors.toList());
    }
    public List<Company> getCompanies() { return list(Company.class); }
    public Company getCompany(int id){
        return getById(Company.class,id);
    }
}
