package ar.edu.utn.frba.dds.models.methodology;

import ar.edu.utn.frba.dds.dao.CompaniesDao;
import ar.edu.utn.frba.dds.dao.IndicatorsDao;
import ar.edu.utn.frba.dds.exception.IncompleteFormException;
import ar.edu.utn.frba.dds.models.Company;
import ar.edu.utn.frba.dds.models.Indicator;
import ar.edu.utn.frba.dds.models.methodology.comparer.MethodologyComparerTypeEnum;
import ar.edu.utn.frba.dds.models.users.User;
import ar.edu.utn.frba.dds.models.methodology.comparer.MethodologyComparer;

/**
 * Created by pablomorrone on 31/7/17.
 */
public class MethodologyFactory {

    public static Methodology metodology(User user, MethodologyTypeEnum methodologyType, String name, Company company, MethodologyComparer comparer, Indicator indicator, String value){
        try {
            switch (methodologyType){
                case COMPANIES: return new MethodologyCompanies(user, name,company,indicator,comparer);
                case OWN: return new MethodologyOwn(user, name,company,indicator,comparer);
                case VALUE: return new MethodologyValue(user, name,Double.parseDouble(value),indicator,comparer);
                default:{
                    return null;
                }
            }
        }catch (Exception ex){
            throw new IncompleteFormException("Complete todos los campos");
        }
    }

    public static Methodology methodology(User user, String methodologyId, String name, String companyId,String comparerId, String indicatorId, String value){

        Company company = null;
        MethodologyTypeEnum methodology = MethodologyTypeEnum.fromInt(Integer.parseInt(methodologyId));
        if (companyId != null && !companyId.isEmpty()){
            company = new CompaniesDao().getCompany(Integer.parseInt(companyId));
        }
        MethodologyComparerTypeEnum comparer = MethodologyComparerTypeEnum.fromInt(Integer.parseInt(comparerId));
        Indicator indicator = new IndicatorsDao().getIndicator(Integer.parseInt(indicatorId));
        return metodology(user,methodology,name,company,MethodologyComparerTypeEnum.getValue(comparer),indicator,value);
    }



}
