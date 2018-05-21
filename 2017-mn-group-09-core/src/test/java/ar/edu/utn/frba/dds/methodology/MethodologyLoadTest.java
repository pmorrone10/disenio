package ar.edu.utn.frba.dds.methodology;

import ar.edu.utn.frba.dds.configuration.EnviromentConfigurationHelper;
import ar.edu.utn.frba.dds.controllers.AutomaticLoader;
import ar.edu.utn.frba.dds.dao.CompaniesDao;
import ar.edu.utn.frba.dds.dao.IndicatorsDao;
import ar.edu.utn.frba.dds.dao.MethodologiesDao;
import ar.edu.utn.frba.dds.dao.UsersDao;
import ar.edu.utn.frba.dds.models.Indicator;
import ar.edu.utn.frba.dds.models.users.User;
import ar.edu.utn.frba.dds.models.methodology.MethodologyResult;
import ar.edu.utn.frba.dds.models.methodology.MethodologyValue;
import ar.edu.utn.frba.dds.models.methodology.comparer.MethodologyComparerLessThan;
import ar.edu.utn.frba.dds.models.methodology.comparer.MethodologyComparerGreaterThan;
import ar.edu.utn.frba.dds.utils.DeleteUtils;
import ar.edu.utn.frba.dds.utils.FileUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDate;
import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * Created by TATIANA on 24/7/2017.
 */
public class MethodologyLoadTest {
    private CompaniesDao companiesDao;
    private MethodologiesDao  mDao;
    private IndicatorsDao iDao;
    private UsersDao uDao;

    private User user;

    private Indicator indicator1;
    private Indicator indicator2;
    private Indicator indicator3;
    private Indicator indicator4;
    private Indicator indicator5;
    private Indicator indicator6;
    private Indicator indicator7;

    @Before
    public void setUp() throws Exception {
        EnviromentConfigurationHelper.setEnviromentTest();
        DeleteUtils.deleteAll();

        companiesDao = new CompaniesDao();
        mDao = new MethodologiesDao();
        iDao = new IndicatorsDao();
        uDao = new UsersDao();

        user = new User("test", "test");
        user = uDao.addIfDontExist(user);

        indicator1 = new Indicator(uDao.exist("test"), "I1", "3+2");
        indicator2 = new Indicator(uDao.exist("test"), "I2", "I1+2");
        indicator3 = new Indicator(uDao.exist("test"), "I3", "8+AC_EBITDA");
        indicator4 = new Indicator(uDao.exist("test"), "I4", "I2+AC_EBITDA");
        indicator5 = new Indicator(uDao.exist("test"), "I5", "AC_FDC+I2");
        indicator6 = new Indicator(uDao.exist("test"), "I6","I3+I4") ;
        indicator7 = new Indicator(uDao.exist("test"), "I7","AC_FDS/3");

        AutomaticLoader loader = new AutomaticLoader();
        loader.LoadCompaniesFromFile(FileUtils.filePath(MethodologyCompanyTest.class, "/files/empresas.csv"));
        loader.LoadCompaniesFromFile(FileUtils.filePath(MethodologyCompanyTest.class, "/files/empresas2.csv"));
        loader.LoadCompaniesFromFile(FileUtils.filePath(MethodologyCompanyTest.class, "/files/empresasMetodologias.csv"));

        iDao.addIndicatorIfDontExist(indicator1);
        iDao.addIndicatorIfDontExist(indicator2);
        iDao.addIndicatorIfDontExist(indicator3);
        iDao.addIndicatorIfDontExist(indicator4);
        iDao.addIndicatorIfDontExist(indicator5);
        iDao.addIndicatorIfDontExist(indicator6);
        iDao.addIndicatorIfDontExist(indicator7);
    }

    @Test
    public void testGreaterThan() {
        MethodologyValue m = new MethodologyValue(uDao.exist("test"), "MethodologyValue1",
                Double.parseDouble("51000"), iDao.findIndicator("I3"), new MethodologyComparerGreaterThan());
        List<MethodologyResult> results = m.run(companiesDao.getCompanies(), LocalDate.of(2017,3,1), LocalDate.of(2017,4,1));
        assertEquals(results.size(), 2);
    }

    @Test
    public void testLessThan() {
        MethodologyValue m = new MethodologyValue(uDao.exist("test"), "MethodologyValue2",
                Double.parseDouble("51000"), iDao.findIndicator("I3"), new MethodologyComparerLessThan());
        List<MethodologyResult> results = m.run(companiesDao.getCompanies(), LocalDate.of(2017,3,1), LocalDate.of(2017,4,1));
        assertEquals(results.size(), 3);
    }

    @After
    public void teardown() {
        DeleteUtils.deleteAll();
    }
}
