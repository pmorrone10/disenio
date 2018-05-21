package ar.edu.utn.frba.dds.models;

import ar.edu.utn.frba.dds.configuration.EnviromentConfigurationHelper;
import ar.edu.utn.frba.dds.controllers.AutomaticLoader;
import ar.edu.utn.frba.dds.dao.CompaniesDao;
import ar.edu.utn.frba.dds.exception.InvalidRangeDateException;
import ar.edu.utn.frba.dds.utils.DateUtils;
import ar.edu.utn.frba.dds.utils.DeleteUtils;
import ar.edu.utn.frba.dds.utils.FileUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import java.net.URISyntaxException;
import java.time.LocalDate;
import static org.junit.Assert.assertEquals;

/**
 * Created by pablomorrone on 15/8/17.
 */
public class SearchModelTest {

    private LocalDate from;
    private LocalDate to;
    private CompaniesDao companiesDao;


    @Before
    public void setup() throws URISyntaxException {
        EnviromentConfigurationHelper.setEnviromentTest();
        this.companiesDao = new CompaniesDao();
        new AutomaticLoader().LoadCompaniesFromFile(FileUtils.filePath(this.getClass(), "/files/empresas.csv"));
        this.from = LocalDate.of(2017, 1, 1);
        this.to = LocalDate.of(2017, 4, 1);
    }

    @Test
    public void testFromDateNull(){
        SearchModel model = new SearchModel(null, null,null, to);
        assertEquals(model.getFrom(), DateUtils.firstDate());
    }

    @Test
    public void testToDateNull(){
        SearchModel model = new SearchModel(null, null,from,null);
        assertEquals(model.getTo(),LocalDate.now());
    }

    @Test
    public void testCompanyNull(){
        SearchModel model = new SearchModel(null, null,from,to);
        assertEquals(model.getCompanies().size(), companiesDao.getCompanies().size());
    }

    @Test
    public void testCompanyNotNull(){
        SearchModel model = new SearchModel(null, companiesDao.getCompanies().get(0),from,to);
        assertEquals(model.getCompanies().size(),1);
    }

    @Test(expected = InvalidRangeDateException.class)
    public void testInvalidRange(){
        SearchModel model = new SearchModel(null, null,to,from);
        model.validateDates();
    }

    @After
    public void teardown() {
        DeleteUtils.deleteAll();
    }
}
