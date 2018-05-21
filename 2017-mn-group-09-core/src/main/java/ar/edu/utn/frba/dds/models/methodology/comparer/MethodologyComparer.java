package ar.edu.utn.frba.dds.models.methodology.comparer;

import ar.edu.utn.frba.dds.models.methodology.MethodologyResult;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Comparator;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * Created by TATIANA on 12/6/2017.
 */
@Entity
@Table(name = "methodology_comparators")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "dtype")
public abstract class MethodologyComparer implements Serializable{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id",unique = true,nullable = false)
    private int id;

    @Column(name = "name",nullable = false)
    private String name;

    @Transient
    private transient Double referenceValue;

    @Transient
    private String type = this.getClass().getName();

    public MethodologyComparer(){}

    public MethodologyComparer(Double referenceValue) {
        this.setReferenceValue(referenceValue);
    }

    public List<MethodologyResult> getResult(List<MethodologyResult> results) {
        results.sort(MethodologyResultComparator);
        return results.stream().filter(MethodologyResultPredicate).collect(Collectors.toList());
    }

    public abstract int sorter(MethodologyResult result1, MethodologyResult result2);

    public abstract boolean filter(MethodologyResult t);

    public transient Comparator<MethodologyResult> MethodologyResultComparator = new Comparator<MethodologyResult>() {
        public int compare(MethodologyResult result1, MethodologyResult result2) {
            return sorter(result1, result2);
        }
    };

    public transient Predicate<MethodologyResult> MethodologyResultPredicate = new Predicate<MethodologyResult>() {
        @Override
        public boolean test(MethodologyResult t) {
            return filter(t);
        }
    };

    public Double getReferenceValue() {
        return referenceValue;
    }

    public void setReferenceValue(Double referenceValue) {
        this.referenceValue = referenceValue;
    }

    @Override
    public boolean equals(Object obj){
        if (obj == null) return false;
        if (this.getReferenceValue() == null) {
            return (((MethodologyComparer)obj).getReferenceValue() == null);
        }
        return this.getReferenceValue().equals(((MethodologyComparer)obj).getReferenceValue());
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "MethodologyComparer{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", referenceValue=" + referenceValue +
                ", type='" + type + '\'' +
                '}';
    }

    public String getName(){
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public abstract void setName();
}
