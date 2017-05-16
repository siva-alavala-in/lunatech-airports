package com.lunatech.airports.model;


import lombok.*;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.*;
import java.util.List;
import java.util.Map;

import static com.lunatech.airports.utils.CsvUtils.l;

@Entity
@Table(indexes = {
        @Index(name = "Country_Code", columnList = "code", unique = true),
        @Index(name = "Country_Name", columnList = "name")
})
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = {"id", "code", "name"})
@ToString(exclude = {"airports", "lowerCaseName", "lowerCaseCode"})
@Slf4j
public class Country {
    @Id
    @Column(name = "countryId")
    @NonNull
    private long id;

    @NonNull
    private String code;

    @NonNull
    private String name;

    private String lowerCaseName;

    private String lowerCaseCode;

    private String continent;

    private String wikipediaLink;

    private String keywords;

    @Singular
    // @OneToMany(cascade = ALL, mappedBy = "country")
    @Transient
    private List<Airport> airports;

    @PrePersist
    @PreUpdate
    private void populateLowerCaseNameAndCode() {
        lowerCaseName = name == null ? null : name.toLowerCase();
        lowerCaseCode = code == null ? null : code.toLowerCase();
    }

    public static Country from(Map<String, String> values) {
        return builder()
                .id(l(values, "id"))
                .code(values.get("code"))
                .name(values.get("name"))
                .continent(values.get("continent"))
                .wikipediaLink(values.get("wikipedia_link"))
                .keywords(values.get("keywords"))
                .build();
    }
}
