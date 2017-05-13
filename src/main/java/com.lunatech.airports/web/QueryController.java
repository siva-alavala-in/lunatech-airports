package com.lunatech.airports.web;

import com.lunatech.airports.dao.CountryRepository;
import com.lunatech.airports.model.Country;
import com.lunatech.airports.utils.StringUtils;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.Collections;

@Controller
@Slf4j
public class QueryController {

    @Autowired
    CountryRepository countryRepository;

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
    public ModelAndView query(@RequestParam(value = "country", required = false) String countryParam) {
        Country country = StringUtils.isEmpty(countryParam) ? null : fetchCountry(countryParam);
        return new ModelAndView("abc", Collections.singletonMap("country", country));
    }

}
