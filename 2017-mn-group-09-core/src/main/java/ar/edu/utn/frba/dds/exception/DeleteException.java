package ar.edu.utn.frba.dds.exception;

/**
 * Created by pablomorrone on 31/7/17.
 */
public class DeleteException extends RuntimeException {

    public DeleteException(){
        super("No se pudo borrar el registro");
    }

    public DeleteException(String message){
        super(message);
    }

}
