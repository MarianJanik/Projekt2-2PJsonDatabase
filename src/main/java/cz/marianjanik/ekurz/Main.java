package cz.marianjanik.ekurz;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws IOException, InterruptedException {

        final String MY_URL = "https://euvatrates.com/rates.json";
        final int NUMBER_OF_COUNTRIES = 4;
        final String FILENAME = "TheBestAndWorstRates";
        final String COUNTRY_ABBREVIATION = "SK";
        List ratesList;

        HttpClientRestApi client = new HttpClientRestApi();
        String jsonText = client.callApi(MY_URL);
        System.out.println(jsonText);

        VatStateMapper stateMapper = new VatStateMapper();
        VatStateMap vatState = new VatStateMap();
        vatState = stateMapper.mapToObject(jsonText);
        System.out.println(vatState.size());

        ratesList = vatState.getListValues();

        System.out.println(vatState.getAllInfo(ratesList));
        vatState.sortStandardRate(ratesList);
        System.out.println(vatState.getAllInfo(ratesList));
        String text1 = vatState.getAllInfo(vatState.getInfo3BigStandardRates(ratesList,NUMBER_OF_COUNTRIES));
        String text2 =vatState.getAllInfo(vatState.getInfo3SmallStandardRates(ratesList,NUMBER_OF_COUNTRIES));

        System.out.println(text1 + "\n\n" + text2);

        vatState.exportToFile(FILENAME,text1 + "\n\n" + text2);



    }

    private void searchTaxRates(){
        String repeat = "ano";
        while (repeat!="n") {
            System.out.print("Zadej Zkratku země: ");
        // -----------------------------------------------------*******************
            System.out.println(vatState.getForTaxRates(COUNTRY_ABBREVIATION));
        }
    }
}
