package ar.edu.utn.frba.dds.utils;

import ar.edu.utn.frba.dds.dao.*;
import ar.edu.utn.frba.dds.models.Company;
import ar.edu.utn.frba.dds.models.Indicator;
import ar.edu.utn.frba.dds.models.methodology.Methodology;
/**
 * Created by pablomorrone on 11/9/17.
 */
public class DeleteUtils {

    public static void deleteAll() {
        MethodologiesDao methodologiesDao = new MethodologiesDao();

        for (Methodology m: methodologiesDao.list(Methodology.class)) {
            methodologiesDao.deleteMethodology(m);
        }

        CompaniesDao companiesDao = new CompaniesDao();

        for (Company c: companiesDao.getCompanies()) {
            companiesDao.deleteElement(c);
        }

        IndicatorsDao indicatorsDao = new IndicatorsDao();

        for (Indicator i: indicatorsDao.list(Indicator.class)) {
            indicatorsDao.deleteIndicator(i);
        }
    }
}
