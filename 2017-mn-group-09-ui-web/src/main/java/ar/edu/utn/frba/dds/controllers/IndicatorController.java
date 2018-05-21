package ar.edu.utn.frba.dds.controllers;

import ar.edu.utn.frba.dds.configuration.PathConfiguration;
import ar.edu.utn.frba.dds.dao.IndicatorsDao;
import ar.edu.utn.frba.dds.exception.DuplicateValueException;
import ar.edu.utn.frba.dds.exception.IncompleteFormException;
import ar.edu.utn.frba.dds.exception.UnknownIndicatorException;
import ar.edu.utn.frba.dds.helper.SessionHelper;
import ar.edu.utn.frba.dds.models.Indicator;
import ar.edu.utn.frba.dds.models.IndicatorWebModel;
import spark.ModelAndView;
import spark.Request;
import spark.Response;
import spark.Spark;
import spark.template.handlebars.HandlebarsTemplateEngine;

/**
 * Created by TATIANA on 10/10/2017.
 */
public class IndicatorController {

    public static final String HTML = "/pages/indicators.html";

    private static IndicatorsDao dao = new IndicatorsDao();
    private static IndicatorWebModel model = new IndicatorWebModel();

    public static void init(){
        HandlebarsTemplateEngine engine = new HandlebarsTemplateEngine();
        Spark.get(PathConfiguration.indicatorsPath(),IndicatorController::load,engine);
        Spark.post(PathConfiguration.indicatorsPath(),IndicatorController::save,engine);
        Spark.delete(PathConfiguration.deleteindicatorsPath(),IndicatorController::delete,engine);
    }

    public static ModelAndView load (Request request, Response response){
        if (!SessionHelper.existSession(request)){
            response.redirect(PathConfiguration.loginPath());
        }
        updateIndicatorModel(request);
        return new ModelAndView(model,HTML);
    }

    public static ModelAndView save (Request request,Response response){
        try {
            Indicator indicator = parseRequest(request);
            dao.addIndicatorIfDontExist(indicator);
            model.getAlert().setHideAlert();
        }catch (IncompleteFormException ex){
            response.status(410);
            response.body(ex.getMessage());
        }catch (DuplicateValueException ex){
            response.status(411);
            response.body(ex.getMessage());
        }catch (Exception ex){
            response.status(400);
            response.body("Ocurrio un error. Intenta nuevamente");
        }
        updateIndicatorModel(request);
        return new ModelAndView(model,HTML);
    }

    public static ModelAndView delete (Request request,Response response){
        try {
            Indicator indicator = dao.findIndicator(request.params(":id"));
            dao.deleteIndicator(indicator);
        }catch (UnknownIndicatorException ex){
            response.status(412);
            response.body(ex.getMessage());
        }catch (DuplicateValueException ex){
            response.status(413);
            response.body(ex.getMessage());
        }catch (Exception ex){
            response.status(400);
            response.body("Ocurrio un error. Intenta nuevamente");
        }
        return new ModelAndView(model,HTML);
    }

    public static Indicator parseRequest(Request request){
        Indicator indicator = new Indicator();
        if (!request.queryParams("name").isEmpty() && !request.queryParams("expression").isEmpty()){
            indicator.setName(request.queryParams("name"));
            indicator.setExpression(request.queryParams("expression"));
            indicator.setUser(SessionHelper.getUser(request));
        }else{
            throw new IncompleteFormException();
        }

        return indicator;
    }

    private static void updateIndicatorModel(Request request){
        model.setIndicators(dao.getIndicators(SessionHelper.getUser(request)));
    }
}
