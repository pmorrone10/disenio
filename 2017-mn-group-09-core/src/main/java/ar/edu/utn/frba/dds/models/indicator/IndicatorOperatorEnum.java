package ar.edu.utn.frba.dds.models.indicator;

/**
 * Created by TATIANA on 19/7/2017.
 */
public enum IndicatorOperatorEnum {
    TIMES("*"),
    PLUS("+"),
    DIV("/"),
    MINUS("-");



    private final String description;

    IndicatorOperatorEnum(String description) {
        this.description = description;
    }

    public String getDescription() {
        return this.description;
    }
}
