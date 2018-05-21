package ar.edu.utn.frba.dds.methodology;

import ar.edu.utn.frba.dds.configuration.EnviromentConfigurationHelper;
import ar.edu.utn.frba.dds.controllers.AutomaticLoader;
import ar.edu.utn.frba.dds.dao.CompaniesDao;
import ar.edu.utn.frba.dds.dao.IndicatorsDao;
import ar.edu.utn.frba.dds.dao.MethodologiesDao;
import ar.edu.utn.frba.dds.dao.UsersDao;
import ar.edu.utn.frba.dds.models.Indicator;
import ar.edu.utn.frba.dds.models.users.User;
import ar.edu.utn.frba.dds.models.methodology.MethodologyOwn;
import ar.edu.utn.frba.dds.models.methodology.MethodologyResult;
import ar.edu.utn.frba.dds.models.methodology.comparer.MethodologyComparerAscending;
import ar.edu.utn.frba.dds.models.methodology.comparer.MethodologyComparerDescending;
import ar.edu.utn.frba.dds.utils.DeleteUtils;
import ar.edu.utn.frba.dds.utils.FileUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.Assert.assertEquals;

/**
 * Created by lili on 21/08/2017.
 */
public class MethodologyOwnTest {
    private CompaniesDao companiesDao;
    private MethodologiesDao  mDao;
    private IndicatorsDao iDao;
    private UsersDao uDao;

    private MethodologyOwn metOwnAscending;
    private MethodologyOwn metOwnDescending;

    private User user;

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

        Indicator indicator1 = new Indicator(uDao.exist("test"), "I1", "AC_EBITDA+AC_IGNETO");
        Indicator indicator2 = new Indicator(uDao.exist("test"), "I2", "AC_FDS*2");
        Indicator indicator3 = new Indicator(uDao.exist("test"), "I3", "I1/2");
        Indicator indicator4 = new Indicator(uDao.exist("test"), "I4", "AC_EBITDA/2");
        Indicator indicator5 = new Indicator(uDao.exist("test"), "I5", "I1+I2");

        AutomaticLoader loader = new AutomaticLoader();
        loader.LoadCompaniesFromFile(FileUtils.filePath(MethodologyCompanyTest.class, "/files/empresas.csv"));
        loader.LoadCompaniesFromFile(FileUtils.filePath(MethodologyCompanyTest.class, "/files/empresas2.csv"));
        loader.LoadCompaniesFromFile(FileUtils.filePath(MethodologyCompanyTest.class, "/files/empresasMetodologias.csv"));

        iDao.addIndicatorIfDontExist(indicator1);
        iDao.addIndicatorIfDontExist(indicator2);
        iDao.addIndicatorIfDontExist(indicator3);
        iDao.addIndicatorIfDontExist(indicator4);
        iDao.addIndicatorIfDontExist(indicator5);

        metOwnAscending = new  MethodologyOwn(uDao.exist("test"), "MetOwnAscending",
                companiesDao.findCompany("Musimundo"), iDao.findIndicator("I2"), new MethodologyComparerAscending());

        metOwnDescending =  new MethodologyOwn(uDao.exist("test"), "MetOwnDescending",
                companiesDao.findCompany("Despegar"), iDao.findIndicator("I2"), new MethodologyComparerDescending());

        mDao.addMethodologyIfDontExist(metOwnAscending);
        mDao.addMethodologyIfDontExist(metOwnDescending);
    }

    @Test
    public void testMetOwnAscending() {
        List<MethodologyResult> results = metOwnAscending.run(companiesDao.getCompanies().stream().filter(e -> (e.getName().equals("Musimundo"))).collect(Collectors.toList()), LocalDate.of(2017,3,1), LocalDate.of(2017,7,1));
        assertEquals(results.size(), 2);
        assertEquals(Double.valueOf(24000).toString(), results.get(0).getResult().toString());

    }
    @Test
    public void testMetOwnDescending() {
        List<MethodologyResult> results = metOwnDescending.run(companiesDao.getCompanies(), LocalDate.of(2017,3,1), LocalDate.of(2017,7,1));
        assertEquals(results.size(), 3);
        assertEquals(Double.valueOf(86000).toString(), results.get(0).getResult().toString());

    }

    @Test
    public void testDescendingError() {
        MethodologyOwn m = new MethodologyOwn(uDao.exist("test"), "MethodologyOwn1",
                companiesDao.findCompany("Mercadolibre"), iDao.findIndicator("I4"), new MethodologyComparerDescending());
        List<MethodologyResult> results = m.run(companiesDao.getCompanies().stream().filter(e -> (e.getName().equals("Mercadolibre"))).collect(Collectors.toList()), LocalDate.of(2017,3,1), LocalDate.of(2017,7,1));
        assertEquals(results.size(), 0);
    }

    @Test
    public void testAscendingError() {
        MethodologyOwn m = new MethodologyOwn(uDao.exist("test"), "MethodologyOwn2",
                companiesDao.findCompany("Despegar"), iDao.findIndicator("I4"), new MethodologyComparerAscending());
        List<MethodologyResult> results = m.run(companiesDao.getCompanies().stream().filter(e -> (e.getName().equals("Despegar"))).collect(Collectors.toList()), LocalDate.of(2017,3,1), LocalDate.of(2017,7,1));
        assertEquals(results.size(), 0);
    }

    @After
    public void teardown() {
        DeleteUtils.deleteAll();
    }
}
