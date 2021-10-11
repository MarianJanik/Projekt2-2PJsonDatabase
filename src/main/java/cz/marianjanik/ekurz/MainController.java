package cz.marianjanik.ekurz;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.web.bind.annotation.*;
import java.io.IOException;
import java.util.*;

/**
 * A "REST API" class that allows a front-end application to communicate with this program
 * and use the methods below to retrieve the required information from the JSON file.
 */

@RestController
public class MainController {

    private final String MY_URL = "https://euvatrates.com/rates.json";
    private VatStateMap vatState;
    private List ratesList;
    StringBuilder builder = new StringBuilder();
    HttpClientRestApi client = new HttpClientRestApi();

    /**
     * The method sends the entire JSON file.
     * @return
     */

    @GetMapping("/json/all")
    public String getJson() {
        builder.setLength(0);
        String jsonText = null;
        try {
            jsonText = client.callApi(MY_URL);
        } catch (IOException e) {
            System.err.println(e.getMessage() + "\nWebová stránky: " + MY_URL);
        } catch (InterruptedException e) {
            System.err.println(e.getMessage() + "\nNačtení z adresy " + MY_URL + " se nezdařilo.");
        }
        builder.append("-------------------------------------------------Volání API pomocí HTTP (výpis JSON):\n\n");
        builder.append(jsonText);
        return builder.toString();
    }

    /**
     * The method sends from the JSON file 6 countries: 3 with the highest and 3 with the lowest VAT.
     * @param number The number can be changed in a front-end application.
     * @return
     */

    @GetMapping("/json/{number}")
    public String getBestAndWorstRates(@PathVariable int number) {
        builder.setLength(0);
        String jsonText = null;
        try {
            jsonText = client.callApi(MY_URL);
        } catch (IOException e) {
            System.err.println(e.getMessage() + "\nWebová stránky: " + MY_URL);
        } catch (InterruptedException e) {
            System.err.println(e.getMessage() + "\nNačtení z adresy " + MY_URL + " se nezdařilo.");
        }
        VatStateMapper stateMapper = new VatStateMapper();
        try {
            vatState = stateMapper.mapToObject(jsonText);
        } catch (JsonProcessingException e) {
            System.err.println("Převedení textových proměnných ze souboru JSON do mapy se nezdařilo.");
        }
        ratesList = vatState.getListValues();
        vatState.sortStandardRate(ratesList);
        String text1 = vatState.getAllInfo(vatState.getInfo3BigStandardRates(ratesList,number));
        String text2 = vatState.getAllInfo(vatState.getInfo3SmallStandardRates(ratesList,number));
        builder.append("-------------------------------------------------Výpis " + number
                + " zemí s nejvyššími sazbami " + "a " + number + " zemí s nejnižšími sazbami:\n\n");
        builder.append(text1 + "\n\n" + text2);
        return builder.toString();
    }

    /**
     * The method allows you to send a response (VAT information) based on the country code sent.
     * @param abbreviation
     * @return
     */

    @GetMapping("/json")
    public String getInfoAboutCountry(@RequestParam String abbreviation) {
        builder.setLength(0);

        String jsonText = null;
        try {
            jsonText = client.callApi(MY_URL);
        } catch (IOException e) {
            System.err.println(e.getMessage() + "\nWebová stránky: " + MY_URL);
        } catch (InterruptedException e) {
            System.err.println(e.getMessage() + "\nNačtení z adresy " + MY_URL + " se nezdařilo.");
        }
        VatStateMapper stateMapper = new VatStateMapper();
        try {
            vatState = stateMapper.mapToObject(jsonText);
        } catch (JsonProcessingException e) {
            System.err.println("Převedení textových proměnných ze souboru JSON do mapy se nezdařilo.");
        }
        Set<String> countrySet = vatState.getAllStateAbbreviations();
        if ((abbreviation==null) || !(countrySet.contains(abbreviation))) {
            builder.append("Bylo zadáno \"" + abbreviation + "\" - tento kód země není v seznamu.");
            builder.append("\nSeznam obsahuje tyto kódy zemí, vyber jeden z nich:\n" + countrySet);
        } else {
            builder.append("-------------------------------------------------Výpis DPH hledané země:\n\n");
            builder.append(vatState.getForTaxRates(abbreviation));
            builder.append("\n\nVyber další kód země:\n" + countrySet);
        }
        return builder.toString();
    }
}
