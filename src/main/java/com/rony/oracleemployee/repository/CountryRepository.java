package com.rony.oracleemployee.repository;

import com.rony.oracleemployee.model.Country;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CountryRepository extends JpaRepository<Country, Long> {
    Country getCountryByCountryCode(String countryCode);
}