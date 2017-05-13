package com.lunatech.airports.dao;

import com.lunatech.airports.model.Airport;
import com.lunatech.airports.model.Country;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface AirportRepository extends CrudRepository<Airport, Long> {

    List<Airport> findByCountry(Country country);
}
