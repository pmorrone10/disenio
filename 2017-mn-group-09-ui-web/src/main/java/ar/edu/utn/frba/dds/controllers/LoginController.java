package ar.edu.utn.frba.dds.controllers;

import ar.edu.utn.frba.dds.configuration.PathConfiguration;
import ar.edu.utn.frba.dds.dao.UsersDao;
import ar.edu.utn.frba.dds.models.AlertModel;
import ar.edu.utn.frba.dds.models.users.User;
import ar.edu.utn.frba.dds.helper.SessionHelper;
import spark.ModelAndView;
import spark.Request;
import spark.Response;
import spark.Spark;
import spark.template.handlebars.HandlebarsTemplateEngine;

/**
 * Created by pablomorrone on 20/9/17.
 */
public class LoginController {

    private static final String LOGIN = "/pages/login/login.html";
    private static final String FAIL = "/pages/login/failLogin.html";
    private static AlertModel alert = new AlertModel(false,"",false);

    public static ModelAndView showLogin(Request request, Response response){
        if (SessionHelper.existSession(request)){
            response.redirect(PathConfiguration.homePath());
        }
        alert.setHideAlert();
        return new ModelAndView(alert, LOGIN);
    }

    public static ModelAndView checkLogin(Request request, Response response){
        User user = parseRequest(request);
        UsersDao dao = new UsersDao();
        User u = dao.exist(user.getUsername());
        if (u != null && u.getPassword().equals(user.getPassword())){
            SessionHelper.setSession(request,u);
            response.redirect(PathConfiguration.homePath());
            alert.setHideAlert();
        }
        response.status(403);
        alert.setShowAlertWithMessage("El usuario o password es incorrecto");
        return new ModelAndView(alert,LOGIN);
    }

    public static void init(){
        HandlebarsTemplateEngine engine = new HandlebarsTemplateEngine();
        Spark.get(PathConfiguration.loginPath(),LoginController::showLogin,engine);
        Spark.post(PathConfiguration.loginPath(),LoginController::checkLogin,engine);
    }

    public static User parseRequest(Request request){
        User user = new User();
        user.setUsername(request.queryParams("email"));
        user.setPassword(request.queryParams("password"));
        return user;
    }
}
