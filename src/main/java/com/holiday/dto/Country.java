package com.holiday.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Country {
    String key;
    String value;

    @Override
    public String toString() {
        return value;
    }

    public static List<Country> addNotSelected(List<Country> countries){
        List<Country> list = new ArrayList<>();
        list.add(new Country("NaN", "Not selected"));
        list.addAll(countries);
        return list;
    }

    public boolean isNotNaN(){
        return !key.equals("NaN");
    }
}
