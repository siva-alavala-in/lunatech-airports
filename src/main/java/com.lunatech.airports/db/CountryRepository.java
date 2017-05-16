package com.lunatech.airports.db;

import com.lunatech.airports.model.Country;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface CountryRepository extends CrudRepository<Country, Long> {

    List<Country> findByLowerCaseNameStartingWith(String name);

    Country findByLowerCaseName(String name);

    Country findByLowerCaseCode(String code);

    List<Country> findByIdIn(List<Long> countryIds);
}
