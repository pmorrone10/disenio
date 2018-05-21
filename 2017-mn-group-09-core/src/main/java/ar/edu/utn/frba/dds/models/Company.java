package ar.edu.utn.frba.dds.models;

import ar.edu.utn.frba.dds.utils.DateUtils;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by pablomorrone on 11/4/17.
 */
@Entity
@Table(name = "companies")
public class Company implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Column(name = "name",nullable = false)
    private String name;

    @Column(name = "creation_date", nullable = false)
    private LocalDate creationDate;

    @OneToMany (cascade = {CascadeType.ALL}, mappedBy = "company", fetch = FetchType.EAGER)
    private List<Account> accounts;

    public Company(String name, LocalDate creationDate){
        this.name = name;
        this.creationDate = creationDate;
        this.accounts = new ArrayList<>();
    }

    public Company(String name, LocalDate creationDate, List<Account> listAccounts){
        this.name = name;
        this.creationDate = creationDate;
        this.accounts = listAccounts;
    }

    //TODO:chequear esto que quedo medio feo.
    public void addAccount(Account account) {
        if (!accounts.contains(account)){
            accounts.add(account);
            account.setCompany(this);
        }
    }

    public void removeAccount(Account account) {
        accounts.remove(account);
        account.setCompany(null);
    }

    public Company() {
        this.accounts = new ArrayList<>();
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public void addAccount(String cuenta, Double valor, LocalDate fecha){
        addAccount(new Account(cuenta,valor,fecha));
    }

    public List<Account> getAccounts(){
        return this.accounts;
    }

    public List<Account> searchAccount(String name, LocalDate from, LocalDate to){
        return accounts.stream().filter(account -> name.equals(account.getName()) && DateUtils.isBetween(from, to, account.getTimestamp())).collect(Collectors.toList());
    }

    public String toString(){
        return "Company: " + getName()
                + " Fecha de Creacion: " + getCreationDate().toString()
                + getAccounts().toString();
    }

    public Account getFirtAccount(){
        return accounts.stream().findFirst().orElse(null);
    }

    public LocalDate getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDate creationDate) {
        this.creationDate = creationDate;
    }

    public int getId() {
        return id;
    }

    @Override
    public int hashCode() {
        int result = name.hashCode();
        result = 31 * result + creationDate.hashCode();
        result = 31 * result + accounts.hashCode();
        return result;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Company)) return false;

        Company company = (Company) o;

        if (!getName().equals(company.getName())) return false;
        if (!getCreationDate().equals(company.getCreationDate())) return false;
        return getAccounts().containsAll(company.getAccounts());
    }
}
