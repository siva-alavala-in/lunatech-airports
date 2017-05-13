package com.lunatech.airports.web;

import com.lunatech.airports.dao.CountryRepository;
import com.lunatech.airports.model.Country;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class QueryControllerTest {

    @MockBean
    private CountryRepository countryRepository;

    @Autowired
    private QueryController queryController;

    private static Country india() {
        return Country.builder().id(1).code("IN").name("India").build();
    }

    @Test
    public void testFetchCountry() throws Exception {
        given(countryRepository.findByCode("IN")).willReturn(india());
        given(countryRepository.findByName("India")).willReturn(india());
        assertThat(queryController.fetchCountry("IN")).isEqualTo(india());
        assertThat(queryController.fetchCountry("India")).isEqualTo(india());

        given(countryRepository.findByName("Netherlands")).willReturn(null);
        given(countryRepository.findByName("NL")).willReturn(null);
        assertThat(queryController.fetchCountry("NL")).isNull();
        assertThat(queryController.fetchCountry("Netherlands")).isNull();
    }
}
