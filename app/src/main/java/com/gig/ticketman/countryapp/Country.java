package com.gig.ticketman.countryapp;

/**
 * Created by DAMILOLA on 8/29/2017.
 */

public class Country {
    private String countryName;
    private String countryCurrency;
    private String countryLanguage;

    public Country() {
    }

    public Country(String countryName, String countryCurrency, String countryLanguage) {
        this.countryName = countryName;
        this.countryCurrency = countryCurrency;
        this.countryLanguage = countryLanguage;
    }

    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    public String getCountryCurrency() {
        return countryCurrency;
    }

    public void setCountryCurrency(String countryCurrency) {
        this.countryCurrency = countryCurrency;
    }

    public String getCountryLanguage() {
        return countryLanguage;
    }

    public void setCountryLanguage(String countryLanguage) {
        this.countryLanguage = countryLanguage;
    }
}
