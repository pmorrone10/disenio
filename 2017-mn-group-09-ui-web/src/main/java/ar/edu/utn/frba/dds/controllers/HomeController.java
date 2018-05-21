package ar.edu.utn.frba.dds.controllers;

import ar.edu.utn.frba.dds.configuration.PathConfiguration;
import ar.edu.utn.frba.dds.helper.SessionHelper;
import spark.ModelAndView;
import spark.Request;
import spark.Response;
import spark.Spark;
import spark.template.handlebars.HandlebarsTemplateEngine;

/**
 * Created by pablomorrone on 3/10/17.
 */
public class HomeController {

    public static final String HTML = "home.html";

    public static void init(){
        HandlebarsTemplateEngine engine = new HandlebarsTemplateEngine();
        Spark.get(PathConfiguration.homePath(),HomeController::load,engine);
        Spark.post(PathConfiguration.homePath(),LoginController::checkLogin,engine);
    }

    public static ModelAndView load (Request request, Response response){
        if (!SessionHelper.existSession(request)){
            response.redirect(PathConfiguration.loginPath());
        }
        return new ModelAndView(null,HTML);
    }
}
