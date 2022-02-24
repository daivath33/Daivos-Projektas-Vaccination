package service;

import model.Manufacturer;
import model.Vaccine;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

public class DataReaderAndLoader {

    public static List<Manufacturer> loadManufacturers(String file) {
        try {
            return Files.lines(Paths.get(file))
                    .map(DataReaderAndLoader::createManufacturerFromFile)
                    .collect(Collectors.toList());
        } catch (IOException e) {
            System.out.println("Neteisingas failo url");
            return Collections.emptyList();
        }
    }

    private static Manufacturer createManufacturerFromFile(String line) {
        String[] data = line.split(",");
        Manufacturer manufacturer = new Manufacturer();
        manufacturer.setName(data[0]);
        manufacturer.setCountry(data[1]);
        return manufacturer;
    }

    public static List<Vaccine> loadVaccinesFromFile(String file, Function<Long, Manufacturer> getManufacturerById) {
        try {
            return Files.lines(Paths.get(file))
                    .map(line -> createVaccineFromFile(line, getManufacturerById))
                    .collect(Collectors.toList());
        } catch (IOException e) {
            System.out.println("Neteisingas failo url");
            return Collections.emptyList();
        }
    }

    private static Vaccine createVaccineFromFile(String line, Function<Long, Manufacturer> getManufacturerById) {
        String[] data = line.split(",");
        Vaccine vaccine = new Vaccine();
        vaccine.setVaccineName(data[0]);
        String idAsString = data[1];
        Manufacturer manufacturer = getManufacturerById.apply(Long.parseLong(idAsString));
        vaccine.setManufacturer(manufacturer);
        return vaccine;
    }
}
