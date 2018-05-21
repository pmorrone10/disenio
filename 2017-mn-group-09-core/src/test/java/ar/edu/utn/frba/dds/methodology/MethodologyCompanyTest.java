package ar.edu.utn.frba.dds.methodology;

import ar.edu.utn.frba.dds.configuration.EnviromentConfigurationHelper;
import ar.edu.utn.frba.dds.controllers.AutomaticLoader;
import ar.edu.utn.frba.dds.dao.CompaniesDao;
import ar.edu.utn.frba.dds.dao.IndicatorsDao;
import ar.edu.utn.frba.dds.dao.MethodologiesDao;
import ar.edu.utn.frba.dds.dao.UsersDao;
import ar.edu.utn.frba.dds.models.Indicator;
import ar.edu.utn.frba.dds.models.users.User;
import ar.edu.utn.frba.dds.models.methodology.Methodology;
import ar.edu.utn.frba.dds.models.methodology.MethodologyCompanies;
import ar.edu.utn.frba.dds.models.methodology.MethodologyResult;
import ar.edu.utn.frba.dds.models.methodology.comparer.*;
import ar.edu.utn.frba.dds.utils.DeleteUtils;
import ar.edu.utn.frba.dds.utils.FileUtils;
import org.junit.*;

import java.time.LocalDate;
import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * Created by lili on 15/08/2017.
 */
public class MethodologyCompanyTest {
    private CompaniesDao companiesDao;
    private MethodologiesDao  mDao;
    private IndicatorsDao iDao;
    private UsersDao uDao;

    private MethodologyCompanies metCompanyGreaterThan;
    private MethodologyCompanies metCompanyGreaterNull;
    private MethodologyCompanies metCompanyLessThan;
    private MethodologyCompanies metCompanyLessNull;

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

        metCompanyGreaterThan = new MethodologyCompanies(uDao.exist("test"), "MetCompanyGreaterThan", companiesDao.findCompany("Musimundo"), iDao.findIndicator("I4"), new MethodologyComparerGreaterThan());
        metCompanyGreaterNull = new MethodologyCompanies(uDao.exist("test"), "MetCompanyGreaterNull", companiesDao.findCompany("Mercadolibre"), iDao.findIndicator("I1"), new MethodologyComparerGreaterThan());
        metCompanyLessThan = new MethodologyCompanies(uDao.exist("test"), "MetCompanyLessThan", companiesDao.findCompany("Garbarino"), iDao.findIndicator("I4"), new MethodologyComparerLessThan());
        metCompanyLessNull = new MethodologyCompanies(uDao.exist("test"), "MetCompanyLessNull", companiesDao.findCompany("Despegar"), iDao.findIndicator("I5"), new MethodologyComparerLessThan());
    }

    @Test
    public void testMetCompanyGreaterThan(){
        mDao.addMethodologyIfDontExist(metCompanyGreaterThan);
        Methodology m = mDao.findMethodology("MetCompanyGreaterThan");
        List<MethodologyResult> results = m.run(companiesDao.getCompanies(), LocalDate.of(2017, 3, 1), LocalDate.of(2017, 4, 1));
        assertEquals(4, results.size());
        assertEquals(Double.valueOf(61500).toString(), results.get(0).getResult().toString());
        assertEquals("Mercadolibre", results.get(0).getCompany().getName());
    }

    @Test
    public void testMetCompanyGreaterNull(){
        mDao.addMethodologyIfDontExist(metCompanyGreaterNull);
        Methodology m = mDao.findMethodology("MetCompanyGreaterNull");
        List<MethodologyResult> results = m.run(companiesDao.getCompanies(), LocalDate.of(2017, 3, 1), LocalDate.of(2017, 4, 1));
        assertEquals(0, results.size());
    }

    @Test
    public void testMetCompanyLessThan(){
        mDao.addMethodologyIfDontExist(metCompanyLessThan);
        Methodology m = mDao.findMethodology("MetCompanyLessThan");
        List<MethodologyResult> results = m.run(companiesDao.getCompanies(), LocalDate.of(2017, 3, 1), LocalDate.of(2017, 4, 1));

        assertEquals(3, results.size());
        assertEquals(Double.valueOf(17500).toString(), results.get(0).getResult().toString());
        assertEquals("Musimundo", results.get(0).getCompany().getName());

    }

    @Test
    public void testMetCompanyLessNull(){
        mDao.addMethodologyIfDontExist(metCompanyLessNull);
        Methodology m = mDao.findMethodology("MetCompanyLessNull");
        List<MethodologyResult> results = m.run(companiesDao.getCompanies(), LocalDate.of(2017, 3, 1), LocalDate.of(2017, 4, 1));
        assertEquals(0, results.size());
    }

    @After
    public void teardown() {
        DeleteUtils.deleteAll();
    }
}



