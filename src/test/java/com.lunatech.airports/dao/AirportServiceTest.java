package com.lunatech.airports.dao;

import com.lunatech.airports.db.AirportRepository;
import com.lunatech.airports.db.CountryRepository;
import com.lunatech.airports.model.Airport;
import com.lunatech.airports.model.Country;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AirportServiceTest {

    @MockBean
    private AirportRepository airportRepository;

    @Autowired
    private AirportService airportService;

    private static Country india() {
        return Country.builder().id(1).code("IN").name("India").build();
    }

    private static List<Airport> indianAirports() {
        return Collections.singletonList(Airport.builder().id(1).countryId(india().getId()).name("Airport").ident("1").build());
    }

    private static Country netherlands() {
        return Country.builder().id(2).code("NL").name("Netherlands").build();
    }

    @Test
    public void testFindAirports() throws Exception {
        given(airportRepository.findByCountryId(1)).willReturn(indianAirports());
        assertThat(airportService.findAirports(india())).isEqualTo(indianAirports());

        given(airportRepository.findByCountryId(2)).willReturn(Collections.emptyList());
        assertThat(airportService.findAirports(netherlands())).isEmpty();
    }

    @Test
    public void testFindAirportsWithList() throws Exception {
        given(airportRepository.findByCountryIdIn(Collections.singletonList(1L))).willReturn(indianAirports());
        assertThat(airportService.findAirports(Collections.singletonList(india()))).isEqualTo(indianAirports());

        given(airportRepository.findByCountryIdIn(Collections.singletonList(2L))).willReturn(Collections.emptyList());
        assertThat(airportService.findAirports(Collections.singletonList(netherlands()))).isEmpty();
    }
}
