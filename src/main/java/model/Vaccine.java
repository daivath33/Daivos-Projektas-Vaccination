package model;

import javax.persistence.*;

@Entity
public class Vaccine {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;
    private String name;
    private boolean used;
    @Column(name = "date_of_vaccination")
    private String usedDate;
    @ManyToOne
    private Manufacturer manufacturer;
    @ManyToOne
    private Person person;

    public Long getId() {
        return Id;
    }

    public void setId(Long id) {
        Id = id;
    }

    public String getVaccineName() {
        return name;
    }

    public void setVaccineName(String vaccineName) {
        this.name = vaccineName;
    }

    public String getUseDate() {
        return usedDate;
    }

    public void setUseDate(String useDate) {
        this.usedDate = useDate;
    }

    public boolean isUsed() {
        return used;
    }

    public void setUsed(boolean used) {
        used = used;
    }

    public Manufacturer getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(Manufacturer manufacturer) {
        this.manufacturer = manufacturer;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    @Override
    public String toString() {
        return Id + " " + usedDate + " " + name + " " + manufacturer;
    }
}
