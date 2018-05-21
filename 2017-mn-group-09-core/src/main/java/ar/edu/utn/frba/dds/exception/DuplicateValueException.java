package ar.edu.utn.frba.dds.exception;

/**
 * Created by pablomorrone on 31/7/17.
 */
public class DuplicateValueException extends RuntimeException {

    public DuplicateValueException(){
        super("Valor ya existente en el storege");
    }

    public DuplicateValueException(String ex){
        super(ex);
    }
}
