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
import ar.edu.utn.frba.dds.models.methodology.comparer.*;
import ar.edu.utn.frba.dds.utils.DeleteUtils;
import ar.edu.utn.frba.dds.utils.FileUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDate;
import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * Created by lili on 14/08/2017.
 */
public class MethodologyValueTest {
    private CompaniesDao companiesDao;
    private MethodologiesDao  mDao;
    private IndicatorsDao iDao;
    private UsersDao uDao;

    private MethodologyValue metValueGreaterThan;
    private MethodologyValue metValueGreaterNull;
    private MethodologyValue metValueLessThan;
    private MethodologyValue metValueLessNull;

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

        metValueGreaterThan = new MethodologyValue(uDao.exist("test"), "MetValueGreaterThan",Double.valueOf("25000"),iDao.findIndicator("I2"),new MethodologyComparerGreaterThan());
        metValueGreaterNull = new MethodologyValue(uDao.exist("test"), "MetValueGreaterNull",Double.valueOf("187000"),iDao.findIndicator("I3"),new MethodologyComparerGreaterThan());
        metValueLessThan = new MethodologyValue(uDao.exist("test"), "MetValueLessThan",Double.valueOf("50000"),iDao.findIndicator("I4"),new MethodologyComparerLessThan());
        metValueLessNull = new MethodologyValue(uDao.exist("test"), "MetValueLessNull",Double.valueOf("350000"),iDao.findIndicator("I5"),new MethodologyComparerLessThan());

        mDao.addMethodologyIfDontExist(metValueGreaterThan);
        mDao.addMethodologyIfDontExist(metValueGreaterNull);
        mDao.addMethodologyIfDontExist(metValueLessThan);
        mDao.addMethodologyIfDontExist(metValueLessNull);
    }


    @Test
    public void testMetValueGreaterThan(){

        List<MethodologyResult> results = metValueGreaterThan.run(companiesDao.getCompanies(), LocalDate.of(2017,3,1), LocalDate.of(2017,4,1));
        assertEquals(2, results.size());
        assertEquals(Double.valueOf(86000).toString(), results.get(0).getResult().toString());
        assertEquals("Mercadolibre", results.get(0).getCompany().getName());
    }

    @Test
    public void testMetValueGreaterNull(){

        List<MethodologyResult> results = metValueGreaterNull.run(companiesDao.getCompanies(), LocalDate.of(2017,3,1), LocalDate.of(2017,4,1));
        assertEquals(0, results.size());

    }
    @Test
    public void testMetValueLessThan(){

        List<MethodologyResult> results = metValueLessThan.run(companiesDao.getCompanies(), LocalDate.of(2017,3,1), LocalDate.of(2017,4,1));
        assertEquals(3, results.size());
        assertEquals(Double.valueOf(17500).toString(), results.get(0).getResult().toString());
        assertEquals("Musimundo", results.get(0).getCompany().getName());
    }

    @Test
    public void testMetValueLessNull(){

        List<MethodologyResult> results = metValueLessNull.run(companiesDao.getCompanies(), LocalDate.of(2017,3,1), LocalDate.of(2017,4,1));
        assertEquals(0, results.size());

    }

    @After
    public void teardown() {
        DeleteUtils.deleteAll();
    }
}