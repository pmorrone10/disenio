/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ar.edu.utn.frba.dds.indicator;

import ar.edu.utn.frba.dds.configuration.EnviromentConfigurationHelper;
import ar.edu.utn.frba.dds.dao.IndicatorsDao;
import ar.edu.utn.frba.dds.dao.UsersDao;
import ar.edu.utn.frba.dds.exception.UnknownIndicatorException;
import ar.edu.utn.frba.dds.models.Indicator;
import ar.edu.utn.frba.dds.models.users.User;
import ar.edu.utn.frba.dds.utils.DeleteUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 *
 * @author lili
 */
public class IndicatorLoadTest {
    private IndicatorsDao iDao;
    private UsersDao uDao;

    private Indicator indicator1;
    private Indicator indicator2;
    private Indicator indicator3;

    private User user;

    @Before
    public void setUp() throws Exception {
        EnviromentConfigurationHelper.setEnviromentTest();
        DeleteUtils.deleteAll();

        iDao = new IndicatorsDao();
        uDao = new UsersDao();

        user = new User("test", "test");
        user = uDao.addIfDontExist(user);

        indicator1 = new Indicator(uDao.exist("test"), "IndicatorLoadTest1", "AC_EBITDA+4");
        indicator2 = new Indicator(uDao.exist("test"), "IndicatorLoadTest2", "Indicator1+3");
        indicator3 = new Indicator(uDao.exist("test"), "IndicatorLoadTest3", "AC_EBITDA+AC_IGNETO");
    }

    @Test
    public void IndicatorAddTest() {
        int previousCant = iDao.getIndicators(uDao.exist("test")).size();

        iDao.addIndicatorIfDontExist(indicator1);
        iDao.addIndicatorIfDontExist(indicator2);

        assertEquals(previousCant + 2, iDao.getIndicators(uDao.exist("test")).size());
    }

    @Test(expected = UnknownIndicatorException.class)
    public void IndicatorDeleteUnknown() {
        iDao.deleteIndicator("Indicator90");
    }

    @Test
    public void testAddNewIndicator() {
        int previousCant = iDao.getIndicators(uDao.exist("test")).size();
        iDao.addIndicatorIfDontExist(indicator3);
        assertEquals(previousCant + 1, iDao.getIndicators(uDao.exist("test")).size());
    }

    @After
    public void teardown() {
        DeleteUtils.deleteAll();
    }
}



