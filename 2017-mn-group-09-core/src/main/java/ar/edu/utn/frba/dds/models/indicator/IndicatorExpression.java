package ar.edu.utn.frba.dds.models.indicator;

import ar.edu.utn.frba.dds.models.Company;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by TATIANA on 19/7/2017.
 */
public abstract class IndicatorExpression {

    public List<IndicatorResult> calc(Company company, LocalDate from, LocalDate to) {
        return new ArrayList<>();
    }
}
