package cz.marianjanik.ekurz;

import com.fasterxml.jackson.core.JsonProcessingException;
import java.io.IOException;

public class CallJson {

    protected static String getJsonText() {
        final String MY_URL = "https://euvatrates.com/rates.json";
        HttpClientRestApi client = new HttpClientRestApi();
        String jsonText = null;
        try {
            jsonText = client.callApi(MY_URL);
        } catch (IOException e) {
            System.err.println(e.getMessage() + "\nWebová stránky: " + MY_URL);
        } catch (InterruptedException e) {
            System.err.println(e.getMessage() + "\nNačtení z adresy " + MY_URL + " se nezdařilo.");
        }
        return jsonText;
    }

    protected static VatStateMap getJsonToMap() {
        VatStateMap vatState = null;
        String jsonText = getJsonText();
        VatStateMapper stateMapper = new VatStateMapper();
        try {
            vatState = stateMapper.mapToObject(jsonText);
        } catch (JsonProcessingException e) {
            System.err.println("Převedení textových proměnných ze souboru JSON do mapy se nezdařilo.");
        }
        return vatState;
    }
}
