package com.lunatech.airports.dao;

import com.lunatech.airports.db.RunwayRepository;
import com.lunatech.airports.model.Airport;
import com.lunatech.airports.model.Country;
import com.lunatech.airports.model.Runway;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RunwayService {

    @Autowired
    private RunwayRepository runwayRepository;

    public List<Runway> fetchRunwaysForAirport(@NonNull Airport airport) {
        return runwayRepository.findByAirportId(airport.getId());
    }

    public List<Runway> fetchRunwaysForAirports(@NonNull List<Airport> airports) {
        List<Long> airportIds = airports.stream().map(a -> a.getId()).collect(Collectors.toList());
        return runwayRepository.findByAirportIdIn(airportIds);
    }

    public List<Runway> fetchRunwaysForCountry(@NonNull Country country) {
        return runwayRepository.findByCountryId(country.getId());
    }

    public List<Runway> fetchRunwaysForCountries(@NonNull List<Country> countries) {
        List<Long> countryIds = countries.stream().map(a -> a.getId()).collect(Collectors.toList());
        return runwayRepository.findByCountryIdIn(countryIds);
    }
}
