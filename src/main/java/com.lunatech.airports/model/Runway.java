package com.lunatech.airports.model;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NonNull;

import javax.persistence.*;

@Data
@Builder
@Entity
@EqualsAndHashCode(of = {"airport", "id"})
public class Runway {

    @Id
    @Column(name = "runwayId")
    private String id;

    @NonNull
    @ManyToOne(optional = false)
    @JoinColumn(name = "airportId", nullable = false)
    private Airport airport;

    private String airportIdent;
    private String lengthFt;
    private String widthFt;
    private String surface;
    private String lighted;
    private String closed;
    private String le_ident;
    private String le_latitude_deg;
    private String le_longitude_deg;
    private String le_elevation_ft;
    private String le_heading_degT;
    private String le_displaced_threshold_ft;
    private String he_ident;
    private String he_latitude_deg;
    private String he_longitude_deg;
    private String he_elevation_ft;
    private String he_heading_degT;
    private String he_displaced_threshold_ft;
}
