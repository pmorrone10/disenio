package ar.edu.utn.frba.dds.exception;

/**
 * Created by pablomorrone on 11/4/17.
 */
public class InvalidRangeDateException extends RuntimeException {

    public InvalidRangeDateException(){
        super();
    }

    public InvalidRangeDateException(String e){
        super(e);
    }

}
