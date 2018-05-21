package ar.edu.utn.frba.dds.exception;

/**
 * Created by pablomorrone on 11/4/17.
 */
public class UnknownIndicatorException extends RuntimeException {

    public UnknownIndicatorException(){
        super();
    }

    public UnknownIndicatorException(String e){
        super(e);
    }

}
