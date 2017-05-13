package com.lunatech.airports.dao;

import com.lunatech.airports.model.Airport;
import com.lunatech.airports.model.Country;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@DataJpaTest
public class AirportRepositoryTests {

    @Autowired
    private AirportRepository repository;

    @Autowired
    private TestEntityManager entityManager;

    private Country sampleCountry() {
        return Country.builder().id(302634).name("India").code("IN").continent("AS").build();
    }

    private Airport sampleAirport() {
        return Airport.builder().id(30356).ident("VERU").name("Rupsi India Airport").country(sampleCountry()).build();
    }

    @Before
    public void setupCountry() {
        entityManager.persistAndFlush(sampleCountry());
        entityManager.persist(sampleAirport());
    }

    @Test
    public void testFindByIsoCountry() throws Exception {
        assertThat(repository.findByCountry(sampleCountry())).containsOnly(sampleAirport());
        assertThat(repository.findByCountry(Country.builder().id(100).name("NL").code("NL").build())).isEmpty();
    }

}
