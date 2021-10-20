package cz.marianjanik.ekurz;

import java.io.FileNotFoundException;
import java.util.*;

public class Main {

    public static void main(String[] args) {

        final int NUMBER_OF_COUNTRIES = 4;
        final String FILENAME = "TheBestAndWorstRates.txt";
        List ratesList;

        System.out.println("\n\n-------------------------------------------------1. Volání API pomocí HTTP (výpis JSON):");

        String jsonText = CallJson.getJsonText();
        System.out.println(jsonText);

        System.out.println("\n\n-------------------------------------------------2. Načítání JSON souboru ");
        System.out.println("\n\n-------------------------------------------------3. + naparsování JSON souboru do objektů - mapování:");
        VatStateMap vatState = CallJson.getJsonToMap();

        System.out.println("Počet načtených objektů v mapě: " + vatState.size());

        System.out.println("\n\n-------------------------------------------------4. Implementování vyhledávací logiky - s použitím listu:");
        ratesList = vatState.getListValues();
        System.out.println(vatState.getAllInfo(ratesList));
        System.out.println("\n\n-------------------------------------------------4. Seřazení pomocí komparátoru:");
        vatState.sortStandardRate(ratesList);
        System.out.println(vatState.getAllInfo(ratesList));
        String text1 = vatState.getAllInfo(vatState.getInfo3BigStandardRates(ratesList,NUMBER_OF_COUNTRIES));
        String text2 =vatState.getAllInfo(vatState.getInfo3SmallStandardRates(ratesList,NUMBER_OF_COUNTRIES));
        System.out.println("\n\n-------------------------------------------------5. Výpis na konzoli:");
        System.out.println(text1 + "\n\n" + text2);

        System.out.println("\n\n-------------------------------------------------6. Výsledek do souboru: " + FILENAME);
        try {
            vatState.exportToFile(FILENAME,text1 + "\n\n" + text2);
        } catch (FileNotFoundException e) {
            System.err.println("Soubor " + FILENAME + " nebylo možné uložit.");
        }

        System.out.println("\n\n-------------------------------------------------7. BONUS1: vyhledávání daňových sazeb dle zkratky: ");
        searchTaxRates(vatState);

        System.out.println("\n\n===================== A TO JE VŠE PŘÁTELÉ =====================");
    }
    private static void searchTaxRates(VatStateMap vatStateMap){
        boolean repeat = true;
        while (repeat == true) {
            Scanner scanner = new Scanner(System.in);
            String abbreviation;
            Set<String> countrySet = vatStateMap.getAllStateAbbreviations();
            System.out.println("\n" + countrySet);
            System.out.print("\n--------------Zadej zkratku výše uvedené země: ");
            abbreviation = scanner.nextLine();
            while ((abbreviation==null) || !(countrySet.contains(abbreviation))) {
                System.err.println("Zkratka země neexistuje");
                System.out.println("Zkratky jsou TYTO:" + countrySet);
                System.out.print("\nZadej novou zkratku výše uvedené země: ");
                abbreviation = scanner.nextLine();
            }
            System.out.println(vatStateMap.getForTaxRates(abbreviation));
            System.out.print("\n--------------Chceš zadat další zemi - a/n ?: ");
            repeat = scanner.nextLine().equals("a");
        }
    }
}
