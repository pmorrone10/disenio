package ar.edu.utn.frba.dds.controllers;

import ar.edu.utn.frba.dds.models.AlertModel;
import ar.edu.utn.frba.dds.configuration.PathConfiguration;
import ar.edu.utn.frba.dds.exception.InvalidFileFormatException;
import ar.edu.utn.frba.dds.exception.ParserErrorException;
import ar.edu.utn.frba.dds.helper.AlertHelper;
import ar.edu.utn.frba.dds.helper.SessionHelper;
import ar.edu.utn.frba.dds.utils.FileUtils;
import spark.ModelAndView;
import spark.Request;
import spark.Response;
import spark.Spark;
import spark.template.handlebars.HandlebarsTemplateEngine;
import javax.servlet.MultipartConfigElement;
import javax.servlet.ServletException;
import java.io.File;
import java.io.IOException;

/**
 * Created by TATIANA on 10/10/2017.
 */
public class UploadController {

    public static final String HTML = "/pages/uploadFile.html";
    private static MultipartConfigElement multipartConfigElement;


    public static void init(){
        HandlebarsTemplateEngine engine = new HandlebarsTemplateEngine();
        setupMultipleElementConfig();
        Spark.get(PathConfiguration.uploadPath(),UploadController::load,engine);
        Spark.post(PathConfiguration.uploadPath(),UploadController::upload,engine);
    }

    public static ModelAndView load (Request request, Response response){
        if (!SessionHelper.existSession(request)){
            response.redirect(PathConfiguration.loginPath());
        }
        return new ModelAndView(AlertHelper.none(),HTML);
    }

    public static ModelAndView upload (Request request, Response response){
        AutomaticLoader c = new AutomaticLoader();
        AlertModel alertModel;
        try {
            request.raw().setAttribute("org.eclipse.jetty.multipartConfig",multipartConfigElement);
            File f = FileUtils.getFileWithPath(request.raw().getPart("file"));
            c.LoadCompaniesFromFile(f.getAbsolutePath());
            f.delete();
            alertModel = AlertHelper.success();
        }catch (InvalidFileFormatException ex){
            alertModel = AlertHelper.failed(ex.getMessage());
        }catch (ParserErrorException e){
            alertModel = AlertHelper.failed();
        } catch (IOException e) {
            alertModel = AlertHelper.failed();
        }catch (ServletException e){
            alertModel = AlertHelper.failed();
        }
        return new ModelAndView(alertModel,HTML);
    }

    private static void setupMultipleElementConfig(){
        multipartConfigElement = new MultipartConfigElement(
                "/templates",100000000 , 100000000, 1024);
    }
}