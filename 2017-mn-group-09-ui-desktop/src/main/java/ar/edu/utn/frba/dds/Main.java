package ar.edu.utn.frba.dds;

import ar.edu.utn.frba.dds.configuration.EnviromentConfigurationHelper;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

/**
 * Created by pablomorrone on 4/10/17.
 */
public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        EnviromentConfigurationHelper.setEnviromentApp();
        Scene scene = new Scene(new StackPane());
        LoginManager loginManager = new LoginManager(scene);
        loginManager.showLoginScreen();
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
