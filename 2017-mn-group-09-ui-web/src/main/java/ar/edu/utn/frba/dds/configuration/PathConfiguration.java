package ar.edu.utn.frba.dds.configuration;

/**
 * Created by pablomorrone on 3/10/17.
 */
public class PathConfiguration {

    private static final String LOGIN = "/login";
    private static final String HOME = "/home";
    private static final String LOGOUT = "/logout";
    private static final String INDICATORS = "/indicators";
    private static final String DELETEINDICATORS = "/indicators/:id";
    private static final String METHODOLOGIES = "/methodologies";
    private static final String DELETEMETHODOLOGIES = "/methodologies/:id";
    private static final String SEARCH = "/search";
    private static final String SEARCHINDICATOR = "/searchMethodologies";
    private static final String UPLOAD = "/upload";


    public static String loginPath(){
        return LOGIN;
    }

    public static String homePath(){
        return HOME;
    }

    public static String logoutPath(){
        return LOGOUT;
    }

    public static String indicatorsPath(){
        return INDICATORS;
    }

    public static String deleteindicatorsPath(){
        return DELETEINDICATORS;
    }

    public static String methodologiesPath(){
        return METHODOLOGIES;
    }

    public static String deleteMethodologiesPath(){return DELETEMETHODOLOGIES;}

    public static String searchPath(){
        return SEARCH;
    }

    public static String searchIndicator(){
        return SEARCHINDICATOR;
    }

    public static String uploadPath(){
        return UPLOAD;
    }
}
