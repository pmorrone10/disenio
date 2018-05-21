package ar.edu.utn.frba.dds.helper;

import ar.edu.utn.frba.dds.dao.UsersDao;
import ar.edu.utn.frba.dds.models.users.User;
import spark.Request;

/**
 * Created by pablomorrone on 3/10/17.
 */
public class SessionHelper {

    private static final String SESSION = "username";

    public static void setSession(Request request, User user){
        request.session().attribute(SESSION,user.userSession());
    }

    public static String getUserSession(Request request){
        return request.session().attribute(SESSION);
    }

    public static Boolean existSession(Request request){
        return request.session().attribute(SESSION) != null;
    }

    public static void deleteSession(Request request){
        request.session().removeAttribute(SESSION);
    }

    public static User getUser(Request request){
        return new UsersDao().exist(getIdUser(request));
    }

    public static int getIdUser(Request request){
        String user = getUserSession(request);
        return Integer.parseInt(user.substring(0,user.indexOf("-")));
    }


}
