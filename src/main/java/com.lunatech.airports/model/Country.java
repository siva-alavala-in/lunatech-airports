package com.lunatech.airports.model;


import lombok.*;

import javax.persistence.*;
import java.util.List;

import static javax.persistence.CascadeType.ALL;

@Data
@Builder
@Entity
@Table(indexes = {
        @Index(name = "Country_Code", columnList = "code", unique = true),
        @Index(name = "Country_Name", columnList = "name")
})
@EqualsAndHashCode(of = {"id", "code", "name"})
public class Country {
    @Id
    @Column(name = "countryId")
    @NonNull
    private long id;

    @NonNull
    private String code;

    @NonNull
    private String name;

    private String continent;

    private String wikipediaLink;

    private String keywords;

    @Singular
    @OneToMany(cascade = ALL, mappedBy = "country")
    private List<Airport> airports;
}
