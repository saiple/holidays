package com.holiday.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Holiday {
    String date;
    String localName;
    String name;
    String countryCode;
    Boolean fixed;
    Boolean global;
    Integer launchYear;
    String type;


}
