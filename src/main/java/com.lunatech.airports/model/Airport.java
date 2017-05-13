package com.lunatech.airports.model;

import lombok.*;

import javax.persistence.*;
import java.util.List;

import static javax.persistence.CascadeType.ALL;

@Data
@Builder
@Entity
@EqualsAndHashCode(of = {"country", "id", "name"})
public class Airport {

    @NonNull
    @ManyToOne(optional = false)
    @JoinColumn(name = "countryId", nullable = false)
    private Country country;

    @Id
    @Column(name = "airportId")
    private long id;

    @NonNull
    private String ident;

    @NonNull
    private String name;

    private String type;
    private String latitudeDeg;
    private String longitudeDeg;
    private String elevationFt;
    private String continent;

    private String isoRegion;
    private String municipality;
    private String scheduledService;
    private String gpsCode;
    private String iataCode;
    private String localCode;
    private String homeLink;
    private String wikipediaLink;
    private String keywords;

    @Singular
    @Transient
    @OneToMany(cascade = ALL, mappedBy = "airport")
    private List<Runway> runways;
}
