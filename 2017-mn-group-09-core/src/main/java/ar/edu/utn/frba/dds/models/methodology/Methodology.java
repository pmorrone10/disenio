package ar.edu.utn.frba.dds.models.methodology;

import ar.edu.utn.frba.dds.exception.NoMethodologyResultsException;
import ar.edu.utn.frba.dds.models.Company;
import ar.edu.utn.frba.dds.models.Indicator;
import ar.edu.utn.frba.dds.models.SearchModel;
import ar.edu.utn.frba.dds.models.users.User;
import ar.edu.utn.frba.dds.models.indicator.IndicatorResult;
import ar.edu.utn.frba.dds.models.methodology.comparer.MethodologyComparer;
import org.apache.log4j.Logger;
import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by pablomorrone on 13/6/17.
 */
@Entity
@Table(name = "methodologies")
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Methodology implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id",unique = true,nullable = false)
    private int id;

    @Column(name = "name")
    private String name;

    @ManyToOne(fetch = FetchType.EAGER,cascade = CascadeType.ALL)
    @JoinColumn(name = "indicators_id")
    private Indicator indicator;


    @ManyToOne(fetch = FetchType.EAGER,cascade = CascadeType.ALL)
    @JoinColumn(name = "methodology_comparators_id")
    private  MethodologyComparer comparer;


    @ManyToOne(fetch = FetchType.EAGER,cascade = CascadeType.PERSIST)
    @JoinColumn(name = "users_id")
    private  User user;

    @Transient
    private String type = this.getClass().getName();

    @Transient
    private MethodologyTypeEnum typeEnum;

    private transient List<MethodologyResult> results = new ArrayList<>();

    final static Logger logger = Logger.getLogger(Methodology.class);

    public Methodology(){}

    public Methodology(User user, String name, Indicator indicator, MethodologyComparer comparer){
        this.user = user;
        this.setName(name);
        this.setIndicator(indicator);
        this.setComparer(comparer);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<MethodologyResult> run(SearchModel model){
        return run(model.getCompanies(),model.getFrom(),model.getTo());
    }

    public List<MethodologyResult> run(List<Company> companies, LocalDate from, LocalDate to) {
        resolve(companies, from, to);
        if (getResults().size() == 0) throw new NoMethodologyResultsException();
        return getComparer().getResult(getResults());
    }

    private void resolve(List<Company> companies, LocalDate from, LocalDate to) {
        this.results = new ArrayList<>();
        for (Company c : companies){
            try {
                for (IndicatorResult r:getIndicator().eval(c, from, to)) {
                    this.results.add(new MethodologyResult(c, from, to, r.getValue()));
                }
            }
            catch(Exception e){
                logger.debug(e.getMessage());
            }
        }
    }

    public String getName() {
        return name;
    }

    public List<MethodologyResult> getResults() {
        return this.results;
    }

    @Override
    public boolean equals(Object object){
        if (object == null) return false;
        Methodology m = (Methodology) object;
        return (this.getName().equals(m.getName())
                && this.getIndicator().equals(m.getIndicator())
                && this.getComparer().equals(m.getComparer()));
    }

    public void setName(String name) {
        this.name = name;
    }

    public Indicator getIndicator() {
        return indicator;
    }

    public void setIndicator(Indicator indicator) {
        this.indicator = indicator;
    }

    public MethodologyComparer getComparer() {
        return comparer;
    }

    public void setComparer(MethodologyComparer comparer) {
        this.comparer = comparer;
    }

    public abstract String getSpecialValue();

    public abstract String getDescription();


}
