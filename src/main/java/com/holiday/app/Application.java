package com.holiday.app;

import com.holiday.api.HttpClient;
import com.holiday.dto.Country;
import com.holiday.dto.Holiday;

import java.util.List;

public class Application {

    public static void main(String[] args) {
        List<Country> countries = new HttpClient().getAllCountries();
        int i = 0;

        List<Holiday> holidays = new HttpClient().getHolidaysOfCountry("AT", 2017);
        int i1 = 0;
    }
}
