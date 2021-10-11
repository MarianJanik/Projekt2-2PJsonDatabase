package cz.marianjanik.ekurz;

import com.fasterxml.jackson.annotation.JsonProperty;

public class VatState {

    private String country;

    @JsonProperty(value = "standard_rate")
    private int standardRate;

    @JsonProperty(value = "reduced_rate")
    private String reducedRate;

    @JsonProperty(value = "reduced_rate_alt")
    private String reducedRateAlt;

    @JsonProperty(value = "super_reduced_rate")
    private String superReducedRate;

    @JsonProperty(value = "parking_rate")
    private String parkingRate;


    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public int getStandardRate() {
        return standardRate;
    }

    public void setStandardRate(int standardRate) {
        this.standardRate = standardRate;
    }

    public String getReducedRate() {
        return reducedRate;
    }

    public void setReducedRate(String reducedRate) {
        this.reducedRate = reducedRate;
    }

    public String getReducedRateAlt() {
        return reducedRateAlt;
    }

    public void setReducedRateAlt(String reducedRateAlt) {
        this.reducedRateAlt = reducedRateAlt;
    }

    public String getSuperReducedRate() {
        return superReducedRate;
    }

    public void setSuperReducedRate(String superReducedRate) {
        this.superReducedRate = superReducedRate;
    }

    public String getParkingRate() {
        return parkingRate;
    }

    public void setParkingRate(String parkingRate) {
        this.parkingRate = parkingRate;
    }
}

