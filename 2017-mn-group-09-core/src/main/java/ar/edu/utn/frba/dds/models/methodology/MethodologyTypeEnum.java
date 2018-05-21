package ar.edu.utn.frba.dds.models.methodology;

import ar.edu.utn.frba.dds.models.methodology.comparer.MethodologyComparerTypeEnum;
import java.util.Arrays;
import java.util.List;

/**
 * Created by pablomorrone on 13/6/17.
 */
public enum MethodologyTypeEnum {

    COMPANIES(1,"Con Empresas"),
    OWN(2,"Misma Empresa"),
    VALUE(3,"Valor Fijo");

    private final int id;
    private final String description;

    MethodologyTypeEnum(int id, String description) {
        this.id = id;
        this.description = description;
    }

    public String getDescription() {
        return this.description;
    }

    public int getId(){
        return this.id;
    }

    public static  MethodologyTypeEnum fromString(String code) {
        for (MethodologyTypeEnum status :MethodologyTypeEnum.values()){
            if (status.getDescription().equals(code)){
                return status;
            }
        }
        throw new UnsupportedOperationException(
                "The string " + code + " is not supported!");
    }

    public static MethodologyTypeEnum fromInt(int code){
        for (MethodologyTypeEnum status :MethodologyTypeEnum.values()){
            if (status.getId() == code){
                return status;
            }
        }
        throw new UnsupportedOperationException("The string " + code + " is not supported!");
    }

    public static List<MethodologyComparerTypeEnum> getComparer(MethodologyTypeEnum e){
        switch (e){
            case VALUE:
            case COMPANIES:{return Arrays.asList(MethodologyComparerTypeEnum.GREAT_THAN,MethodologyComparerTypeEnum.LESS_THAN);}
            case OWN:{return Arrays.asList(MethodologyComparerTypeEnum.ASCENDING,MethodologyComparerTypeEnum.DESCENDING);}
            default: return null;
        }
    }

}
