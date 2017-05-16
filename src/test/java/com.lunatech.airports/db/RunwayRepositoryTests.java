package com.lunatech.airports.db;

import com.lunatech.airports.model.Runway;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@DataJpaTest
public class RunwayRepositoryTests {

    @Autowired
    private RunwayRepository repository;

    @Autowired
    private TestEntityManager entityManager;

    private Runway sampleRunway() {
        return Runway.builder().id(30356).airportId(1L).build();
    }

    @Before
    public void setupRunway() {
        entityManager.persist(sampleRunway());
    }


    @Test
    public void testFindByAirportId() throws Exception {
        assertThat(repository.findByAirportId(1L)).containsOnly(sampleRunway());
        assertThat(repository.findByAirportId(2L)).isEmpty();
    }

    @Test
    public void testFindByAirportIdIn() throws Exception {
        assertThat(repository.findByAirportIdIn(Collections.singletonList(1L))).containsOnly(sampleRunway());
        assertThat(repository.findByAirportIdIn(Collections.singletonList(2L))).isEmpty();
        assertThat(repository.findByAirportIdIn(Collections.emptyList())).isEmpty();
    }

}
