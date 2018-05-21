package ar.edu.utn.frba.dds.indicator;

import ar.edu.utn.frba.dds.configuration.EnviromentConfigurationHelper;
import ar.edu.utn.frba.dds.dao.CompaniesDao;
import ar.edu.utn.frba.dds.dao.IndicatorsDao;
import ar.edu.utn.frba.dds.dao.UsersDao;
import ar.edu.utn.frba.dds.models.Account;
import ar.edu.utn.frba.dds.models.Company;
import ar.edu.utn.frba.dds.models.Indicator;
import ar.edu.utn.frba.dds.models.users.User;
import ar.edu.utn.frba.dds.models.indicator.IndicatorResult;
import ar.edu.utn.frba.dds.utils.DateUtils;
import ar.edu.utn.frba.dds.utils.DeleteUtils;
import org.junit.*;

import java.time.LocalDate;
import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * Created by TATIANA on 16/5/2017.
 */
public class IndicatorTest {
    private Account account1;
    private Account account2;
    private Account account3;

    private Company company;
    private IndicatorsDao iDao;
    private UsersDao uDao;

    private Indicator indicator1;
    private Indicator indicator2;
    private Indicator indicator3;
    private Indicator indicator4;
    private Indicator indicator5;
    private Indicator indicator6;
    private Indicator indicator7;

    private User user;

    @Before
    public void setUp() {
        EnviromentConfigurationHelper.setEnviromentTest();
        DeleteUtils.deleteAll();

        iDao = new IndicatorsDao();
        uDao = new UsersDao();

        user = new User("test", "test");
        user = uDao.addIfDontExist(user);

        account1 = new Account("EBITDA", Double.valueOf(1000), DateUtils.getDateFromString("01/01/2017"));
        account2 = new Account("IGNETO", Double.valueOf(2000), DateUtils.getDateFromString("01/01/2017"));
        account3 = new Account("FDS", Double.valueOf(1500), DateUtils.getDateFromString("01/01/2017"));

        company = new Company("Garbarino", LocalDate.now());
        company.addAccount(account1);
        company.addAccount(account2);
        company.addAccount(account3);

        new CompaniesDao().addCompanyIfDontExist(company);

        indicator1 = new Indicator(uDao.exist("test"), "I1", "3+2");
        indicator2 = new Indicator(uDao.exist("test"), "I2", "I1+2");
        indicator3 = new Indicator(uDao.exist("test"), "I3", "8+AC_EBITDA");
        indicator4 = new Indicator(uDao.exist("test"), "I4", "I2+AC_EBITDA");
        indicator5 = new Indicator(uDao.exist("test"), "I5", "AC_FDC+I2");
        indicator6 = new Indicator(uDao.exist("test"), "I6", "I3+I4");
        indicator7 = new Indicator(uDao.exist("test"), "I7", "AC_IGNETO/2");

        iDao.addIndicatorIfDontExist(indicator1);
        iDao.addIndicatorIfDontExist(indicator2);
        iDao.addIndicatorIfDontExist(indicator3);
        iDao.addIndicatorIfDontExist(indicator4);
        iDao.addIndicatorIfDontExist(indicator5);
        iDao.addIndicatorIfDontExist(indicator6);
        iDao.addIndicatorIfDontExist(indicator7);
    }


    @Test
    public void testSimpleEval(){
        List<IndicatorResult> results = indicator1.eval(company, null, null);
        assertEquals(2, results.size());
        assertEquals(Double.valueOf(5).toString(), results.get(0).getValue().toString());
    }

    @Test
    public void testIndicatorEval(){
        List<IndicatorResult> results = indicator2.eval(company, null, null);
        assertEquals(4, results.size());
        assertEquals(Double.valueOf(7).toString(), results.get(0).getValue().toString());
    }

    @Test
    public void testAccountEval(){
        List<IndicatorResult> results = indicator3.eval(company, null, null);
        assertEquals(1, results.size());
        assertEquals(Double.valueOf(1008).toString(), results.get(0).getValue().toString());
    }

    @Test
    public void testAccountAndIndicatorEval(){
        List<IndicatorResult> results = indicator4.eval(company, null, null);
        assertEquals(4, results.size());
        assertEquals(Double.valueOf(1007).toString(), results.get(0).getValue().toString());
   }

    @Test
    public void testAccountNotFound(){
        List<IndicatorResult> results = indicator5.eval(company, null, null);
        assertEquals(0, results.size());
    }

    @Test
    public void testIndicatorAndOtherIndicator(){
        List<IndicatorResult> results = indicator6.eval(company, null, null);
        assertEquals(4, results.size());
        assertEquals(Double.valueOf(2015).toString(), results.get(0).getValue().toString());
    }
    @Test
    public void testIndicatorOtherCalc() {
        List<IndicatorResult> results = indicator7.eval(company, null, null);
        assertEquals(1, results.size());
        assertEquals(Double.valueOf(1000).toString(), results.get(0).getValue().toString());
    }

    @After
    public void teardown() {
        DeleteUtils.deleteAll();
    }
}
