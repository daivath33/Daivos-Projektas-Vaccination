import configuration.HibernateConfiguration;
import model.Manufacturer;
import model.Vaccine;
import org.hibernate.SessionFactory;
import service.*;

import java.util.List;
import java.util.Scanner;

public class Main {
    private static final SessionFactory CONNECTION = HibernateConfiguration.getSessionFactory();
    private static final ManufacturerService MANUFACTURER_SERVICE = new ManufacturerService(CONNECTION);
    private static final VaccineService VACCINE_SERVICE = new VaccineService(CONNECTION);
    private static final PersonService PERSON_SERVICE = new PersonService(CONNECTION);
    private static final String MANUFACTURERS_FILE = "src/main/resources/manufacturers.csv";
    private static final String VACCINES_FILE = "src/main/resources/vaccines.csv";
    private static final Scanner SCANNER = new Scanner(System.in);

    public static void main(String[] args) {
        createDataBase();
        int choice = ChoicesPrinter.mainChoices();
        while (choice != 0) {
            switch (choice) {
                case 1:
                    PERSON_SERVICE.registerPerson();
                    break;
                case 2:
                    VACCINE_SERVICE.showUnusedVaccines();
                    break;
                case 3:
                    registerVaccination();
                    break;
                case 4:
                    PERSON_SERVICE.findByVaccineCount(0);
                    break;
                case 5:
                    PERSON_SERVICE.findByVaccineCount(ChoicesPrinter.getNumberOfVaccines());
                    break;
                case 6:
                    PERSON_SERVICE.showPersonVaccineHistory();
                    break;
                case 7:
                    VACCINE_SERVICE.showUnusedVaccinesByName();
                    break;
                case 8:
                    VACCINE_SERVICE.showVaccinesByManufacturerId();
                    break;
                case 9:
                    VACCINE_SERVICE.getUnusedVaccinesCount();
                    break;
            }
            choice = ChoicesPrinter.mainChoices();
        }
    }

    private static void registerVaccination() {
        System.out.println("Įveskite vakcinos ID:");
        long vaccineId = SCANNER.nextLong();
        System.out.println("Įveskite skiepijamo paciento ID:");
        long personId = SCANNER.nextLong();
        if (VACCINE_SERVICE.isVaccineIdValid(vaccineId) && PERSON_SERVICE.isPersonIdValid(personId)) {
            PERSON_SERVICE.vaccinatePerson(vaccineId, personId);
            System.out.println("Paciento vakcinacija sėkmingai užregistruota");
        }
    }

    private static void createDataBase() {
        saveManufacturersIntoDataBase();
        saveVaccinesIntoDataBase();
    }

    private static void saveManufacturersIntoDataBase() {
        List<Manufacturer> manufacturers = DataReaderAndLoader.loadManufacturers(MANUFACTURERS_FILE);
        manufacturers.forEach(MANUFACTURER_SERVICE::saveManufacturer);
    }

    private static void saveVaccinesIntoDataBase() {
        List<Vaccine> vaccines = DataReaderAndLoader.loadVaccinesFromFile(VACCINES_FILE, MANUFACTURER_SERVICE::findById);
        vaccines.forEach(VACCINE_SERVICE::saveVaccine);
    }
}
