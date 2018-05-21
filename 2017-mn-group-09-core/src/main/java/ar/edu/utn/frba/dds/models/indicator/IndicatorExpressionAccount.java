package ar.edu.utn.frba.dds.models.indicator;

import ar.edu.utn.frba.dds.exception.UnknownAccountException;
import ar.edu.utn.frba.dds.models.Account;
import ar.edu.utn.frba.dds.models.AccountName;
import ar.edu.utn.frba.dds.models.Company;
import ar.edu.utn.frba.dds.utils.ConfigurationUtils;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by TATIANA on 26/8/2017.
 */
public class IndicatorExpressionAccount extends IndicatorExpression {

    private AccountName accountName;

    public IndicatorExpressionAccount(AccountName accountName) {
        this.accountName = accountName;
    }

    public IndicatorExpressionAccount(String name){
        this.accountName = new AccountName(name);
    }

    @Override
    public List<IndicatorResult> calc(Company company, LocalDate from, LocalDate to) {

        List<IndicatorResult> ret = new ArrayList<>();

        if (accountName.getName().toLowerCase().equals(ConfigurationUtils.getConfiguration("ar.edu.utn.frba.dds.models.indicator.antiquityLabel"))) {
            ret.add(new IndicatorResult(accountName.getName(), company, null, (double) (LocalDate.now().getYear() - company.getCreationDate().getYear())));
            return ret;
        }

        for (Account a : company.searchAccount(accountName.getName(), from, to))
            ret.add(new IndicatorResult(a.getName(), company, a.getTimestamp(), a.getValue()));

        if (ret.size() > 0) return ret;

        throw new UnknownAccountException(accountName.getName());
    }
}
