package service;

import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class ChoicesPrinter {
    private static final Scanner SCANNER = new Scanner(System.in);
    private static final List<Integer> MAIN_CHOICES = Arrays.asList(0, 1, 2, 3, 4, 5, 6, 7, 8, 9);
    private static final List<Character> UNUSED_VACCINES_CHOICES = Arrays.asList('n', 's');
    private static final List<Integer> NUMBER_OF_VACCINES = Arrays.asList(1, 2, 3);

    public static int mainChoices() {
        System.out.println("Pasirinkite ką norėtumėte atlikti:\n" +
                "0 - Uždaryti programą\n" +
                "1 - Užregistruoti pacientą\n" +
                "2 - Parodyti visas nepanaudotas vakcinas\n" +
                "3 - Užregistruoti kad pacientas gavo skiepą\n" +
                "4 - Parodyti visus pacientus kurie nėra gavę skiepo\n" +
                "5 - Surasti visus žmones pagal skiepų skaičių\n" +
                "6 - Parodyti paciento skiepų istoriją\n" +
                "7 - Parodyti nepanaudotas vakcinas pagal pavadinimą\n" +
                "8 - Parodyti visas vakcinas pagal gamintoją\n" +
                "9 - Parodyti kiek yra nepanaudotų vakcinų");

        try {
            int choice = SCANNER.nextInt();
            if (MAIN_CHOICES.contains(choice)) {
                return choice;
            }
        } catch (Exception e) {
            System.out.println("Neteisingai įvestas pasirinkimas, bandykite dar kartą...");
        }
        return mainChoices();
    }

    public static char unusedVaccinesChoices() {
        System.out.println("Pasirinkite:\n" +
                "n - rodyti sekančias tris nepanaudotas vakcinas\n" +
                "s - baigti vakcinų rodymą");
        try {
            char choice = SCANNER.next().charAt(0);
            if (UNUSED_VACCINES_CHOICES.contains(choice)) {
                return choice;
            }
        } catch (Exception e) {
            System.out.println("Neteisingai įvestas pasirinkimas, bandykite dar kartą...");
        }
        return unusedVaccinesChoices();
    }

    public static int getNumberOfVaccines() {
        System.out.println("Pasirinkite skiepų skaičių:\n" +
                "1 - vieną kartą paskiepyti pacientai\n" +
                "2 - du kartus paskiepyti pacientai\n" +
                "3 - tris kartus paskiepyti pacientai");
        try {
            int numberOfVaccines = SCANNER.nextInt();
            if (NUMBER_OF_VACCINES.contains(numberOfVaccines)) {
                return numberOfVaccines;
            }
        } catch (Exception e) {
            System.out.println("Neteisingai įvestas pasirinkimas, bandykite dar kartą...");
        }
        return getNumberOfVaccines();
    }
}
