package ar.edu.utn.frba.dds.models.methodology.comparer;

/**
 * Created by pablomorrone on 13/6/17.
 */
public enum MethodologyComparerTypeEnum {

    ASCENDING(1,"CRECIENTE"),
    DESCENDING(2,"DESCENDENTE"),
    GREAT_THAN(3,"Mayor Que"),
    LESS_THAN(4,"Menor Que");

    private final String description;
    private final int id;

    MethodologyComparerTypeEnum(int id, String description) {
        this.id = id;
        this.description = description;
    }

    public String getDescription() {
        return this.description;
    }

    public int getId(){return this.id;}

    public static MethodologyComparer getValue(MethodologyComparerTypeEnum value) {
        switch (value) {
            case GREAT_THAN: return new MethodologyComparerGreaterThan();
            case LESS_THAN: return new MethodologyComparerLessThan();
            case ASCENDING: return new MethodologyComparerAscending();
            case DESCENDING: return new MethodologyComparerDescending();
        }
        return null;
    }

    public static MethodologyComparerTypeEnum fromString(String code) {
        for (MethodologyComparerTypeEnum status :MethodologyComparerTypeEnum.values()){
            if (status.getDescription().equals(code)){
                return status;
            }
        }
        throw new UnsupportedOperationException(
                "The string " + code + " is not supported!");
    }

    public static MethodologyComparerTypeEnum fromInt(int code) {
        for (MethodologyComparerTypeEnum status :MethodologyComparerTypeEnum.values()){
            if (status.getId() == code){
                return status;
            }
        }
        throw new UnsupportedOperationException(
                "The string " + code + " is not supported!");
    }

}
