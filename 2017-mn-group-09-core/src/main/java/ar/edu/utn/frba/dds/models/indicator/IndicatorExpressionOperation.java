package ar.edu.utn.frba.dds.models.indicator;

import ar.edu.utn.frba.dds.models.Company;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Dictionary;
import java.util.Hashtable;
import java.util.List;

/**
 * Created by TATIANA on 19/7/2017.
 */
public class IndicatorExpressionOperation extends IndicatorExpression {

    private IndicatorExpression operand1;

    private IndicatorExpression operand2;
    IndicatorOperatorEnum operator;

    public IndicatorExpressionOperation(IndicatorExpression operand1, IndicatorExpression operand2, IndicatorOperatorEnum operator) {
        this.operand1 = operand1;
        this.operand2 = operand2;
        this.operator = operator;
    }

    public IndicatorExpressionOperation() {
    }

    @Override
    public List<IndicatorResult> calc(Company company, LocalDate from, LocalDate to) {
        Dictionary<LocalDate, IndicatorResult> operators = new Hashtable<>();
        List<IndicatorResult> ret = new ArrayList<>();
        List<IndicatorResult> resultsOperand1 = operand1.calc(company, from, to);
        List<IndicatorResult> resultsOperand2 = operand2.calc(company, from, to);


        for (IndicatorResult r: resultsOperand1) {
            if (r.getDate() != null) operators.put(r.getDate(), r);
            else {
                for (IndicatorResult op2: resultsOperand2) {
                    LocalDate date = op2.getDate() != null ? op2.getDate() : r.getDate();

                    if (op2 != null)
                        ret.add(new IndicatorResult(operator.toString(), company, date, calOperator(r.getValue(), op2.getValue())));

                }
            }
        }

        for (IndicatorResult r: resultsOperand2) {
            LocalDate date = r.getDate();
            if (r.getDate() != null) {
                IndicatorResult op1 = operators.get(r.getDate());
                if (op1 != null) {
                    date = op1.getDate() != null ? op1.getDate() : r.getDate();
                    ret.add(new IndicatorResult(operator.toString(), company, date, calOperator(op1.getValue(),r.getValue())));
                }
            } else {
                for (IndicatorResult op1 : resultsOperand1) {
                    ret.add(new IndicatorResult(operator.toString(), company, date, calOperator(op1.getValue(), r.getValue())));
                }
            }
        }

        return ret;
    }

    private Double calOperator(Double op1, Double op2) {
        switch (operator) {
            case DIV:
                return op1 / op2;
            case TIMES:
                return op1 * op2;
            case PLUS:
                return op1 + op2;
            case MINUS:
                return op1 - op2;
        }

        return null;
    }
}
