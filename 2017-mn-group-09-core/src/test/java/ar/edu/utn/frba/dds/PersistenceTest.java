package ar.edu.utn.frba.dds;

import ar.edu.utn.frba.dds.configuration.EnviromentConfigurationHelper;
import ar.edu.utn.frba.dds.dao.CompaniesDao;
import ar.edu.utn.frba.dds.dao.IndicatorsDao;
import ar.edu.utn.frba.dds.dao.MethodologiesDao;
import ar.edu.utn.frba.dds.dao.UsersDao;
import ar.edu.utn.frba.dds.exception.UnknownIndicatorException;
import ar.edu.utn.frba.dds.models.Account;
import ar.edu.utn.frba.dds.models.Company;
import ar.edu.utn.frba.dds.models.Indicator;
import ar.edu.utn.frba.dds.models.users.User;
import ar.edu.utn.frba.dds.models.methodology.MethodologyCompanies;
import ar.edu.utn.frba.dds.models.methodology.MethodologyValue;
import ar.edu.utn.frba.dds.models.methodology.comparer.MethodologyComparerLessThan;
import ar.edu.utn.frba.dds.models.methodology.comparer.MethodologyComparerGreaterThan;
import ar.edu.utn.frba.dds.utils.DeleteUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDate;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

/**
 * Created by pablomorrone on 12/6/17.
 */
public class PersistenceTest {

    private Indicator indicator;
    private Company company;
    private MethodologyCompanies methodologyCompany;
    private MethodologyValue methodologyValue;
    private CompaniesDao companiesDao;
    private IndicatorsDao iDao;
    private MethodologiesDao mDao;
    private User user;

    @Before
    public void setUp() {
        EnviromentConfigurationHelper.setEnviromentTest();
        DeleteUtils.deleteAll();
        UsersDao userDao = new UsersDao();

        user = new User("test", "test");
        user = userDao.addIfDontExist(user);

        companiesDao = new CompaniesDao();
        iDao = new IndicatorsDao();
        mDao = new MethodologiesDao();
        company = new Company("Company1", LocalDate.now());
        Account a = new Account("Prueba",1500.0,LocalDate.of(2017,01,01));
        company.addAccount(a);
        companiesDao.addCompanyIfDontExist(company);
        indicator = new Indicator(user, "Indicator1", "3 + 4");
        methodologyCompany = new MethodologyCompanies(user, "Methodology1", company, indicator, new MethodologyComparerLessThan());
        methodologyValue = new MethodologyValue(user, "Methodology2", Double.valueOf("5"), indicator, new MethodologyComparerGreaterThan());
    }

    @Test(expected = UnknownIndicatorException.class)
    public void testIndicator(){
        iDao.addIndicator(indicator);
        assertNotEquals(iDao.findIndicator(indicator.getName()), null);
        assertEquals(iDao.findIndicator(indicator.getName()), indicator);
        iDao.deleteIndicator(indicator.getName());
        iDao.findIndicator(indicator.getName());
    }

    @Test
    public void testMethodology(){
        mDao.addMethodologyIfDontExist(methodologyCompany);
        mDao.addMethodologyIfDontExist(methodologyValue);
        assertEquals(mDao.findMethodology(methodologyCompany.getName()), methodologyCompany);
        assertEquals(mDao.findMethodology(methodologyValue.getName()), methodologyValue);
    }

    @After
    public void teardown() {
        DeleteUtils.deleteAll();
    }
}
