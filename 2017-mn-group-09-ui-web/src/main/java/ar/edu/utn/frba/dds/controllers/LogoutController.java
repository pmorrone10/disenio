package ar.edu.utn.frba.dds.controllers;

import ar.edu.utn.frba.dds.configuration.PathConfiguration;
import ar.edu.utn.frba.dds.helper.SessionHelper;
import spark.Spark;


/**
 * Created by pablomorrone on 3/10/17.
 */
public class LogoutController {

    public static void init(){
        Spark.get(PathConfiguration.logoutPath(),((request, response) -> {
            SessionHelper.deleteSession(request);
            response.redirect(PathConfiguration.loginPath());
            return null;
        }));
    }

}
