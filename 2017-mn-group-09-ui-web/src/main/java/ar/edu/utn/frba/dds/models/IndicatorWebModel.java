package ar.edu.utn.frba.dds.models;

import java.util.List;

/**
 * Created by pablomorrone on 18/10/17.
 */
public class IndicatorWebModel {

    private List<Indicator> indicators;
    private AlertModel alert;

    public IndicatorWebModel() {
        this.alert = new AlertModel(false,"",false);
    }

    public List<Indicator> getIndicators() {
        return indicators;
    }

    public void setIndicators(List<Indicator> indicators) {
        this.indicators = indicators;
    }

    public AlertModel getAlert() {
        return alert;
    }

    public void setAlert(AlertModel alert) {
        this.alert = alert;
    }
}
