package com.lunatech.airports.dao;

import com.lunatech.airports.db.AirportRepository;
import com.lunatech.airports.model.Airport;
import com.lunatech.airports.model.Country;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AirportService {

    @Autowired
    private AirportRepository airportRepository;

    public List<Airport> findAirports(@NonNull Country country) {
        return airportRepository.findByCountryId(country.getId());
    }

    public List<Airport> findAirports(@NonNull List<Country> countries) {
        List<Long> countryIds = countries.stream().map(c -> c.getId()).collect(Collectors.toList());
        return airportRepository.findByCountryIdIn(countryIds);
    }

}
