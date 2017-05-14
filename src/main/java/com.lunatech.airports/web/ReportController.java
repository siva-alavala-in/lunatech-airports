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
public class ReportController {

    @Autowired
    private CountryRepository countryRepository;

    @RequestMapping("/report")
    public String report(Model model) {
        log.info("Report");
        return "report";
    }

}
