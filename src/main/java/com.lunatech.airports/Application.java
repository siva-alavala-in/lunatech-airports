package com.lunatech.airports;

import com.lunatech.airports.db.AirportRepository;
import com.lunatech.airports.db.CountryRepository;
import com.lunatech.airports.db.RunwayRepository;
import com.lunatech.airports.model.Airport;
import com.lunatech.airports.model.Country;
import com.lunatech.airports.model.Runway;
import com.lunatech.airports.utils.CsvUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;

import java.io.IOException;

@SpringBootApplication
@Slf4j
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Autowired
    CountryRepository countryRepository;

    @Autowired
    AirportRepository airportRepository;

    @Autowired
    RunwayRepository runwayRepository;

    @Bean
    @Profile("!test")
    public CommandLineRunner bootstrap(ApplicationContext ctx) {
        return args -> {
            if (!countryRepository.findAll().iterator().hasNext()) {
                saveCountries();
                saveAirports();
                saveRunways();
            }
        };
    }


    void saveCountries() throws IOException {
        CsvUtils.readCsv("countries.csv").parallelStream().forEach(row -> {
            Country country = Country.from(row);
            log.debug("Saving {}", country);
            countryRepository.save(country);
        });
    }

    void saveAirports() throws IOException {
        CsvUtils.readCsv("airports.csv").parallelStream().forEach(row -> {
            Airport airport = Airport.from(row, (country) -> countryRepository.findByLowerCaseCode(country.toLowerCase()).getId());
            log.debug("Saving {}", airport);
            airportRepository.save(airport);
        });
    }

    void saveRunways() throws IOException {
        CsvUtils.readCsv("runways.csv").parallelStream().forEach(row -> {
            Runway runway = Runway.from(row, (airportId) -> airportRepository.findOne(airportId).getCountryId());
            log.debug("Saving {}", runway);
            runwayRepository.save(runway);
        });
    }

}
