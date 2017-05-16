package com.lunatech.airports.db;

import com.lunatech.airports.model.Airport;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@DataJpaTest
public class AirportRepositoryTests {

    @Autowired
    private AirportRepository repository;

    @Autowired
    private TestEntityManager entityManager;

    private Airport veruAirport() {
        return Airport.builder().id(30356).ident("VERU").name("Rupsi India Airport").countryId(302634L).build();
    }


    private Airport siachenAirport() {
        return Airport.builder().id(35129).ident("IN-0001").name("Siachen Glacier AFS Airport").countryId(302634L).build();
    }


    private Airport amsterdamAirport() {
        return Airport.builder().id(2513).ident("EHAM").name("Amsterdam Airport Schiphol").countryId(302708L).build();
    }

    @Before
    public void setupCountries() {
        entityManager.persist(veruAirport());
        entityManager.persist(siachenAirport());
        entityManager.persist(amsterdamAirport());
    }

    @Test
    public void testFindByCountryId() throws Exception {
        assertThat(repository.findByCountryId(302634L)).containsSequence(veruAirport(), siachenAirport());
        assertThat(repository.findByCountryId(302708L)).containsOnly(amsterdamAirport());
        assertThat(repository.findByCountryId(100)).isEmpty();
    }

    @Test
    public void testFindByCountryIdIn() throws Exception {
        assertThat(repository.findByCountryIdIn(Arrays.asList(302634L, 302708L))).contains(veruAirport());
        assertThat(repository.findByCountryIdIn(Collections.singletonList(100L))).isEmpty();
    }

    @Test
    public void testCountriesWithHighestNumberOfAirports() {
        assertThat(repository.countriesWithHighestNumberOfAirports()).containsSequence(302634L, 302708L);
    }

    @Test
    public void testCountriesWithLeastNumberOfAirports() {
        assertThat(repository.countriesWithLeastNumberOfAirports()).containsSequence(302708L, 302634L);
    }

}
