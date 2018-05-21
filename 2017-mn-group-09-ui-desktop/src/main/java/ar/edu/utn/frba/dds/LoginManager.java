package ar.edu.utn.frba.dds;

import ar.edu.utn.frba.dds.viewcontrollers.LoginViewController;
import ar.edu.utn.frba.dds.viewcontrollers.MainViewController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import java.io.IOException;

/**
 * Created by pablomorrone on 5/10/17.
 */
public class LoginManager {

    private Scene scene;

    public LoginManager(Scene scene) {
        this.scene = scene;
    }

    public void authenticated() {
        showMainView();
    }

    public void showLoginScreen() {
        try {
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("/views/login.fxml")
            );
            scene.setRoot((Parent) loader.load());
            LoginViewController controller =
                    loader.<LoginViewController>getController();
            controller.initManager(this);
        } catch (IOException ex) {
            //Logger.getLogger(LoginManager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void showMainView() {
        try {
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("/views/main.fxml")
            );
            scene.setRoot((Parent) loader.load());
            MainViewController controller =
                    loader.<MainViewController>getController();
            controller.initSessionID(this);
        } catch (IOException ex) {
            //Logger.getLogger(LoginManager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
