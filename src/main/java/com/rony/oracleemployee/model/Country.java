package com.rony.oracleemployee.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;

@Entity
@Table(name = "tbl_countries")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Country{
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @CreationTimestamp
    private LocalDate createdAt;

    @UpdateTimestamp
    private LocalDate updatedAt;

    @NotNull(message = "name cannot be empty")
    @Size(min = 2, message = "min is 2 characters")
    private String name;

    @Column(unique = true)
    private String countryCode;

}
