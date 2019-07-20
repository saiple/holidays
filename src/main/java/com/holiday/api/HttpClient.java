package com.holiday.api;

import com.fasterxml.jackson.databind.SerializationFeature;
import com.holiday.dto.Country;
import com.holiday.dto.Holiday;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class HttpClient {

    RestTemplate restTemplate;

    private static final String GET_ALL_COUNTRIES_URL = "https://date.nager.at/Api/v2/AvailableCountries";

    private static final String GET_ALL_HOLIDAYS_OF_COUNTRY_URL = "https://date.nager.at/Api/v1/Get/%1$s/%2$s";

    public HttpClient() {
        restTemplate = new RestTemplate();
        MappingJackson2HttpMessageConverter jsonHttpMessageConverter = new MappingJackson2HttpMessageConverter();
        jsonHttpMessageConverter.getObjectMapper().configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        restTemplate.getMessageConverters().add(jsonHttpMessageConverter);

    }

    public List<Country> getAllCountries(){
        Country[] forNow = restTemplate.getForObject(GET_ALL_COUNTRIES_URL, Country[].class);
        return Arrays.asList(forNow);
    }

    public List<Holiday> getHolidaysOfCountry(String countryCode, Integer year){
        Holiday[] forNow = restTemplate.getForObject(String.format(GET_ALL_HOLIDAYS_OF_COUNTRY_URL, countryCode, year), Holiday[].class);
        return Arrays.asList(forNow);
    }


}
