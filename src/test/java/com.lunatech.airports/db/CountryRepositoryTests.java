package com.lunatech.airports.db;

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
    public void testFindByLowerCaseNameStartingWith() throws Exception {
        assertThat(repository.findByLowerCaseNameStartingWith("Ind")).isEmpty();
        assertThat(repository.findByLowerCaseNameStartingWith("ind")).containsOnly(sampleCountry());
        assertThat(repository.findByLowerCaseNameStartingWith("Ne")).isEmpty();
        assertThat(repository.findByLowerCaseNameStartingWith("ne")).isEmpty();
    }


    @Test
    public void testFindByLowerCaseName() throws Exception {
        assertThat(repository.findByLowerCaseName("India")).isNull();
        assertThat(repository.findByLowerCaseName("india")).isEqualTo(sampleCountry());
        assertThat(repository.findByLowerCaseName("Netherlands")).isNull();
        assertThat(repository.findByLowerCaseName("netherlands")).isNull();
    }


    @Test
    public void testFindByLowerCaseCode() throws Exception {
        assertThat(repository.findByLowerCaseCode("IN")).isNull();
        assertThat(repository.findByLowerCaseCode("in")).isEqualTo(sampleCountry());
        assertThat(repository.findByLowerCaseCode("NL")).isNull();
        assertThat(repository.findByLowerCaseCode("nl")).isNull();
    }
}
