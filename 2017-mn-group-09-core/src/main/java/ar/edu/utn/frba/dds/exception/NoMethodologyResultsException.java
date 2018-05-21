package ar.edu.utn.frba.dds.exception;

/**
 * Created by pablomorrone on 11/4/17.
 */
public class NoMethodologyResultsException extends RuntimeException {

    public NoMethodologyResultsException(){
        super();
    }

    public NoMethodologyResultsException(String e){
        super(e);
    }

}
