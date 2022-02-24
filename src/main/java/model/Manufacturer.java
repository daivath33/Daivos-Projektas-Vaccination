package model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Manufacturer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String country;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "manufacturer_id")
    private List<Vaccine> vaccines = new ArrayList<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public List<Vaccine> getVaccineList() {
        return vaccines;
    }

    public void setVaccineList(List<Vaccine> vaccineList) {
        this.vaccines = vaccineList;
    }

    @Override
    public String toString() {
        return name + " " + country;
    }
}
