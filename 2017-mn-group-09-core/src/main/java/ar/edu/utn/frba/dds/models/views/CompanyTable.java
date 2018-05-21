package ar.edu.utn.frba.dds.models.views;

import ar.edu.utn.frba.dds.models.Account;
import ar.edu.utn.frba.dds.models.Company;
import ar.edu.utn.frba.dds.utils.DateUtils;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by pablomorrone on 11/4/17.
 */
public class CompanyTable {

    private SimpleStringProperty company;
    private SimpleStringProperty creationDate;
    private SimpleStringProperty account;
    private SimpleStringProperty value;
    private SimpleObjectProperty<LocalDate> timestamp;

    public CompanyTable(String name, LocalDate creationDate, Account c){
        this.company = new SimpleStringProperty(name);
        this.creationDate = new SimpleStringProperty(DateUtils.getStringFromDate(creationDate));
        this.account = new SimpleStringProperty(c.getName());
        this.timestamp = new SimpleObjectProperty<LocalDate>(c.getTimestamp());
        this.value = new SimpleStringProperty(c.getValue().toString());
    }

    public static List<CompanyTable> transform(List<Company> companies){
        List<CompanyTable> lst = new ArrayList<CompanyTable>();
        for (Company e: companies) {
            for (Account c: e.getAccounts()) {
                try {
                    lst.add(new CompanyTable(e.getName(),e.getCreationDate(),c));
                }catch (NullPointerException ex){
                    //no-ops
                }
            }
        }
        return lst;
    }

    public String getCompany() {
        return company.get();
    }

    public StringProperty companyProperty(){
        return company;
    }

    public String getCreationDate(){return creationDate.get();}

    public StringProperty creationDateProperty(){return creationDate;}

    public String getAccount() {
        return account.get();
    }

    public StringProperty accountProperty(){
        return account;
    }

    public LocalDate getTimestamp() {
        return timestamp.get();
    }

    public ObjectProperty<LocalDate> timestampProperty(){
        return timestamp;
    }

    public String getValue() {
        return value.get();
    }

    public StringProperty valueProperty(){
        return value;
    }

}
