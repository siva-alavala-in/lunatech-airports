package com.lunatech.airports.db;

import com.lunatech.airports.model.Runway;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Set;

public interface RunwayRepository extends CrudRepository<Runway, Long> {

    List<Runway> findByAirportId(Long airportId);

    List<Runway> findByAirportIdIn(List<Long> airportIds);
}
