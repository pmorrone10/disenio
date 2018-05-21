package ar.edu.utn.frba.dds;
import ar.edu.utn.frba.dds.configuration.EnviromentConfigurationHelper;
import ar.edu.utn.frba.dds.controllers.AutomaticLoader;
import ar.edu.utn.frba.dds.dao.CompaniesDao;
import ar.edu.utn.frba.dds.exception.ParserErrorException;
import ar.edu.utn.frba.dds.utils.DeleteUtils;
import ar.edu.utn.frba.dds.utils.FileUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by gasti on 16/04/2017.
 */
public class AutomaticLoaderTest {

    private String companyPath;
    private String company2Path;
    private String companyIncompletPath;
    private AutomaticLoader automaticLoader;
    private CompaniesDao dao;

    @Before
    public void setUp() throws Exception {
        EnviromentConfigurationHelper.setEnviromentTest();
        DeleteUtils.deleteAll();
        dao = new CompaniesDao();
        companyPath = FileUtils.filePath(this.getClass(), "/files/empresas.csv");
        company2Path = FileUtils.filePath(this.getClass(), "/files/empresas2.csv");
        companyIncompletPath = FileUtils.filePath(this.getClass(),"/files/empresasConUnCampoMenos.csv");
        automaticLoader = new AutomaticLoader();
    }

    @Test
    public void testCompanyLoad() throws Exception {
        this.automaticLoader.LoadCompaniesFromFile(company2Path);
        assertEquals(dao.getCompanies().size(),3);
    }

    @Test
    public void testCompanyLoadFromTwoFiles() throws Exception {
        this.automaticLoader.LoadCompaniesFromFile(companyPath);
        this.automaticLoader.LoadCompaniesFromFile(company2Path);
        assertEquals(dao.getCompanies().size(),8);
    }

    @Test(expected = ParserErrorException.class)
    public void testInvalidFormatFile() throws Exception{
        this.automaticLoader.LoadCompaniesFromFile(companyIncompletPath);
    }

    @After
    public void teardown() {
        DeleteUtils.deleteAll();
    }
}