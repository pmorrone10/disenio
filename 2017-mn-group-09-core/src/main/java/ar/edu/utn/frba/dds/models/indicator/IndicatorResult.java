package ar.edu.utn.frba.dds.models.indicator;

import ar.edu.utn.frba.dds.models.Company;

import java.time.LocalDate;

/**
 * Created by TATIANA on 15/8/2017.
 */
public class IndicatorResult {
    private String name;
    private Company company;
    private LocalDate date;
    private Double value;

    public IndicatorResult(String name, Company company, LocalDate date, Double value) {
        this.name = name;
        this.company = company;
        this.date = date;
        this.value = value;
    }

    public Company getCompany() {
        return company;
    }

    public LocalDate getDate() {
        return date;
    }

    public Double getValue() {
        return value;
    }

    public String getName() {
        return name;
    }
}
