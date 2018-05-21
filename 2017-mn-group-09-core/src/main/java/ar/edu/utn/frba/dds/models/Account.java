package ar.edu.utn.frba.dds.models;


import ar.edu.utn.frba.dds.dao.CompaniesDao;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;

/**
 * Created by pablomorrone on 11/4/17.
 */
@Entity
@Table(name = "accounts")
public class Account implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "account_names_id",unique = true)
    private AccountName accountName;

    @Column(name = "value",nullable = false)
    private Double value;

    @Column(name = "timestamp",nullable = false)
    private LocalDate timestamp;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "companies_id")
    private Company company;

    public Account( String name, Double value, LocalDate timestamp){
        this.setName(name);
        this.value = value;
        this.timestamp = timestamp;
    }

    public Account() {
    }

    public void setName(String name) {
        this.accountName = new CompaniesDao().addAccountNameIfDontExist(name);
    }

    public String getName(){
        return this.accountName.getName();
    }
    public Double getValue() {
        return value;
    }

    public void setValue(Double value) {
        this.value = value;
    }

    public LocalDate getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDate timestamp) {
        this.timestamp = timestamp;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Account{" +
                "id=" + id +
                ", name='" + accountName + '\'' +
                ", value=" + value +
                ", timestamp=" + timestamp +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Account account = (Account) o;

        if (accountName != null ? !accountName.equals(account.accountName) : account.accountName != null) return false;
        if (value != null ? !value.equals(account.value) : account.value != null) return false;
        return timestamp != null ? timestamp.equals(account.timestamp) : account.timestamp == null;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (accountName != null ? accountName.hashCode() : 0);
        result = 31 * result + (value != null ? value.hashCode() : 0);
        result = 31 * result + (timestamp != null ? timestamp.hashCode() : 0);
        return result;
    }

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    public void setAccountName(AccountName a){
        this.accountName = a;
    }

    public AccountName getAccountName(){
        return this.accountName;
    }
}
