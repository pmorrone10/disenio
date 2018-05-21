package ar.edu.utn.frba.dds.utils;

import java.util.Properties;

/**
 * Created by TATIANA on 26/8/2017.
 */
public class ConfigurationUtils {
    static Properties properties = null;

    public static String getConfiguration(String label) {
        if (properties == null) {
            try {
                properties = new Properties();
                properties.load(ConfigurationUtils.class.getClassLoader().getResourceAsStream("config.properties"));
            }
            catch (Exception e){ properties = null; return "";}
        }

        return properties.getProperty(label);
    }
}
