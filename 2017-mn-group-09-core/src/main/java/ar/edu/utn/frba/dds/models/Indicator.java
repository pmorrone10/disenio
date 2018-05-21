package ar.edu.utn.frba.dds.models;

import ar.edu.utn.frba.dds.models.indicator.IndicatorCache;
import ar.edu.utn.frba.dds.models.indicator.IndicatorExpression;
import ar.edu.utn.frba.dds.indicators.IndicatorParser;
import ar.edu.utn.frba.dds.models.indicator.IndicatorResult;
import ar.edu.utn.frba.dds.models.users.User;
import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

/**
 * Created by TATIANA on 12/5/2017.
 */
@Entity
@Table(name = "indicators")
public class Indicator implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Column(name = "name")
    private String name;

    @Column(name = "expression")
    private String expression;

    @ManyToOne(fetch = FetchType.EAGER,cascade = CascadeType.PERSIST)
    @JoinColumn(name = "users_id")
    private User user;

    private transient IndicatorExpression tree = null;

    public Indicator(User user, String name, String expression) {
        this.name = name;
        this.expression = expression;
        this.user = user;
        IndicatorParser parser = new IndicatorParser();
        this.tree = parser.analize(expression);
    }

    public Indicator() {

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getExpression() {
        if (tree == null) {
            IndicatorParser parser = new IndicatorParser();
            this.tree = parser.analize(expression);
        }
        return expression;
    }

    public IndicatorExpression getTree() {
        if (tree == null) {
            IndicatorParser parser = new IndicatorParser();
            this.tree = parser.analize(expression);
        }
        return tree;
    }

    public void setExpression(String expression) {
        this.expression = expression;
    }

    @Override
    public String toString(){
        return "Indicador: " + name
                + " expresion: " + expression;
    }

    public List<IndicatorResult> eval(Company company, LocalDate from, LocalDate to) {
        List<IndicatorResult> ret = IndicatorCache.getResults(this.getName(), company, from, to);

        if (ret.size() == 0) {
            try {
                ret = getTree().calc(company, from, to);
                for (IndicatorResult r: ret) {
                    IndicatorCache.setResult(this.getName(), company, r.getDate(), r.getValue());
                }
            } catch (Exception e) {
                ;
            }
        }
        return ret;
    }

    @Override
    public boolean equals(Object object){
        if (object == null) return false;
        Indicator m = (Indicator) object;
        return (this.getName().equals(m.getName())
                && this.getExpression().equals(m.getExpression()));
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
