package com.lunatech.airports.web;

import com.lunatech.airports.dao.AirportService;
import com.lunatech.airports.dao.CountryService;
import com.lunatech.airports.dao.RunwayService;
import com.lunatech.airports.model.Airport;
import com.lunatech.airports.model.Country;
import com.lunatech.airports.model.Runway;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.websocket.server.PathParam;
import java.util.*;

@Slf4j
@Controller
public class QueryController {

    @Autowired
    private CountryService countryService;

    @Autowired
    private AirportService airportService;

    @Autowired
    private RunwayService runwayService;

    @RequestMapping("/query")
    public String query() {
        log.info("Query");
        return "query";
    }


    @RequestMapping("/query/{countryParam}")
    public String queryCountry(@PathVariable String countryParam, Model model) {
        log.info("Query for {}", countryParam);
        Optional<Country> country = countryService.findCountry(countryParam);
        if (country.isPresent()) {
            log.debug("Found country {} for {}", country.get(), countryParam);
            populateResults(country.get(), model);
        } else {
            log.info("No country found for {}", countryParam);
            model.addAttribute("error", "Invalid Country " + countryParam);
        }
        return "query";
    }

    protected void populateResults(@NonNull Country country, @NonNull Model model) {
        log.debug("populateResults {}", country);
        List<Airport> airports = airportService.findAirports(country);
        Map<Long, Airport> airportMap = new HashMap<>();
        airports.forEach(a -> {
            airportMap.put(a.getId(), a);
            a.setRunways(new ArrayList<>());
        });
        List<Runway> runways = runwayService.fetchRunways(airports);
        runways.forEach(r -> airportMap.get(r.getAirportId()).getRunways().add(r));
        model.addAttribute("country", country);
        model.addAttribute("airports", airports);
    }

}
