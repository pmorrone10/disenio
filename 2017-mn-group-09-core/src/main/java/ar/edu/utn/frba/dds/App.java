package ar.edu.utn.frba.dds;

import ar.edu.utn.frba.dds.dao.MethodologiesDao;
import ar.edu.utn.frba.dds.models.methodology.Methodology;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        MethodologiesDao dao = new MethodologiesDao();
        Methodology m = dao.findMethodology("sarasa");
        if (m != null){
            System.out.print("existe");
        }else{
            System.out.print("No existe");
        }
    }
}
