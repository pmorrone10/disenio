package ar.edu.utn.frba.dds.models;

import ar.edu.utn.frba.dds.dao.CompaniesDao;
import ar.edu.utn.frba.dds.exception.InvalidRangeDateException;
import ar.edu.utn.frba.dds.models.users.User;
import ar.edu.utn.frba.dds.utils.DateUtils;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by pablomorrone on 1/8/17.
 */
public class SearchModel {

    /** Esta clase tiene el modelo de los datos de busqueda de la pantalla SearchViewController*/

    private List<Company> companies;
    private LocalDate from;
    private LocalDate to;
    private CompaniesDao dao;
    private User user;

    public SearchModel(User user,Company company, LocalDate from, LocalDate to){
        this.companies = new ArrayList<>();
        this.dao = new CompaniesDao();
        setCompany(company);
        setFrom(from);
        setTo(to);
        setUser(user);
    }

    private void setCompany(Company company){
        if(company == null){
            this.companies.addAll(dao.getCompanies());
        }else {
            this.companies.add(company);
        }
    }

    private LocalDate getDate(LocalDate date,LocalDate aux){
        return date == null ? aux : date;
    }

    public void setFrom(LocalDate from){
        this.from = getDate(from, DateUtils.firstDate());
    }

    public void setTo(LocalDate to){
        this.to = getDate(to,LocalDate.now());
    }

    public List<Company> getCompanies(){
        return this.companies;
    }

    public LocalDate getFrom(){
        return this.from;
    }

    public LocalDate getTo(){
        return this.to;
    }

    public void validateDates(){
        if (to.isBefore(from)) {
            throw new InvalidRangeDateException("Rango de fechas incorrecto.");
        }
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
