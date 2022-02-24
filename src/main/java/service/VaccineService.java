package service;

import model.Vaccine;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class VaccineService {
    private final SessionFactory sessionFactory;
    private final Scanner scanner = new Scanner(System.in);

    public VaccineService(SessionFactory connection) {
        this.sessionFactory = connection;
    }

    public void saveVaccine(Vaccine vaccine) {
        Session session = sessionFactory.openSession();
        session.save(vaccine);
        session.close();
    }

    public void showUnusedVaccines() {
        List<Vaccine> unusedVaccines = getUnusedVaccines();
        unusedVaccines.stream().limit(3).collect(Collectors.toList()).forEach(ReportPrinter::VaccineBasicReportPrinter);
        char choice1 = ChoicesPrinter.unusedVaccinesChoices();
        int i = 3;
        do {
            List<Vaccine> tempList = unusedVaccines.stream().skip(i).limit(3).collect(Collectors.toList());
            tempList.forEach(ReportPrinter::VaccineBasicReportPrinter);
            choice1 = ChoicesPrinter.unusedVaccinesChoices();
            i = i + 3;
        }
        while (choice1 != 's');
    }

    public List<Vaccine> getUnusedVaccines() {
        Session session = sessionFactory.openSession();
        return session.createQuery("SELECT v FROM Vaccine v WHERE v.used = false", Vaccine.class).list();
    }

    public void showUnusedVaccinesByName() {
        System.out.println("Įveskite vakcinos pavadinimą:");
        String name = scanner.next();
        getUnusedVaccinesByName(name).forEach(ReportPrinter::VaccineBasicReportPrinter);
    }

    public List<Vaccine> getUnusedVaccinesByName(String name) {
        Session session = sessionFactory.openSession();
        return session.createQuery("SELECT v FROM Vaccine v WHERE v.name =:name AND v.used = false", Vaccine.class)
                .setParameter("name", name)
                .list();
    }

    public void getUnusedVaccinesCount() {
        Session session = sessionFactory.openSession();
        var query = session.createQuery("SELECT count(*) FROM Vaccine WHERE used = false");
        System.out.println("Nepanaudotų vakcinų sakičius: " + query.getSingleResult() + " vnt.");
    }

    public void showVaccinesByManufacturerId() {
        System.out.println("Įveskite gamintojo ID:");
        Long id = scanner.nextLong();
        getVaccinesByManufacturerId(id).forEach(ReportPrinter::VaccineBasicReportPrinter);
    }

    public List<Vaccine> getVaccinesByManufacturerId(Long id) {
        Session session = sessionFactory.openSession();
        return session.createQuery("SELECT v FROM Vaccine v WHERE manufacturer_id =:id",
                        Vaccine.class)
                .setParameter("id", id)
                .list();
    }

    public boolean isVaccineIdValid(Long vaccineId) {
        if (unusedVaccinesId().contains(vaccineId)) {
            return true;
        } else {
            System.out.println("VAKCINACIJA NEGALIMA!");
            System.out.println("Vakcinos nėra sąraše, peržiūrėkite nepanaudotų vakcinų sąrašą");
            return false;
        }
    }

    public List<Long> unusedVaccinesId() {
        Session session = sessionFactory.openSession();
        var query = session.createQuery("SELECT v.id FROM Vaccine v WHERE v.used = false").list();
        return query;
    }
}
