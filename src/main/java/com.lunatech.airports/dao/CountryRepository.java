package com.lunatech.airports.dao;

import com.lunatech.airports.model.Country;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CountryRepository extends CrudRepository<Country, Long> {

    List<Country> findByNameStartingWith(String name);

    Country findByName(@Param("name") String name);

    Country findByCode(@Param("code") String code);
}
