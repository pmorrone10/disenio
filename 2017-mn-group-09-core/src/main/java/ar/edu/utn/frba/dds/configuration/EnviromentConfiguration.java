package ar.edu.utn.frba.dds.configuration;

/**
 * Created by pablomorrone on 20/9/17.
 */
public class EnviromentConfiguration {

    private static EnviromentConfiguration instance = new EnviromentConfiguration();

    private EnviromentType env;

    public static EnviromentConfiguration getInstance() {
        return instance;
    }

    private EnviromentConfiguration() {
    }

    public EnviromentType getEnv() {
        return env;
    }

    public void setEnv(EnviromentType env) {
        this.env = env;
    }

}
