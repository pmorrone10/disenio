package ar.edu.utn.frba.dds.models.indicator;

import ar.edu.utn.frba.dds.models.Company;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by TATIANA on 19/7/2017.
 */
public class IndicatorExpressionNumber extends IndicatorExpression {

    Double number;

    public IndicatorExpressionNumber(Double number) {
        this.number = number;
    }

    public IndicatorExpressionNumber() {
    }

    @Override
    public List<IndicatorResult> calc(Company company, LocalDate from, LocalDate to) {
        List<IndicatorResult> ret = new ArrayList<>();
        ret.add(new IndicatorResult(number.toString(), company, null, number));
        return ret;
    }
}
