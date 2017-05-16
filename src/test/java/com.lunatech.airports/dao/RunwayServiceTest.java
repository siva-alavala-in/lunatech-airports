package com.lunatech.airports.dao;

import com.lunatech.airports.db.RunwayRepository;
import com.lunatech.airports.model.Airport;
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

    private static Airport bangalore() {
        return Airport.builder().id(1).countryId(1L).ident("BLR").name("Bangalore").build();
    }


    private static Airport amsterdam() {
        return Airport.builder().id(2).countryId(2L).ident("AMS").name("Amsterdam").build();
    }

    private static List<Runway> bangaloreRunways() {
        return Collections.singletonList(Runway.builder().id(1).airportId(bangalore().getId()).build());
    }

    @Test
    public void testFetchRunways() throws Exception {
        given(runwayRepository.findByAirportId(1L)).willReturn(bangaloreRunways());
        given(runwayRepository.findByAirportIdIn(Collections.singletonList(1L))).willReturn(bangaloreRunways());

        assertThat(runwayService.fetchRunways(bangalore())).isEqualTo(bangaloreRunways());
        assertThat(runwayService.fetchRunways(Collections.singletonList(bangalore()))).isEqualTo(bangaloreRunways());

        given(runwayRepository.findByAirportId(2L)).willReturn(Collections.emptyList());
        given(runwayRepository.findByAirportIdIn(Collections.singletonList(2L))).willReturn(Collections.emptyList());

        assertThat(runwayService.fetchRunways(amsterdam())).isEmpty();
        assertThat(runwayService.fetchRunways(Collections.singletonList(amsterdam()))).isEmpty();

        given(runwayRepository.findByAirportIdIn(Arrays.asList(1L, 2L))).willReturn(bangaloreRunways());
        assertThat(runwayService.fetchRunways(Arrays.asList(bangalore(), amsterdam()))).isEqualTo(bangaloreRunways());
    }

}
