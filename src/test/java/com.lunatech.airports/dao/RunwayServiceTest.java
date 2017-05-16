package com.lunatech.airports.dao;

import com.lunatech.airports.db.RunwayRepository;
import com.lunatech.airports.model.Airport;
import com.lunatech.airports.model.Country;
import com.lunatech.airports.model.Runway;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RunwayServiceTest {

    @MockBean
    private RunwayRepository runwayRepository;

    @Autowired
    private RunwayService runwayService;

    private static Country india() {
        return Country.builder().id(1).name("India").code("IN").continent("AS").build();
    }

    private static Airport bangalore() {
        return Airport.builder().id(1).countryId(india().getId()).ident("BLR").name("Bangalore").build();
    }

    private static Country netherlands() {
        return Country.builder().id(2).name("Netherlands").code("NL").continent("EU").build();
    }

    private static Airport amsterdam() {
        return Airport.builder().id(2).countryId(netherlands().getId()).ident("AMS").name("Amsterdam").build();
    }

    private static List<Runway> bangaloreRunways() {
        return Collections.singletonList(Runway.builder().id(1).airportId(bangalore().getId()).countryId(india().getId()).build());
    }

    @Test
    public void testFetchRunwaysForAirport() throws Exception {
        given(runwayRepository.findByAirportId(1L)).willReturn(bangaloreRunways());
        given(runwayRepository.findByAirportIdIn(Collections.singletonList(1L))).willReturn(bangaloreRunways());

        assertThat(runwayService.fetchRunwaysForAirport(bangalore())).isEqualTo(bangaloreRunways());
        assertThat(runwayService.fetchRunwaysForAirports(Collections.singletonList(bangalore()))).isEqualTo(bangaloreRunways());

        given(runwayRepository.findByAirportId(2L)).willReturn(Collections.emptyList());
        given(runwayRepository.findByAirportIdIn(Collections.singletonList(2L))).willReturn(Collections.emptyList());

        assertThat(runwayService.fetchRunwaysForAirport(amsterdam())).isEmpty();
        assertThat(runwayService.fetchRunwaysForAirports(Collections.singletonList(amsterdam()))).isEmpty();

        given(runwayRepository.findByAirportIdIn(Arrays.asList(1L, 2L))).willReturn(bangaloreRunways());
        assertThat(runwayService.fetchRunwaysForAirports(Arrays.asList(bangalore(), amsterdam()))).isEqualTo(bangaloreRunways());
    }


    @Test
    public void testFetchRunwaysForCountry() throws Exception {
        given(runwayRepository.findByCountryId(1L)).willReturn(bangaloreRunways());
        given(runwayRepository.findByCountryIdIn(Collections.singletonList(1L))).willReturn(bangaloreRunways());

        assertThat(runwayService.fetchRunwaysForCountry(india())).isEqualTo(bangaloreRunways());
        assertThat(runwayService.fetchRunwaysForCountries(Collections.singletonList(india()))).isEqualTo(bangaloreRunways());

        given(runwayRepository.findByCountryId(2L)).willReturn(Collections.emptyList());
        given(runwayRepository.findByCountryIdIn(Collections.singletonList(2L))).willReturn(Collections.emptyList());

        assertThat(runwayService.fetchRunwaysForCountry(netherlands())).isEmpty();
        assertThat(runwayService.fetchRunwaysForCountries(Collections.singletonList(netherlands()))).isEmpty();

        given(runwayRepository.findByCountryIdIn(Arrays.asList(1L, 2L))).willReturn(bangaloreRunways());
        assertThat(runwayService.fetchRunwaysForCountries(Arrays.asList(india(), netherlands()))).isEqualTo(bangaloreRunways());
    }

}
