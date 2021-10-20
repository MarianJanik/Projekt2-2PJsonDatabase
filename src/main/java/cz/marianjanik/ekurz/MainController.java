package cz.marianjanik.ekurz;

import org.springframework.web.bind.annotation.*;
import java.util.*;

/**
 * A "REST API" class that allows a front-end application to communicate with this program
 * and use the methods below to retrieve the required information from the JSON file.
 */

@RestController
public class MainController {

    private VatStateMap vatState;
    private List ratesList;
    StringBuilder builder = new StringBuilder();

    /**
     * The method sends the entire JSON file.
     * @return
     */

    @GetMapping("/json/all")
    public String getJson() {
        return CallJson.getJsonText();
    }

    /**
     * The method sends from the JSON file 6 countries: 3 with the highest and 3 with the lowest VAT.
     * @param number The number can be changed in a front-end application.
     * @return
     */

    @GetMapping("/json/{number}")
    public String getBestAndWorstRates(@PathVariable int number) {
        builder.setLength(0);
        vatState = CallJson.getJsonToMap();
        ratesList = vatState.getListValues();
        vatState.sortStandardRate(ratesList);
        String text1 = vatState.getAllInfo(vatState.getInfo3BigStandardRates(ratesList,number));
        String text2 = vatState.getAllInfo(vatState.getInfo3SmallStandardRates(ratesList,number));
        builder.append("-------------------------------------------------Výpis " + number
                + " zemí s nejvyššími sazbami a " + number + " zemí s nejnižšími sazbami:\n\n");
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
        vatState = CallJson.getJsonToMap();
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
