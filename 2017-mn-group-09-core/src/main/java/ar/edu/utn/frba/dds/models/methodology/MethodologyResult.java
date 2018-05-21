package ar.edu.utn.frba.dds.models.methodology;

import ar.edu.utn.frba.dds.models.Company;

import java.time.LocalDate;

/**
 * Created by TATIANA on 12/6/2017.
 */
public class MethodologyResult {
    private Company company;
    private LocalDate from;
    private LocalDate to;
    private Double result;

    public MethodologyResult(Company company, LocalDate from, LocalDate to, Double result) {
        this.company = company;
        this.from = from;
        this.to = to;
        this.result = result;
    }

    public Double getResult() {
        return result;
    }

    public Company getCompany() { return company; }

    public LocalDate getFrom() {
        return from;
    }

    public LocalDate getTo() {
        return to;
    }
}
