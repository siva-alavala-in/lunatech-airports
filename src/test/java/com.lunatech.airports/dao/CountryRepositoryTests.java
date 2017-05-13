package com.lunatech.airports.dao;

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
public class CountryRepositoryTests {

    @Autowired
    private CountryRepository repository;

    @Autowired
    private TestEntityManager entityManager;


    private Country sampleCountry() {
        return Country.builder().id(302634).name("India").code("IN").continent("AS").build();
    }

    @Before
    public void setupCountry() {
        entityManager.persist(sampleCountry());
    }

    @Test
    public void testFindByNameStartingWith() throws Exception {
        assertThat(repository.findByNameStartingWith("Ind")).containsOnly(sampleCountry());
        assertThat(repository.findByNameStartingWith("Ne")).isEmpty();
    }


    @Test
    public void testFindByName() throws Exception {
        assertThat(repository.findByName("India")).isEqualTo(sampleCountry());
        assertThat(repository.findByName("Netherlands")).isNull();
    }


    @Test
    public void testFindByCode() throws Exception {
        assertThat(repository.findByCode("IN")).isEqualTo(sampleCountry());
        assertThat(repository.findByCode("NL")).isNull();
    }
}
