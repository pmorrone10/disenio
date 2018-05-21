package ar.edu.utn.frba.dds.controllers;

import ar.edu.utn.frba.dds.configuration.PathConfiguration;
import ar.edu.utn.frba.dds.dao.CompaniesDao;
import ar.edu.utn.frba.dds.dao.IndicatorsDao;
import ar.edu.utn.frba.dds.dao.MethodologiesDao;
import ar.edu.utn.frba.dds.exception.NoMethodologyResultsException;
import ar.edu.utn.frba.dds.helper.SessionHelper;
import ar.edu.utn.frba.dds.models.Company;
import ar.edu.utn.frba.dds.models.SearchModel;
import ar.edu.utn.frba.dds.models.SearchWebModel;
import ar.edu.utn.frba.dds.models.methodology.Methodology;
import ar.edu.utn.frba.dds.models.users.User;
import ar.edu.utn.frba.dds.models.views.CompanyTable;
import ar.edu.utn.frba.dds.utils.DateUtils;
import spark.ModelAndView;
import spark.Request;
import spark.Response;
import spark.Spark;
import spark.template.handlebars.HandlebarsTemplateEngine;

import java.time.LocalDate;

/**
 * Created by TATIANA on 10/10/2017.
 */
public class SearchController {

    public static final String HTML = "/pages/search.html";
    private static SearchWebModel model = new SearchWebModel();
    private static CompaniesDao dao = new CompaniesDao();
    private static IndicatorsDao idao = new IndicatorsDao();
    private static MethodologiesDao mdao = new MethodologiesDao();

    public static void init(){
        HandlebarsTemplateEngine engine = new HandlebarsTemplateEngine();
        Spark.get(PathConfiguration.searchPath(),SearchController::load,engine);
        Spark.get(PathConfiguration.searchIndicator(),SearchController::loadIndicators,engine);
    }

    public static ModelAndView load (Request request, Response response){
        if (!SessionHelper.existSession(request)){
            response.redirect(PathConfiguration.loginPath());
        }
        updateSearchModel(request);
        model.setCompanies(dao.getCompanies());
        model.setCompanyTables(dao.searchCompanies(model.getSearchModel()));
        model.setIndicatorResults(idao.searchIndicator(model.getSearchModel()));
        model.setMethodologies(mdao.getMethodologies(SessionHelper.getUser(request)));
        model.setShowModal(false);
        return new ModelAndView(model,HTML);
    }

    public static ModelAndView loadIndicators (Request request, Response response){
        try {
            Methodology m = mdao.getMethodology(Integer.parseInt(request.queryParams("met")));
            model.setMethodologiesResult(m.run(model.getSearchModel()));
            model.setShowModal(true);
        }catch (NoMethodologyResultsException ex){
            model.setMethodologiesResult(null);
            model.setShowModal(false);
        }

        return new ModelAndView(model,HTML);
    }

    private static SearchModel parseRequest(Request request){
        Company company = null;

        if (request.queryParams("companies") != null){
            company = dao.getCompany(Integer.parseInt(request.queryParams("companies")));
        }

        return new SearchModel(SessionHelper.getUser(request),
                company,
                DateUtils.getDateFromStringWeb(request.queryParams("dateFrom")),
                DateUtils.getDateFromStringWeb(request.queryParams("dateUpto")));
    }

    private static void updateSearchModel(Request request){
        if (request.queryParams().size()>0){
            model.setSearchModel(parseRequest(request));
        }else{
            model.setSearchModel(new SearchModel(SessionHelper.getUser(request),null,null,null));
        }
    }


}
