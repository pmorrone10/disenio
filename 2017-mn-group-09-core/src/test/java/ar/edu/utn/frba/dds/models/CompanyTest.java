package ar.edu.utn.frba.dds.models;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import java.time.LocalDate;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Created by TATIANA on 17/4/2017.
 */
public class CompanyTest {
    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();

    private Account newAccount;
    private Account existentAccount;

    private Company company;

    @Before
    public void setUp() throws Exception {
        company = new Company("Empresa de Test",LocalDate.now());
        existentAccount = mock(Account.class);
        when(existentAccount.getName()).thenReturn("Cuenta Existente");
        when(existentAccount.getTimestamp()).thenReturn(LocalDate.of(2017,04,18));
        when(existentAccount.getValue()).thenReturn(Double.valueOf(1000));

        company.addAccount(existentAccount);

        newAccount = mock(Account.class);
        when(newAccount.getName()).thenReturn("Cuenta Nueva");
        when(existentAccount.getTimestamp()).thenReturn(LocalDate.of(2017,04,18));
        when(existentAccount.getValue()).thenReturn(Double.valueOf(1000));
    }

    @Test
    public void testAddExistentAccount()
    {
        company.addAccount(existentAccount);
        assertEquals(1, company.getAccounts().size());
        
    }

    @Test
    public void testAddNewAccount()
    {
        company.addAccount(newAccount);
        assertEquals(2, company.getAccounts().size());
    }
}
