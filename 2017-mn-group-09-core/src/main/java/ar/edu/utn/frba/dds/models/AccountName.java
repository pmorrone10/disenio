package ar.edu.utn.frba.dds.models;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by pablomorrone on 29/8/17.
 */
@Entity
@Table(name = "account_names")
public class AccountName implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Column(name = "name")
    private String name;

    public AccountName(){}

    public AccountName(String name){
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AccountName)) return false;

        AccountName that = (AccountName) o;

        return getName() != null ? getName().equals(that.getName()) : that.getName() == null;
    }

    @Override
    public int hashCode() {
        return getName() != null ? getName().hashCode() : 0;
    }
}
