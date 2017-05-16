package com.lunatech.airports.model;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Transient;
import java.util.Map;
import java.util.function.Function;

import static com.lunatech.airports.utils.CsvUtils.l;
import static com.lunatech.airports.utils.CsvUtils.s;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = {"airportId", "id"})
@ToString(exclude = "airport")
public class Runway {

    @Id
    @Column(name = "runwayId")
    private long id;

    @NonNull
    private Long airportId;

    // @NonNull
    // @ManyToOne(optional = false)
    // @JoinColumn(name = "airportId", nullable = false)
    @Transient
    private Airport airport;

    // To improve performance
    @NonNull
    private Long countryId;

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

    public static Runway from(Map<String, String> values, Function<Long, Long> airportCountryProvider) {
        return builder()
                .id(l(values, "id"))
                .airportId(l(values, "airport_ref"))
                .countryId(airportCountryProvider.apply(l(values, "airport_ref")))
                .airportIdent(s(values, "airport_ident"))
                .lengthFt(s(values, "length_ft"))
                .widthFt(s(values, "width_ft"))
                .surface(s(values, "surface"))
                .lighted(s(values, "lighted"))
                .closed(s(values, "closed"))
                .le_ident(s(values, "le_ident"))
                .le_latitude_deg(s(values, "le_latitude_deg"))
                .le_longitude_deg(s(values, "le_longitude_deg"))
                .le_elevation_ft(s(values, "le_elevation_ft"))
                .le_heading_degT(s(values, "le_heading_degT"))
                .le_displaced_threshold_ft(s(values, "le_displaced_threshold_ft"))
                .he_ident(s(values, "he_ident"))
                .he_latitude_deg(s(values, "he_latitude_deg"))
                .he_longitude_deg(s(values, "he_longitude_deg"))
                .he_elevation_ft(s(values, "he_elevation_ft"))
                .he_heading_degT(s(values, "he_heading_degT"))
                .he_displaced_threshold_ft(s(values, "he_displaced_threshold_ft"))
                .build();
    }

}
