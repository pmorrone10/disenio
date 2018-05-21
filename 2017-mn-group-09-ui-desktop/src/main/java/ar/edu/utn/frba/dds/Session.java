package ar.edu.utn.frba.dds;

import ar.edu.utn.frba.dds.models.users.User;

/**
 * Created by pablomorrone on 5/10/17.
 */
public class Session {

    private User user;
    private static Session instance = null;

    public static Session getSession(){
        if (instance == null){
            instance = new Session();
        }
        return instance;
    }

    public void setUser(User user){
        this.user = user;
    }

    public User getUser(){
        return this.user;
    }

    public void clearUser(){
        this.user = null;
    }


}
