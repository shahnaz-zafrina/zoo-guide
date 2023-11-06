package com.example.zooui;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;

public class CountryUtils {

    public static String[] getAllCountries() {
        List<String> countries = new ArrayList<>();

        // Get all available locales
        Locale[] locales = Locale.getAvailableLocales();

        for (Locale locale : locales) {
            String country = locale.getDisplayCountry();

            // Ignore empty or duplicate entries
            if (!country.isEmpty() && !countries.contains(country)) {
                countries.add(country);
            }
        }

        // Sort the countries alphabetically
        Collections.sort(countries, new Comparator<String>() {
            @Override
            public int compare(String country1, String country2) {
                return country1.compareToIgnoreCase(country2);
            }
        });

        return countries.toArray(new String[0]);
    }
}
