package com.lunatech.airports.web;

import com.lunatech.airports.dao.CountryRepository;
import com.lunatech.airports.model.Country;
import com.lunatech.airports.utils.StringUtils;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Slf4j
@Controller
public class QueryController {

    @Autowired
    private CountryRepository countryRepository;

    Country fetchCountry(@NonNull String countryParam) {
        Country country = countryRepository.findByCode(countryParam);
        log.debug("Country by Code {} => {}", countryParam, country);
        if (country == null) {
            country = countryRepository.findByName(countryParam);
            log.debug("Country by Name {} => {}", countryParam, country);
        }
        return country;
    }

    @RequestMapping("/query")
    public String query(@RequestParam(value = "country", required = false) String countryParam, Model model) {
        log.info("Query for {}", countryParam);
        if (!StringUtils.isEmpty(countryParam)) {
            model.addAttribute("country", fetchCountry(countryParam));
        }
        return "query";
    }

}
