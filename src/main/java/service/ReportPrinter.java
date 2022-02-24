package service;

import model.Person;
import model.Vaccine;

public class ReportPrinter {
    public static void PersonReportPrinter(Person person){
        System.out.printf("ID=%d %s %s %d \n",
                person.getId(),
                person.getFirstName(),
                person.getLastName(),
                person.getAge());
    }
    public static void VaccineDetailedReportPrinter(Vaccine vaccine){
        System.out.printf("ID=%d %s %s %s \n",
                vaccine.getId(),
                vaccine.getVaccineName(),
                vaccine.getManufacturer().getCountry(),
                vaccine.getUseDate());
    }
    public static void VaccineBasicReportPrinter(Vaccine vaccine){
        System.out.printf("ID=%d %s %s %s \n",
                vaccine.getId(),
                vaccine.getVaccineName(),
                vaccine.getManufacturer().getName(),
                vaccine.getManufacturer().getCountry() );
    }
}
