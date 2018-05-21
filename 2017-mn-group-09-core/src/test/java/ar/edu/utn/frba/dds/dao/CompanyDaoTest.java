package ar.edu.utn.frba.dds.dao;

import ar.edu.utn.frba.dds.configuration.EnviromentConfigurationHelper;
import ar.edu.utn.frba.dds.models.Account;
import ar.edu.utn.frba.dds.models.Company;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import java.time.LocalDate;
import static org.junit.Assert.*;

/**
 * Created by pablomorrone on 21/9/17.
 */
public class CompanyDaoTest {

    private CompaniesDao dao;
    private Account account;
    private Account account2;
    private Account account3;
    private Company company;
    private Company company2;
    /*
    @Before
    public void setup(){
        EnviromentConfigurationHelper.setEnviromentTest();
        this.dao = new CompaniesDao();
        account = new Account("ML",12.0,LocalDate.of(2017,02,02));
        account2 = new Account("Despe",12.0,LocalDate.of(2017,02,02));
        account3 = new Account("Despe",15.0,LocalDate.of(2017,02,03));
        company = new Company("Pablo", LocalDate.of(2017,01,01));
        company2 = new Company("Pablo3", LocalDate.of(2017,01,01));
        company.addAccount(account);
        company.addAccount(account2);
        company2.addAccount(account3);
    }

    @Test
    public void agregar(){
        dao.addCompanyIfDontExist(company);
        assertEquals(dao.getCompanies().size(),1);
    }

    @Test
    public void agregarDosEmpresas(){
        dao.addCompanyIfDontExist(company);
        dao.addCompanyIfDontExist(company);
        dao.addCompanyIfDontExist(company2);
        assertEquals(dao.getCompanies().size(),2);
    }

    /*@Test
    public void borrarEmpresa(){
        dao.addCompanyIfDontExist(company2);
        dao.deleteCompany(company2);
        assertEquals(dao.getCompanies().size(),0);
    }

    @After
    public void tearDown(){
        for (Company c:dao.getCompanies()) {
            dao.deleteCompany(c);
        }
    }*/


}
