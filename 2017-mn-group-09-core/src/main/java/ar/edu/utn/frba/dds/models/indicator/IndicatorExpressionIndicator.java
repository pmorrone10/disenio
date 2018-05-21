package ar.edu.utn.frba.dds.models.indicator;

import ar.edu.utn.frba.dds.dao.IndicatorsDao;
import ar.edu.utn.frba.dds.exception.UnknownIndicatorException;
import ar.edu.utn.frba.dds.models.Company;
import ar.edu.utn.frba.dds.models.Indicator;

import java.time.LocalDate;
import java.util.List;

/**
 * Created by TATIANA on 19/7/2017.
 */

public class IndicatorExpressionIndicator extends IndicatorExpression {

    private Indicator indicator = null;

    private String indicatorName;

    public IndicatorExpressionIndicator(String indicatorName) {
        this.indicatorName = indicatorName;
    }

    public IndicatorExpressionIndicator() {
    }


    @Override
    public List<IndicatorResult> calc(Company company, LocalDate from, LocalDate to) {

        if (getIndicator() != null )
            return getIndicator().eval(company, from, to);

        throw new UnknownIndicatorException();
    }

    public Indicator getIndicator() {
        if (indicator == null) {
            indicator = new IndicatorsDao().findIndicator(indicatorName);
        }
        return indicator;
    }
}
