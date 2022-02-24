package service;

import model.Person;
import model.Vaccine;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.List;
import java.util.Scanner;

public class PersonService {
    private final SessionFactory sessionFactory;
    private final Scanner scanner = new Scanner(System.in);

    public PersonService(SessionFactory connection) {
        this.sessionFactory = connection;
    }

    public void savePersonIntoDataBase(Person person) {
        Session session = sessionFactory.openSession();
        session.save(person);
        session.close();
    }

    public void registerPerson() {
        System.out.println("Įveskite asmens vardą:");
        String firstName = scanner.next();
        System.out.println("Įveskite asmens pavardę:");
        String lastName = scanner.next();
        System.out.println("Įveskite asmens amžių:");
        int age = scanner.nextInt();
        Person person = new Person();
        person.setFirstName(firstName);
        person.setLastName(lastName);
        person.setAge(age);

        if (isPersonValid(person)) {
            savePersonIntoDataBase(person);
            System.out.println("Asmuo sėkmingai užregistruotas vakcinacijai");
        }
    }

    private boolean isPersonValid(Person person) {
        if (person.getFirstName().matches(".*[0-9].*")) {
            System.out.println("Vardas negali tureti skaičiaus.");
            return false;
        }
        if (person.getLastName().matches(".*[0-9].*")) {
            System.out.println("Pavardė negali tureti skiačiaus");
            return false;
        }
        if (person.getAge() < 14) {
            System.out.println("Paciento amžius privalo būti daugiau nei 14 metų");
            return false;
        }
        return true;
    }

    public Person findById(Long id) {
        Session session = this.sessionFactory.openSession();
        return session.find(Person.class, id);
    }

    public void vaccinatePerson(long vaccineId, long personId) {
        LocalDateTime localDateTime = LocalDateTime.now();
        String formattedLocalDataTime = localDateTime.format(DateTimeFormatter.ofLocalizedDateTime(FormatStyle.SHORT));
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        Query query = session.createQuery("UPDATE Vaccine set used =:used, date_of_vaccination =:usedDate," +
                " person_id =:personId WHERE id =:id");
        query.setParameter("used", true);
        query.setParameter("usedDate", formattedLocalDataTime);
        query.setParameter("personId", findById(personId));
        query.setParameter("id", vaccineId);
        query.executeUpdate();
        session.close();
    }

    public boolean isPersonIdValid(Long personId) {
        if (getRegistratedPersonsId().contains(personId)) {
            return true;
        } else {
            System.out.println("VAKCINACIJA NEGALIMA!");
            System.out.println("Asmuo nėra registruotas vakcinacijai");
            return false;
        }
    }

    public List<Long> getRegistratedPersonsId() {
        Session session = sessionFactory.openSession();
        var list = session.createQuery("SELECT p.id FROM Person p").list();
        return list;
    }

//    public void getAllNotVaccinatedPersons() {
//        Session session = sessionFactory.openSession();
//        showVaccinatePersonsByNumberOfVaccines(0);
////        var list = session.createQuery("SELECT p FROM Person p WHERE p.vaccines.size =:count", Person.class)
////                .setParameter("count", 0)
////                .list();
//        list.forEach(ReportPrinter::PersonReportPrinter);
//    }

    public void findByVaccineCount(int numberOfVaccination) {
        Session session = sessionFactory.openSession();
        var list = session.createQuery("SELECT p FROM Person p WHERE p.vaccines.size =:count", Person.class)
                .setParameter("count", numberOfVaccination)
                .list();
        list.forEach(ReportPrinter::PersonReportPrinter);
    }
    public List<Person> showPersonById(Long personId){
        Session session = sessionFactory.openSession();
        var person = session.createQuery("SELECT p FROM Person p WHERE id =:personId", Person.class)
                .setParameter("personId", personId).list();
        return person;
    }
    public void showPersonVaccineHistory() {
        System.out.println("Įveskite paciento ID:");
        long personId = scanner.nextLong();
        if (isPersonIdValid(personId)) {
            var list = showVaccineByPersonId(personId);
           showPersonById(personId).forEach(System.out::println);
            list.forEach(ReportPrinter::VaccineDetailedReportPrinter);
        }
    }

    public List<Vaccine> showVaccineByPersonId(long personId) {
        Session session = sessionFactory.openSession();
        return session.createQuery("SELECT v FROM Vaccine v WHERE v.person.id =:personId " +
                        "ORDER BY date_of_vaccination DESC", Vaccine.class)
                .setParameter("personId", personId)
                .list();
    }
}
