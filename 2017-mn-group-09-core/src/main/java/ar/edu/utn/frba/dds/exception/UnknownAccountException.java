package ar.edu.utn.frba.dds.exception;

/**
 * Created by pablomorrone on 11/4/17.
 */
public class UnknownAccountException extends RuntimeException {

    public UnknownAccountException(){
        super();
    }

    public UnknownAccountException(String e){
        super(e);
    }

}
