package ar.edu.utn.frba.dds.models.indicator;

public enum IndicatorExpressionTypeEnum {
    ACCOUNT("Cuenta"),
    NUMBER("Número"),
    OPERATION("Operación"),
    INDICATOR("Indicator");

    private final String description;

    IndicatorExpressionTypeEnum(String description) {
        this.description = description;
    }

    public String getDescription() {
        return this.description;
    }

}
