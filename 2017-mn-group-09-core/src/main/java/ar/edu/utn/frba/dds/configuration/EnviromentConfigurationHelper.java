package ar.edu.utn.frba.dds.configuration;

/**
 * Created by pablomorrone on 20/9/17.
 */
public class EnviromentConfigurationHelper {

    public static void setEnviromentApp(){
        EnviromentConfiguration.getInstance().setEnv(EnviromentType.APP);
    }

    public static void setEnviromentTest(){
        EnviromentConfiguration.getInstance().setEnv(EnviromentType.TEST);
    }

    public static EnviromentType getEnv(){
        return EnviromentConfiguration.getInstance().getEnv();
    }
}
