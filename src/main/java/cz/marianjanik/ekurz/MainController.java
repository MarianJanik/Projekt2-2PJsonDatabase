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

    /**
     * The method sends the entire JSON file.
     * @return
     */
    @GetMapping("/json/all")
    public VatStateMap getJson() {
        return CallJson.getJsonToMap();
    }

    /**
     * The method sends from the JSON file 2X countries: X with the highest and X with the lowest VAT.
     * @param number The number can be changed in a front-end application.
     * @return
     */
    @GetMapping("/json/{number}")
    public List<VatState> getBestAndWorstRates(@PathVariable int number) {
        vatState = CallJson.getJsonToMap();
        ratesList = vatState.getListValues();
        vatState.sortStandardRate(ratesList);
        List<VatState> listOfVatState = vatState.getInfo3BigStandardRates(ratesList,number);
        List<VatState> smallList = vatState.getInfo3SmallStandardRates(ratesList,number);
        for (VatState item: smallList) {
            listOfVatState.add(item);
        }
        return listOfVatState;
    }

    /**
     * The method allows you to send a response (VAT information) based on the country code sent.
     * @param abbreviation
     * @return
     */
    @GetMapping("/json")
    public VatState getInfoAboutCountry(@RequestParam String abbreviation) {
        vatState = CallJson.getJsonToMap();
        return vatState.getRates().get(abbreviation);
    }
}
