package ar.edu.utn.frba.dds.exception;

/**
 * Created by pablomorrone on 5/10/17.
 */
public class LoginException extends RuntimeException{

    public LoginException(){
        super("El usuario o contraseña no son correctos");
    }

    public LoginException(String message){
        super(message);
    }
}
