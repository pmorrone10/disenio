package ar.edu.utn.frba.dds.viewcontrollers;

import ar.edu.utn.frba.dds.LoginManager;
import ar.edu.utn.frba.dds.Session;
import ar.edu.utn.frba.dds.dao.UsersDao;
import ar.edu.utn.frba.dds.exception.LoginException;
import ar.edu.utn.frba.dds.models.users.User;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by pablomorrone on 5/10/17.
 */
public class LoginViewController implements Initializable {

    @FXML
    private TextField txtUser;

    @FXML
    private PasswordField txtPass;

    @FXML
    private Button loginButton;

    @FXML
    private Label errorMessage;

    private User user;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }

    public void initManager(final LoginManager loginManager){
        this.user = new User();
        loginButton.setOnAction(event -> login(loginManager));
    }

    private void authorize(User user) {
        UsersDao dao = new UsersDao();
        User u = dao.exist(user);
        if (u != null){
            Session.getSession().setUser(u);
        }
    }

    public void login(final LoginManager loginManager){
        user.setUsername(txtUser.getText());
        user.setPassword(txtPass.getText());
        try {
            authorize(user);
            if (Session.getSession().getUser() != null) {
                loginManager.authenticated();
            } else {
                throw new LoginException();
            }
        }catch (LoginException ex){
            showErrorMessage();
        }catch (Exception ex){
            AlertFactory.errorAlert("","upps! hubo un error intente nuevamente.").show();
        }

    }
    private void showErrorMessage(){
        errorMessage.setText("El usuario y/o password son incorrectos");
        errorMessage.setVisible(true);
    }

}
