package com.lunatech.airports.web;

import com.lunatech.airports.dao.AirportService;
import com.lunatech.airports.dao.CountryService;
import com.lunatech.airports.dao.RunwayService;
import com.lunatech.airports.model.Airport;
import com.lunatech.airports.model.Country;
import com.lunatech.airports.model.Runway;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Slf4j
@Controller
public class ReportController {

    @ToString
    public static class CountryRunwayStats {
        public final Country country;
        public Set<String> runways = new HashSet<>();
        public Set<String> runwayIdentifications = new HashSet<>();

        CountryRunwayStats(Country country) {
            this.country = country;
        }
    }

    @Autowired
    private CountryService countryService;

    @Autowired
    private AirportService airportService;

    @Autowired
    private RunwayService runwayService;

    @RequestMapping("/report")
    public String report(Model model) {
        log.info("Report");
        List<Country> countriesWithMostAirports = countryService.topTenCountriesWithMostNumberOfAirports();
        log.info("countriesWithMostAirports => {}", countriesWithMostAirports);
        List<Country> countriesWithLeastAirports = countryService.topTenCountriesWithLeastNumberOfAirports();
        log.info("countriesWithLeastAirports => {}", countriesWithLeastAirports);
        Map<String, List<CountryRunwayStats>> results = new HashMap<>();
        results.put("Most Airports", getStatsFast(countriesWithMostAirports));
        results.put("Least Airports", getStatsFast(countriesWithLeastAirports));
        model.addAttribute("results", results);
        log.info("Returning {}", results);
        return "report";
    }

    public List<CountryRunwayStats> getStats(List<Country> countries) {
        fattenCountries(countries);
        return countries.stream().map(c -> {
            CountryRunwayStats stats = new CountryRunwayStats(c);
            // Runway Surfaces
            List<Runway> countryRunways = c.getAirports().stream().flatMap(a -> a.getRunways().stream()).collect(Collectors.toList());
            stats.runways = runwaySurfaces(countryRunways);
            stats.runwayIdentifications = topTenIdentifications(countryRunways);
            return stats;
        }).collect(Collectors.toList());
    }

    public void fattenCountries(List<Country> countries) {
        Map<Long, Country> countryMap = new HashMap<>();
        Map<Long, Airport> airportMap = new HashMap<>();
        countries.forEach(c -> {
            c.setAirports(new ArrayList<>());
            countryMap.put(c.getId(), c);
        });
        List<Airport> airports = airportService.findAirports(countries);
        airports.forEach(a -> {
            countryMap.get(a.getCountryId()).getAirports().add(a);
            a.setRunways(new ArrayList<>());
            airportMap.put(a.getId(), a);

        });
        List<Runway> runways = runwayService.fetchRunwaysForAirports(airports);
        runways.forEach(r -> {
            airportMap.get(r.getAirportId()).getRunways().add(r);
        });
    }

    public Map<Long, List<Runway>> runwaysForCountries(List<Country> countries) {
        Map<Long, List<Runway>> results = new HashMap<>();
        countries.forEach(c -> results.put(c.getId(), new ArrayList<>()));

        List<Runway> runways = runwayService.fetchRunwaysForCountries(countries);
        runways.forEach(r -> results.get(r.getCountryId()).add(r));

        return results;
    }

    public List<CountryRunwayStats> getStatsFast(List<Country> countries) {
        Map<Long, List<Runway>> runways = runwaysForCountries(countries);
        return countries.stream().map(c -> {
            CountryRunwayStats stats = new CountryRunwayStats(c);
            List<Runway> countryRunways = runways.get(c.getId());
            stats.runways = runwaySurfaces(countryRunways);
            stats.runwayIdentifications = topTenIdentifications(countryRunways);
            return stats;
        }).collect(Collectors.toList());
    }

    private Set<String> runwaySurfaces(List<Runway> runways) {
        return runways.stream().map(r -> r.getSurface()).filter(r -> !r.isEmpty()).collect(Collectors.toSet());
    }


    private Set<String> topTenIdentifications(List<Runway> runways) {
        // Top 10 Runway Identifications
        Map<String, Long> identificationCounts = runways.stream().map(r -> r.getLe_ident()).collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));
        List<Map.Entry<String, Long>> ic = new ArrayList<>(identificationCounts.entrySet());
        ic.sort((x, y) -> (int) (x.getValue() - y.getValue()));
        return ic.stream().limit(10).map(Map.Entry::getKey).collect(Collectors.toSet());
    }


}
