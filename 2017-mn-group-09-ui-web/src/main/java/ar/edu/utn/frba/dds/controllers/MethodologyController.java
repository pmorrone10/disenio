package ar.edu.utn.frba.dds.controllers;

import ar.edu.utn.frba.dds.configuration.PathConfiguration;
import ar.edu.utn.frba.dds.dao.CompaniesDao;
import ar.edu.utn.frba.dds.dao.IndicatorsDao;
import ar.edu.utn.frba.dds.dao.MethodologiesDao;
import ar.edu.utn.frba.dds.exception.IncompleteFormException;
import ar.edu.utn.frba.dds.exception.UnknownIndicatorException;
import ar.edu.utn.frba.dds.helper.SessionHelper;
import ar.edu.utn.frba.dds.models.MethodologyWebModel;
import ar.edu.utn.frba.dds.models.methodology.Methodology;
import ar.edu.utn.frba.dds.models.methodology.MethodologyFactory;
import ar.edu.utn.frba.dds.models.methodology.MethodologyTypeEnum;
import ar.edu.utn.frba.dds.models.methodology.comparer.MethodologyComparerTypeEnum;
import ar.edu.utn.frba.dds.models.views.MethodologyTable;
import spark.ModelAndView;
import spark.Request;
import spark.Response;
import spark.Spark;
import spark.template.handlebars.HandlebarsTemplateEngine;

import java.util.Arrays;

/**
 * Created by TATIANA on 10/10/2017.
 */
public class MethodologyController {

    public static final String HTML = "/pages/methodologies.html";

    private static MethodologiesDao dao = new MethodologiesDao();
    private static IndicatorsDao idao = new IndicatorsDao();
    private static CompaniesDao cdao = new CompaniesDao();
    private static MethodologyWebModel model;


    public static void init(){
        HandlebarsTemplateEngine engine = new HandlebarsTemplateEngine();
        Spark.get(PathConfiguration.methodologiesPath(),MethodologyController::load,engine);
        Spark.post(PathConfiguration.methodologiesPath(),MethodologyController::saveMethodology,engine);
        Spark.delete(PathConfiguration.deleteMethodologiesPath(),MethodologyController::deleteMethodology,engine);
    }

    public static ModelAndView load (Request request, Response response){
        if (!SessionHelper.existSession(request)){
            response.redirect(PathConfiguration.loginPath());
        }
        model = initModel(request);
        return new ModelAndView(updateModel(request),HTML);
    }

    public static ModelAndView saveMethodology (Request request,Response response){
        try {
            Methodology met = parseRequest(request);
            dao.addMethodologyIfDontExist(met);
        }catch (IncompleteFormException ex){
            response.status(410);
            response.body(ex.getMessage());
        }catch (Exception ex){
            response.status(400);
            response.body("Ocurrio un error. Intenta nuevamente");
        }

        return new ModelAndView(updateModel(request),HTML);
    }

    public static ModelAndView deleteMethodology (Request request,Response response){
        Methodology methodology = dao.findMethodology(request.params(":id"));
        try {
            dao.deleteMethodology(methodology);
            model.getAlert().setHideAlert();
        }catch (UnknownIndicatorException ex){
            response.status(412);
            response.body(ex.getMessage());
        }catch (Exception ex){
            response.status(400);
            response.body("Ocurrio un error. Intenta nuevamente");
        }
        return new ModelAndView(updateModel(request),HTML);
    }

    public static MethodologyWebModel initModel(Request request){
        MethodologyWebModel model = new MethodologyWebModel();
        model.setMethodologyTypes(Arrays.asList(MethodologyTypeEnum.values()));
        model.setIndicators(idao.getIndicators(SessionHelper.getUser(request)));
        model.setCompanies(cdao.getCompanies());
        model.setComparerTypeEnums(Arrays.asList(MethodologyComparerTypeEnum.values()));
        return model;
    }

    public static Methodology parseRequest(Request request){
        Methodology methodology = null;
        try {
            methodology = MethodologyFactory.methodology(SessionHelper.getUser(request),request.queryParams("methodology"),
                    request.queryParams("name"),request.queryParams("companies"),request.queryParams("comparator"),request.queryParams("indicators"),
                    request.queryParams("txtValue"));

        }catch (NullPointerException ex){
            throw new IncompleteFormException();
        }
        return methodology;
    }


    public static MethodologyWebModel updateModel(Request request){
        model.setMethodologies(dao.getMethodologies(SessionHelper.getUser(request)));
        model.setMtables(MethodologyTable.some(dao.getMethodologies(SessionHelper.getUser(request))));
        return model;
    }
}
