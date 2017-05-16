package com.lunatech.airports.db;

import com.lunatech.airports.model.Airport;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface AirportRepository extends CrudRepository<Airport, Long> {

    List<Airport> findByCountryId(long countryId);

    List<Airport> findByCountryIdIn(List<Long> countryId);

    @Query("SELECT a.countryId FROM Airport a GROUP BY a.countryId ORDER BY COUNT(a) DESC")
    List<Long> countriesWithHighestNumberOfAirports();

    @Query("SELECT a.countryId FROM Airport a GROUP BY a.countryId ORDER BY COUNT(a) ASC")
    List<Long> countriesWithLeastNumberOfAirports();
}
