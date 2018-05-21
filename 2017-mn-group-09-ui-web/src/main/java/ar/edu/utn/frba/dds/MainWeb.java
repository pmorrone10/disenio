package ar.edu.utn.frba.dds;

import ar.edu.utn.frba.dds.configuration.EnviromentConfigurationHelper;
import ar.edu.utn.frba.dds.configuration.PathConfiguration;
import ar.edu.utn.frba.dds.controllers.*;
import ar.edu.utn.frba.dds.utils.ConfigurationUtils;
import spark.Spark;
import spark.debug.DebugScreen;

/**
 * Created by pablomorrone on 4/10/17.
 */
public class MainWeb {

    public static void main(String[] args){
        EnviromentConfigurationHelper.setEnviromentApp();

        Spark.port(8083);
        DebugScreen.enableDebugScreen();
        Spark.staticFiles.location(ConfigurationUtils.getConfiguration("ar.edu.utn.frba.dds.controllers.baseController.location"));

        LoginController.init();
        LogoutController.init();
        HomeController.init();
        MethodologyController.init();
        SearchController.init();
        IndicatorController.init();
        UploadController.init();

        Spark.get("/",((request, response) -> { response.redirect(PathConfiguration.loginPath());
            return null;}));
    }
}
