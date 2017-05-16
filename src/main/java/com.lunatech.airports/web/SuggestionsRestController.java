package com.lunatech.airports.web;

import com.lunatech.airports.dao.CountryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Rest Controller for Suggestions
 */
@Slf4j
@RestController("/suggestions")
public class SuggestionsRestController {

    @Autowired
    private CountryService countryService;

    /**
     * Provide suggestions for Country Name
     * @param countryParam Country Name prefix
     * @return List of countries with given prefix
     */
    @RequestMapping("/country/{countryParam}")
    public List<String> countrySuggestions(@PathVariable String countryParam) {
        log.debug("countrySuggestions for {}", countryParam);
        List<String> suggestions = countryService.countryNamesWithPrefix(countryParam);
        log.info("countrySuggestions for {} => {}", countryParam, suggestions);
        return suggestions;
    }

}
