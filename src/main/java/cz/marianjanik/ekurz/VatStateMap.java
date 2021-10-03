package cz.marianjanik.ekurz;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.util.*;

public class VatStateMap {

    @JsonProperty(value = "last_updated")
    private String lastUpdated;

    private String disclaimer;

    private Map<String, VatState> rates = new HashMap<>();

    public String getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(String lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

    public String getDisclaimer() {
        return disclaimer;
    }

    public void setDisclaimer(String disclaimer) {
        this.disclaimer = disclaimer;
    }

    public Map<String, VatState> getRates() {
        return rates;
    }

    public void setRates(Map<String, VatState> rates) {
        this.rates = rates;
    }

    public int size(){
        return rates.size();
    }

    public String getForTaxRates(String countryAbbreviation) {
        String text = "Rates: standard - reduce - reduce alt - reduce super - parking\n";
               text += rates.get(countryAbbreviation).getCountry() + ":    "
                + rates.get(countryAbbreviation).getStandardRate() + " --- "
                + rates.get(countryAbbreviation).getReducedRate() + " --- "
                + rates.get(countryAbbreviation).getReducedRateAlt() + " ----- "
                + rates.get(countryAbbreviation).getSuperReducedRate() + " ----- "
                + rates.get(countryAbbreviation).getParkingRate() + "\n";
        return text;
    }


    public List getListValues() {
        ArrayList<VatState> ratesList = new ArrayList<VatState>(rates.values());
        return ratesList;
    }

    public String getAllInfo(List<VatState> listRates){
        StringBuilder builder = new StringBuilder();
        builder.append("Rates: standard - reduce - reduce alt - reduce super - parking\n");
        for (VatState item:listRates) {
            builder.append(item.getCountry() + ":    " + item.getStandardRate() + " --- " + item.getReducedRate()
                    + " --- " + item.getReducedRateAlt() + " ----- " + item.getSuperReducedRate() + " ----- " + item.getParkingRate() + "\n");
        }
        return builder.toString();
    }

    public List sortStandardRate(List<VatState> listRates) {
        Collections.sort(listRates,new StandardRateComparator());
        return listRates;
    }

    public List getInfo3SmallStandardRates(List<VatState> listRates,int numberOfCountry) {
        List <VatState> myList = sortStandardRate(listRates);
        List <VatState> listChoice = new ArrayList<>();
        for (int i = 0; i < numberOfCountry ; i++) {
            listChoice.add(myList.get(i));
        }
        return listChoice;
    }

    public List getInfo3BigStandardRates(List<VatState> listRates,int numberOfCountry) {
        List <VatState> myList = sortStandardRate(listRates);
        List <VatState> listChoice = new ArrayList<>();
        for (int i = myList.size()-1; i > myList.size()-numberOfCountry-1 ; i--) {
            listChoice.add(myList.get(i));
        }
        return listChoice;
    }

    public void exportToFile(String filename, String text) throws FileNotFoundException {
        try (PrintWriter writer = new PrintWriter(new FileOutputStream(filename))) {
            writer.println(text);
        }
    }
}