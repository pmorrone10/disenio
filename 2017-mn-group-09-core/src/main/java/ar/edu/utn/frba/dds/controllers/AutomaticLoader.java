package ar.edu.utn.frba.dds.controllers;

import ar.edu.utn.frba.dds.dao.CompaniesDao;
import ar.edu.utn.frba.dds.models.Company;
import ar.edu.utn.frba.dds.exception.ParserErrorException;
import ar.edu.utn.frba.dds.utils.DateUtils;
import org.apache.log4j.Logger;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by pablomorrone on 12/4/17.
 */
public class AutomaticLoader {

    final static Logger logger = Logger.getLogger(AutomaticLoader.class);

    private CompaniesDao dao;

    public AutomaticLoader(){
        this.dao = new CompaniesDao();
    }

    public void LoadCompaniesFromFile(String path){
        List<String[]> lines = CsvReader.parse(path);
        List<Company> companies = new ArrayList<>();
        try {
            for (String[] line : lines){
                companies.add(parser(line));
            }
        }catch (ArrayIndexOutOfBoundsException ex){
            throw new ParserErrorException("No se pudo parsear el archivo");
        }
        dao.addCompaniesIfDontExist(companies);
    }

    private Company parser(String[] line){
        String companyName = line[0];
        LocalDate creationDate = DateUtils.getDateFromString(line[1]);
        String accountName = line[2];
        Double accountValue = Double.parseDouble(line[3]);
        LocalDate timestamp = DateUtils.getDateFromString(line[4]);
        Company e = new Company(companyName,creationDate);
        e.addAccount(accountName,accountValue,timestamp);
        return e;
    }
}
