package ar.edu.utn.frba.dds.models;

/**
 * Created by pablomorrone on 11/10/17.
 */
public class AlertModel {

    private Boolean isSuccess;
    private String message;
    private boolean showAlert;

    public AlertModel() {
    }

    public AlertModel(boolean isSuccess, String message, boolean showAlert) {
        this.isSuccess = isSuccess;
        this.message = message;
        this.showAlert = showAlert;
    }

    public Boolean getIsSuccess() {
        return isSuccess;
    }

    public void setIsSuccess(Boolean type) {
        this.isSuccess = type;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isShowAlert() {
        return showAlert;
    }

    public void setShowAlert(boolean showAlert) {
        this.showAlert = showAlert;
    }

    public void setShowAlertWithMessage(String message){
        setMessage(message);
        setShowAlert(true);
    }

    public void setHideAlert(){
        setShowAlert(false);
    }
}
