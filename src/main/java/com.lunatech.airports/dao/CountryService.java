package com.lunatech.airports.dao;

import com.lunatech.airports.db.AirportRepository;
import com.lunatech.airports.db.CountryRepository;
import com.lunatech.airports.model.Country;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
public class CountryService {

    @Autowired
    private CountryRepository countryRepository;

    @Autowired
    private AirportRepository airportRepository;

    public List<String> countryNamesWithPrefix(@NonNull String prefix) {
        return countryRepository.findByLowerCaseNameStartingWith(prefix.toLowerCase()).stream().map(c -> c.getName()).collect(Collectors.toList());
    }

    public Optional<Country> findCountry(@NonNull String countryParam) {
        Country country = countryRepository.findByLowerCaseName(countryParam.toLowerCase());
        if (country == null) {
            country = countryRepository.findByLowerCaseCode(countryParam.toLowerCase());
        }
        return Optional.ofNullable(country);
    }

    public List<Country> topTenCountriesWithHighestNumberOfAirports() {
        List<Long> countryIds = airportRepository.countriesWithHighestNumberOfAirports().stream().limit(10).collect(Collectors.toList());
        log.debug("topTenCountriesWithHighestNumberOfAirports => {}", countryIds);
        return countryRepository.findByIdIn(countryIds);
    }

    public List<Country> topTenCountriesWithLeastNumberOfAirports() {
        List<Long> countryIds = airportRepository.countriesWithLeastNumberOfAirports().stream().limit(10).collect(Collectors.toList());
        log.debug("topTenCountriesWithLeastNumberOfAirports => {}", countryIds);
        return countryRepository.findByIdIn(countryIds);
    }

}
